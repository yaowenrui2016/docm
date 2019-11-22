package indi.aby.docm.core.permission.validator;

import org.springframework.stereotype.Component;

import indi.aby.docm.core.auth.UserSummaryVO;

/**
 * 简单的权限校验器
 *
 * @author: yaowr
 * @create: 2019-09-12
 */
@Component("simplePermValidator")
public class SimplePermValidator extends AbstractPermValidator implements PermissionValidator {
    @Override
    public boolean validate(String permission, UserSummaryVO userInfo, Object param) {
        return hasPermission(permission, userInfo);
    }
}
