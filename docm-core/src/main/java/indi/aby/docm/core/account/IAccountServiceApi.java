package indi.aby.docm.core.account;

import indi.aby.docm.core.auth.UserSummaryVO;
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
