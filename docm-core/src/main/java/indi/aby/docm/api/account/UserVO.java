package indi.aby.docm.api.account;

import indi.aby.docm.api.permission.PermissionVO;
import indi.rui.common.base.dto.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class UserVO extends AbstractVO {
    private String username;
    private String password;
    private String phone;
    private String email;
    private List<PermissionVO> permissions;
    private Boolean activate;
    private Boolean frozen;
}
