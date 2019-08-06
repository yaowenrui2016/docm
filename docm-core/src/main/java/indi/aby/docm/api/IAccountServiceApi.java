package indi.aby.docm.api;

import indi.aby.docm.api.dto.UserModPwdVO;
import indi.aby.docm.api.dto.UserSummaryVO;
import indi.rui.common.base.field.IFieldId;

public interface IAccountServiceApi {
    UserSummaryVO get(IFieldId fieldId);
    void modPwd(UserModPwdVO userModPwdVO);
}
