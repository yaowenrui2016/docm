package indi.aby.docm.api.permission.exception;

import indi.aby.docm.api.util.ErrorCode;
import indi.rui.common.web.exception.BizException;

/**
 * 无访问权限异常
 *
 * @author: yaowr
 * @create: 2019-09-12
 */
public class NoPermissionException extends BizException {
    public NoPermissionException() {
        super(ErrorCode.NO_PERMISSION);
    }
}
