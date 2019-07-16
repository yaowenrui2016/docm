package indi.aby.docm.api.util;

import indi.rui.common.base.dto.Status;

public enum ErrorCode implements Status {
    USERNAME_OR_PASSWORD_WRONG("03001001", "用户名或密码错误"),
    CREATING_FILE_DIRECTORY_FAILED("03001002", "创建文件目录失败"),
    EXCEPTION("99999999", "服务器异常");

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
