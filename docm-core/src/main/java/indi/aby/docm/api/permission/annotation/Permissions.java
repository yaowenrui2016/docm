package indi.aby.docm.api.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解，支持重复注解定义多个权限
 *
 * @author: yaowr
 * @create: 2019-09-12
 */
@Target(value = { ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permissions {
    Permission[] value();
}
