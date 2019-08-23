package indi.aby.docm.api.operlog.intercept;

import indi.aby.docm.api.account.UserVO;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.aby.docm.api.operlog.OperLogWriterHelper;
import indi.aby.docm.api.operlog.OperName;
import indi.aby.docm.api.operlog.OperResult;
import indi.aby.docm.util.ResourceUtil;
import indi.rui.common.base.dto.AbstractEntity;
import indi.rui.common.web.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class OperlogAspect {

    @Pointcut("execution(public * *(..))")
    public void anyPublic() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.dao.*.add(..))")
    public void addInDao() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.dao.*.update(..))")
    public void updateInDao() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.dao.*.findAll(..))")
    public void findAllInDao() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.dao.*.findById(..))")
    public void findByIdInDao() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.dao.*.deleteAll(..))")
    public void deleteAllInDao() {}

    @Pointcut("within(indi.aby.docm..*)")
    public void withinDocm() {}

//    @Pointcut("target(indi.rui.common.web.dao.CommonMapper)")
//    public void implCommonMapper() {}
//
//    @Pointcut("@annotation(indi.aby.docm.api.operlog.annotation.OperLog)")
//    public void withOperLogAnnotation() {}


    /*================ 声明通知 ===================*/

    /**
     * 新增-操作日志
     * @param pjp
     */
    @Around("addInDao()")
    public void writeOperLog4Add(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args[0] instanceof List) {
            return;
        }
        OperResult operResult = OperResult.SUCCESS;
        try {
            pjp.proceed();
        } catch (Throwable t) {
            operResult = OperResult.FAILED;
            throw t;
        } finally {
            OperLogWriterHelper.write4Add((AbstractEntity) args[0], operResult);
        }
    }

    /**
     * 登录-操作日志
     * @param pjp
     * @throws Throwable
     */
    @Around("execution(indi.aby.docm.api.auth.UserSummaryVO indi.aby.docm.core..*.*.login(..))")
    public void writeOperLog4Login(ProceedingJoinPoint pjp) throws Throwable {
        Object val;
        Object[] args = pjp.getArgs();
        OperResult operResult = OperResult.SUCCESS;
        String content = null;
        try {
            val = pjp.proceed();
            content = "{\"data\": \"" + ((UserSummaryVO)val).toString() + "\"}";
        } catch (Throwable t) {
            if (t instanceof BizException) {
                operResult = OperResult.FAILED;
                content = "{\"data\": \"" + ((UserVO)args[0]).toString()
                        + "\", \"error\": \"" + ((BizException) t).getMessage()
                        + "\"}";
            }
            throw t;
        } finally {
            OperLogWriterHelper.write4Login(OperName.LOGIN, operResult,
                    ResourceUtil.getString("module.name", "auth"), content);
        }
    }

//    @Before("execution(public * indi.aby.docm..*.dao.*.add(..))")
//    public void writeAdd() {
//        log.info("记录日志。。。writeAdd");
//    }
//
//    @Before("execution(public * indi.aby.docm..*.dao.*.deleteAll(..))")
//    public void writeDelete() {
//        log.info("记录日志。。。writeDelete");
//    }
}
