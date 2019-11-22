package indi.aby.docm.core.permission.validator;

import indi.aby.docm.core.auth.UserSummaryVO;

/**
 * 权限校验器
 *
 * @author: yaowr
 * @create: 2019-09-12
 */
public interface PermissionValidator {
    /**
     * 校验通过返回true，否则返回false
     * @param permission
     * @param userInfo
     * @param param
     */
    boolean validate(String permission, UserSummaryVO userInfo, Object param) ;
}
