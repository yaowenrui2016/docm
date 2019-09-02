package indi.aby.docm.api.operlog;

import indi.aby.docm.api.operlog.annotation.OperLog;
import indi.aby.docm.api.operlog.constant.OperResult;
import indi.aby.docm.api.operlog.dto.OperLogItemVO;
import indi.aby.docm.util.ModuleHelper;
import indi.rui.common.base.dto.Response;
import indi.rui.common.base.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class OperlogAspect {

    @Pointcut("execution(public * *(..))")
    public void anyPublicPC() {}

    @Pointcut("target(indi.rui.common.web.dao.IMapper)")
    public void implIMapperPC() {}

    @Pointcut("execution(public * indi.aby.docm..*.*Controller.*(..))")
    public void controllerPC() {}

    @Pointcut("execution(indi.aby.docm.api.auth.UserSummaryVO indi.aby.docm.core..*.*.login(..))")
    public void loginPC() {}

    @Pointcut("within(indi.aby.docm..*)")
    public void withinDocmPC() {}


    /*================ 通知 ===================*/

    /**
     * 通用操作日志
     * @param pjp
     */
    @Around(value = "controllerPC() && args(param) && @annotation(operLog)", argNames = "param,operLog")
    public Response write4General(ProceedingJoinPoint pjp, Object param, OperLog operLog) throws Throwable {
        OperResult operResult = OperResult.SUCCESS;
        Map<Object, Object> content = new HashMap<>();
        try {
            content.put("param", OperLogItemVO.of(param));
            Response res = (Response) pjp.proceed();
            Optional.ofNullable(res.getData()).map(data -> content.put("data", OperLogItemVO.of(data)));
            return res;
        } catch (Throwable t) {
            operResult = OperResult.FAILED;
            content.put("error", t.getMessage());
            throw t;
        } finally {
            OperLogWriterHelper.write(operLog.name(), operResult, ModuleHelper.getModuleName(operLog.module()),
                    JsonUtil.encode(content));
        }
    }

    /**
     * 登录-操作日志
     * @param pjp
     */
    @Around(value = "controllerPC() && args(param,*) && @annotation(operLog)", argNames = "param,operLog")
    public Response write4Login(ProceedingJoinPoint pjp, Object param, OperLog operLog) throws Throwable {
        return write4General(pjp, param, operLog);
    }
}
