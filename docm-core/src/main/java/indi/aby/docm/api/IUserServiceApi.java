package indi.aby.docm.api;

import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.web.service.IApi;

public interface IUserServiceApi extends IApi<UserVO> {
    void freeze(IFieldIds fieldIds, boolean doFreeze);
    Boolean checkUniqueUsername(UserVO userVO);
}
