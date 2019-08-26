package indi.aby.docm.api.account;

import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.web.IApi;

public interface IAccountManageServiceApi extends IApi<UserVO> {
    void freeze(IFieldIds fieldIds, boolean doFreeze);
    Boolean checkUniqueUsername(UserVO userVO);
}
