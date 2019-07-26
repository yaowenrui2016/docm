package indi.aby.docm.api.dto;

import indi.rui.common.base.dto.BaseVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO extends BaseVO {
    private String username;
    private String password;
    private String phone;
    private String email;
    private boolean activate;
    private boolean frozen;
}
