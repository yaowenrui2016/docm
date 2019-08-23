package indi.aby.docm.api.account;

import indi.rui.common.base.dto.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModPwdVO extends AbstractVO {
    private String oldPassword;
    private String password;
}
