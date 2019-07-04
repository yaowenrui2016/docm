package indi.aby.docm.api;

import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.field.IFieldId;

import java.util.List;

public interface IUserServiceApi {
    void add(UserVO userVO);
    List<UserVO> list(QueryRequest queryRequest);
    UserVO get(IFieldId fieldId);
    void delete(IFieldId fieldId);
}
