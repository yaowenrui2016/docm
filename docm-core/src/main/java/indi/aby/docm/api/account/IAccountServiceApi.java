package indi.aby.docm.api.account;

import indi.aby.docm.api.account.UserModPwdVO;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.rui.common.base.field.IFieldId;

public interface IAccountServiceApi {
    UserSummaryVO get(IFieldId fieldId);
    void modPwd(UserModPwdVO userModPwdVO);
}
