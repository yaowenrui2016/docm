package indi.aby.docm.core.service;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.aby.docm.core.dao.UserMapper;
import indi.aby.docm.core.entity.UserEntity;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.QueryResult;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.RandomUtil;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService extends AbstractService implements IUserServiceApi {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(UserVO userVO) {
        UserEntity entity = copyProperties(userVO, UserEntity.class);
        entity.setId(RandomUtil.nextUserId());
        entity.setActivate(true);
        userMapper.add(entity);
    }

    @Override
    public void edit(UserVO userVO) {
        Map<String,Object> nullAbles = userVO.getNullAbles();
        nullAbles.put("phone", true);
        nullAbles.put("email", true);
        update(userVO);
    }

    protected void update(UserVO userVO) {
        UserEntity entity = copyProperties(userVO, UserEntity.class);
        userMapper.update(entity);
    }

    @Override
    public QueryResult<UserVO> list(QueryRequest queryRequest) {
        Integer total = userMapper.findTotalNum(queryRequest);
        List<UserVO> vos = copyPropertiesForList(
                userMapper.findAll(queryRequest), UserVO.class, "password");
        return new QueryResult<>(total, vos);
    }

    @Override
    public UserVO get(IFieldId fieldId) {
        UserVO doc = copyProperties(userMapper.findById(fieldId), UserVO.class, "password");
        return doc;
    }

    @Transactional
    @Override
    public void delete(IFieldIds fieldIds) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                userMapper.delete(IdVO.ofId(id));
            }
        }
    }

    @Transactional
    @Override
    public void freeze(IFieldIds fieldIds, boolean doFreeze) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                UserVO userVO = new UserVO();
                userVO.setId(id);
                userVO.setFrozen(doFreeze);
                update(userVO);
            }
        }
    }
}
