package indi.aby.docm.core.operlog.annotation;

import indi.aby.docm.core.operlog.constant.OperName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {
    /**
     * 操作名称
     * @return
     */
    OperName name();

    /**
     * 模块名称
     * @return
     */
    String module();
}
