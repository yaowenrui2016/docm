package indi.aby.docm.core.account;

import java.util.List;

import indi.aby.docm.api.account.DeptVO;
import indi.aby.docm.api.permission.PermissionVO;
import indi.rui.common.web.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体
 *
 * @author: yaowr
 * @create: 2019-09-06
 */
@Getter
@Setter
public class UserEntity extends BaseEntity {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（MD5加密)
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属科室
     */
    private DeptVO dept;

    /**
     * 拥有权限
     */
    private List<PermissionVO> permissions;

    /**
     * 是否激活
     */
    private Boolean activate;

    /**
     * 是否冻结
     */
    private Boolean frozen;
}
