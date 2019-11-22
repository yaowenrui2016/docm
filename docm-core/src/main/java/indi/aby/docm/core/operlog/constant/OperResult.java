package indi.aby.docm.core.operlog.constant;

import indi.rui.common.base.dto.IEnum;
import lombok.Getter;

public enum OperResult implements IEnum {
    SUCCESS("成功"),
    FAILED("失败"),
    ;

    @Getter
    private String value;

    OperResult(String value) {
        this.value = value;
    }
}
