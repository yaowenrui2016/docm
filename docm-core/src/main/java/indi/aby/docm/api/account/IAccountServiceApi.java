package indi.aby.docm.api.account;

import indi.aby.docm.api.auth.UserSummaryVO;
import indi.rui.common.base.field.IFieldId;

public interface IAccountServiceApi {
    /**
     * 登录时加载用户
     * @param fieldId
     * @return
     */
    UserSummaryVO get(IFieldId fieldId);
    void modPwd(UserModPwdVO userModPwdVO);
}
