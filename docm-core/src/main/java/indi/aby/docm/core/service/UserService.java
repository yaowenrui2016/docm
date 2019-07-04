package indi.aby.docm.core.service;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.field.IFieldId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserServiceApi {
    @Override
    public void add(UserVO userVO) {

    }

    @Override
    public List<UserVO> list(QueryRequest queryRequest) {
        return null;
    }

    @Override
    public UserVO get(IFieldId fieldId) {
        return null;
    }

    @Override
    public void delete(IFieldId fieldId) {

    }
}
