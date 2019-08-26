package indi.aby.docm.api.account;

import indi.rui.common.web.BaseVO;
import indi.aby.docm.api.permission.PermissionVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserVO extends BaseVO {
    private String username;
    private String password;
    private String phone;
    private String email;
    private List<PermissionVO> permissions;
    private Boolean activate;
    private Boolean frozen;
}
