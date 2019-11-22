package indi.aby.docm.core.permission.annotation;

import java.lang.annotation.*;

/**
 * 权限注解，目前仅支持在方法上定义权限
 *
 * @author: yaowr
 * @create: 2019-09-12
 */
@Repeatable(Permissions.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    /**
     * 权限名称
     * @return
     */
    String name();

    /**
     * 权限id
     * @return
     */
    String id();

    /**
     * 所属模块
     * @return
     */
    String module();

    /**
     * 描述信息
     * @return
     */
    String desc() default "";

    /**
     * 权限校验器名字，即spring bean 的id
     * @return
     */
    String validator();
}
