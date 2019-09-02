package indi.aby.docm.api.operlog;

import indi.aby.docm.api.account.UserHelper;
import indi.aby.docm.api.operlog.constant.OperName;
import indi.aby.docm.api.operlog.constant.OperResult;
import indi.aby.docm.api.operlog.dto.OperLogVO;
import indi.aby.docm.api.util.ThreadLocalUtil;
import indi.aby.docm.util.ResourceUtil;
import indi.rui.common.base.util.SnowflakeIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
public class OperLogWriterHelper implements BeanFactoryAware {
    public static final String OPER_LOG = "threadVar.operLog";

    private static final AtomicInteger THREAD_COUNT = new AtomicInteger();

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4, r -> new Thread(r, "operLogWriter-" + THREAD_COUNT.getAndIncrement()));

    private static IOperLogServiceApi operLogServiceApi;


    public static OperLogVO get() {
        return Optional.ofNullable(ThreadLocalUtil.get(OPER_LOG))
                .map(operLog -> (OperLogVO) operLog)
                .orElseGet(()->{
                    set();
                    return get();
                });
    }

    public static void set(HttpServletRequest request) {
        ThreadLocalUtil.put(OPER_LOG, build(request));
    }

    public static void set() {
        ThreadLocalUtil.put(OPER_LOG, build());
    }

    private static OperLogVO build(HttpServletRequest request) {
        OperLogVO operLog = build();
        operLog.setIp(request.getRemoteHost());
        operLog.setMethod(request.getMethod());
        operLog.setUrl(request.getRequestURL().toString());
        operLog.setUserAgent(request.getHeader("user-agent"));
        return operLog;
    }

    private static OperLogVO build() {
        OperLogVO operLog = new OperLogVO();
        operLog.setId(String.valueOf(SnowflakeIDGenerator.genId()));
        return operLog;
    }

    public static void write(OperName operName, OperResult operResult, String module, String content) {
        write(operName, operResult, module, content,
                Optional.ofNullable(UserHelper.getCurrentUser())
                        .map(curUser -> curUser.getUsername())
                        .orElse(ResourceUtil.getString("default.unknown.operator")));
    }

    public static void write(OperName operName, OperResult operResult, String module, String content, String operator) {
        OperLogVO operLog = OperLogWriterHelper.get();
        operLog.setName(operName.getValue());
        operLog.setModule(module);
        operLog.setOperator(operator);
        operLog.setResult(operResult.getValue());
        operLog.setContent(content);
        doWrite();
    }

    private static void doWrite() {
        final OperLogVO operLog = get();
        if (operLog != null) {
            executor.submit(() -> {
                try {
                    operLogServiceApi.add(operLog);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        operLogServiceApi = beanFactory.getBean(IOperLogServiceApi.class);
    }
}
