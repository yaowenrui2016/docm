package indi.aby.docm.api.dto;

import indi.rui.common.base.dto.BaseVO;
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
