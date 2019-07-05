package indi.aby.docm.core.service;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.aby.docm.core.dao.UserMapper;
import indi.aby.docm.core.entity.UserEntity;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.RandomUtil;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService extends AbstractService implements IUserServiceApi {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(UserVO userVO) {
        UserEntity entity = new UserEntity();
        voToEntity(userVO, entity);
        entity.setId(RandomUtil.nextUserId());
        userMapper.add(entity);
        log.info("Add a user success");
    }

    @Override
    public void edit(UserVO userVO) {

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

    @Override
    public void batchedDelete(IFieldIds idsVO) {

    }
}
