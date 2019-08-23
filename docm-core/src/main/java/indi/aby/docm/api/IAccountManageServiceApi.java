package indi.aby.docm.api;

import indi.aby.docm.api.account.UserVO;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.web.service.IApi;

public interface IAccountManageServiceApi extends IApi<UserVO> {
    void freeze(IFieldIds fieldIds, boolean doFreeze);
    Boolean checkUniqueUsername(UserVO userVO);
}
