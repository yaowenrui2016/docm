package indi.aby.docm.api.permission;

import indi.aby.docm.api.account.UserHelper;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.rui.common.web.exception.NoPermissionException;
import indi.aby.docm.api.permission.validator.PermissionValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import indi.aby.docm.api.permission.annotation.Permission;
import indi.aby.docm.api.permission.annotation.Permissions;
import indi.rui.common.base.dto.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 权限拦截切面
 *
 * @author: yaowr
 * @create: 2019-09-12
 */
@Slf4j
@Aspect
@Component
public class PermissionAspect implements BeanFactoryAware {
    private BeanFactory beanFactory;

    /*================== 切入点 ===================*/
    @Pointcut("execution(public indi.rui.common.base.dto.Response indi.aby.docm..*.*Controller.*(..))")
    public void controllerPC() {}

    @Pointcut("execution(public org.springframework.http.ResponseEntity indi.aby.docm..*.*Controller.*(..))")
    public void controllerWithResponseEntityRtnPC() {}

    /*==================== 通知 ====================*/
    /**
     * 针对controller方法上多权限的拦截通知 如果权限校验失败则抛出 NoPermissionException
     * 
     * @param pjp
     */
    @Around(value = "controllerPC() && @annotation(permissions) && args(param)", argNames = "param,permissions")
    public Response perm4Multiple(ProceedingJoinPoint pjp, Object param, Permissions permissions) throws Throwable {
        UserSummaryVO userInfo = UserHelper.getCurrentUser();
        // 只要其中一个权限校验通过则通过，不在校验剩下的权限
        if (Arrays.stream(permissions.value()).noneMatch(permission -> {
            PermissionValidator validator = beanFactory.getBean(permission.validator(), PermissionValidator.class);
            return validator.validate(permission.id(), userInfo, param);
        })) {
            throw new NoPermissionException();
        }
        Response res = (Response)pjp.proceed();
        return res;
    }

    /**
     * 针对controller方法上单权限的拦截通知 如果权限校验失败则抛出 NoPermissionException
     * 
     * @param pjp
     */
    @Around(value = "controllerPC() && @annotation(permission) && args(param)", argNames = "param,permission")
    public Response perm4Single(ProceedingJoinPoint pjp, Object param, Permission permission) throws Throwable {
        UserSummaryVO userInfo = UserHelper.getCurrentUser();
        PermissionValidator validator = beanFactory.getBean(permission.validator(), PermissionValidator.class);
        if (!validator.validate(permission.id(), userInfo, param)) {
            throw new NoPermissionException();
        }
        Response res = (Response)pjp.proceed();
        return res;
    }

    /**
     * 针对下载的权限的拦截通知 如果权限校验失败则抛出 NoPermissionException
     * 
     * @param pjp
     */
    @Around(value = "controllerWithResponseEntityRtnPC() && @annotation(permission) && args(param,..)",
        argNames = "param,permission")
    public ResponseEntity perm4Download(ProceedingJoinPoint pjp, Object param, Permission permission) throws Throwable {
        UserSummaryVO userInfo = UserHelper.getCurrentUser();
        PermissionValidator validator = beanFactory.getBean(permission.validator(), PermissionValidator.class);
        if (!validator.validate(permission.id(), userInfo, param)) {
            throw new NoPermissionException();
        }
        ResponseEntity res = (ResponseEntity)pjp.proceed();
        return res;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
