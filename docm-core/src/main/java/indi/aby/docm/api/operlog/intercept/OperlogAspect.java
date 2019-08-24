package indi.aby.docm.api.operlog.intercept;

import indi.aby.docm.api.account.UserVO;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.aby.docm.api.operlog.OperLogWriterHelper;
import indi.aby.docm.api.operlog.OperName;
import indi.aby.docm.api.operlog.OperResult;
import indi.aby.docm.util.ResourceUtil;
import indi.rui.common.base.dto.AbstractEntity;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class OperlogAspect {

    @Pointcut("execution(public * *(..))")
    public void anyPublicPT() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.*Mapper.add(..))")
    public void addInMapperPT() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.*Mapper.update(..))")
    public void updateInMapperPT() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.*Mapper.findAll(..))")
    public void findAllInMapperPT() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.*Mapper.findById(..))")
    public void findByIdInMapperPT() {}

    @Pointcut("execution(public * indi.aby.docm.core..*.*Mapper.deleteAll(..))")
    public void deleteAllInMapperPT() {}

    @Pointcut("execution(indi.aby.docm.api.auth.UserSummaryVO indi.aby.docm.core..*.*.login(..))")
    public void loginPT() {}

    @Pointcut("within(indi.aby.docm..*)")
    public void withinDocmPT() {}

//    @Pointcut("target(indi.rui.common.web.dao.IMapper)")
//    public void implCommonMapper() {}
//
//    @Pointcut("@annotation(indi.aby.docm.api.operlog.annotation.OperLog)")
//    public void withOperLogAnnotation() {}


    /*================ 声明通知 ===================*/

    /**
     * 新增-操作日志
     * @param pjp
     */
    @Around(value = "addInMapperPT() && args(entity)", argNames = "entity")
    public void writeOperLog4Add(ProceedingJoinPoint pjp, AbstractEntity entity) throws Throwable {
        OperResult operResult = OperResult.SUCCESS;
        String content = null;
        try {
            pjp.proceed();
            content = "{\"data\": " + entity.toString() + "}";
        } catch (Throwable t) {
            operResult = OperResult.FAILED;
            content = "{\"data\": " + entity.toString()
                    + ", \"error\": \"" + t.getMessage()
                    + "\"}";
            throw t;
        } finally {
            OperLogWriterHelper.write(OperName.ADD, operResult, entity.getModule(), content);
        }
    }

    /**
     * 查询-操作日志
     * @param pjp
     */
    @Around(value = "findAllInMapperPT() && args(queryRequest)", argNames = "queryRequest")
    public List writeOperLog4findAll(ProceedingJoinPoint pjp, QueryRequest queryRequest) throws Throwable {
        OperResult operResult = OperResult.SUCCESS;
        Map<Object, Object> content = new HashMap<>();
        try {
            content.put("param", queryRequest);
            List rtnList = (List) pjp.proceed();
            content.put("data", rtnList);
            return rtnList;
        } catch (Throwable t) {
            operResult = OperResult.FAILED;
            content.put("error", t.getMessage());
            throw t;
        } finally {
            OperLogWriterHelper.write(OperName.ADD, operResult, "", JsonUtil.encode(content));
        }
    }

    /**
     * 登录-操作日志
     * @param pjp
     * @throws Throwable
     */
    @Around(value = "loginPT() && args(vo,..)", argNames = "vo")
    public UserSummaryVO writeOperLog4Login(ProceedingJoinPoint pjp, UserVO vo) throws Throwable {
        OperResult operResult = OperResult.SUCCESS;
        String content = null;
        UserSummaryVO rtnVal;
        try {
            rtnVal = (UserSummaryVO) pjp.proceed();
            content = "{\"data\": " + (rtnVal).toString() + "}";
            return rtnVal;
        } catch (Throwable t) {
            operResult = OperResult.FAILED;
            content = "{\"data\": " + vo.toString()
                    + ", \"error\": \"" + t.getMessage()
                    + "\"}";
            throw t;
        } finally {
            OperLogWriterHelper.write(OperName.LOGIN, operResult,
                    ResourceUtil.getString("module.name", "auth"), content);
        }
    }
}
