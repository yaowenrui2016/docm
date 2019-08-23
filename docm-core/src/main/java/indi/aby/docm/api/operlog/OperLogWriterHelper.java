package indi.aby.docm.api.operlog;

import indi.aby.docm.api.operlog.support.IOperLogServiceApi;
import indi.aby.docm.api.user.UserHelper;
import indi.aby.docm.api.util.ThreadLocalUtil;
import indi.aby.docm.core.account.UserEntity;
import indi.aby.docm.util.ResourceUtil;
import indi.rui.common.base.dto.AbstractEntity;
import indi.rui.common.base.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;


@Slf4j
@Component
public class OperLogWriterHelper implements BeanFactoryAware {
    public static final String OPER_LOG = "threadVar.operLog";

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4, r -> new Thread(r, "oper-log-writer"));

    private static IOperLogServiceApi operLogServiceApi;


    public static OperLogVO get() {
        return Optional.ofNullable(ThreadLocalUtil.get(OPER_LOG))
                .map(operLog -> (OperLogVO) operLog)
                .orElse(null);
    }

    public static void set(HttpServletRequest request) {
        ThreadLocalUtil.put(OPER_LOG, build(request));
    }

    private static OperLogVO build(HttpServletRequest request) {
        OperLogVO operLog = new OperLogVO();
        operLog.setId(RandomUtil.uuid());
        operLog.setOperator(Optional.ofNullable(UserHelper.getCurrentUser()).map(curUser -> curUser.getName()).orElse(null));
        operLog.setIp(request.getRemoteHost());
        operLog.setMethod(request.getMethod());
        operLog.setUrl(request.getRequestURL().toString());
        operLog.setUserAgent(request.getHeader("user-agent"));
        return operLog;
    }

    public static void write4Add(AbstractEntity entity, OperResult operResult) {
        OperLogVO operLog = OperLogWriterHelper.get();
        operLog.setName(OperName.ADD.getValue());
        operLog.setModule(entity.getModule());
        operLog.setResult(operResult.getValue());
        operLog.setContent("{\"data\": \"" + entity.toString() + "\"}");
        doWrite();
    }

    public static void write4Login(OperName operName, OperResult operResult, String module, String content) {
        OperLogVO operLog = OperLogWriterHelper.get();
        operLog.setName(operName.getValue());
        operLog.setModule(module);
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
