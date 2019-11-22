package indi.aby.docm.core.permission.validator;

import java.util.List;
import java.util.Objects;

import indi.aby.docm.core.auth.UserSummaryVO;
import indi.aby.docm.core.permission.PermissionVO;

/**
 * 权限校验器的抽象实现类
 *
 * @author: yaowr
 * @create: 2019-09-12
 */
public abstract class AbstractPermValidator implements PermissionValidator {
    /**
     * 当前用户是否拥有指定权限
     * @param permission
     * @param userInfo
     * @return
     */
    protected boolean hasPermission(String permission, UserSummaryVO userInfo) {
        Objects.requireNonNull(userInfo);
        List<PermissionVO> perms = userInfo.getPermissions();
        return perms != null && perms.stream().anyMatch(perm -> permission.equals(perm.getId()));
    }
}
