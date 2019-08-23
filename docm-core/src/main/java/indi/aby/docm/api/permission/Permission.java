package indi.aby.docm.api.permission;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String id();

    String module() default "";

    String desc() default "";
}
