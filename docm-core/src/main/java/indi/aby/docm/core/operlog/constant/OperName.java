package indi.aby.docm.core.operlog.constant;

import indi.rui.common.base.dto.IEnum;
import lombok.Getter;

public enum OperName implements IEnum {
    ADD("新建"),
    LIST("查询"),
    VIEW("查看"),
    UPDATE("修改"),
    DELETE("删除"),
    UPLOAD("上传"),
    DOWNLOAD("下载"),
    LOGIN("登录"),
    MOD_PWD("修改密码"),
    FREEZE("冻结"),
    UN_FREEZE("解冻");

    @Getter
    private String value;

    OperName(String value) {
        this.value = value;
    }
}
