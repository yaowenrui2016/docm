package indi.aby.docm.api.dto;

import indi.rui.common.base.dto.BaseVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModPwdVO extends BaseVO {
    private String oldPassword;
    private String password;
}
