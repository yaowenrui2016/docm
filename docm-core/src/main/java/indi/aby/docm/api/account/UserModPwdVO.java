package indi.aby.docm.api.account;

import indi.rui.common.web.BaseVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModPwdVO extends BaseVO {
    private String oldPassword;
    private String password;
}
