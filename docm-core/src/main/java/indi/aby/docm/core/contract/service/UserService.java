package indi.aby.docm.core.contract.service;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.PermissionVO;
import indi.aby.docm.api.dto.UserVO;
import indi.aby.docm.core.contract.dao.UserMapper;
import indi.aby.docm.core.contract.entity.UserEntity;
import indi.rui.common.base.dto.DefaultStatus;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.QueryResult;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.RandomUtil;
import indi.rui.common.base.util.StringUtil;
import indi.rui.common.web.exception.BizException;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService extends AbstractService implements IUserServiceApi {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void add(UserVO userVO) {
        UserEntity entity = copyProperties(userVO, UserEntity.class);
        entity.setId(RandomUtil.nextUserId());
        entity.setActivate(true);
        if (!CollectionUtils.isEmpty(entity.getPermissions())) {
            userMapper.addCorrelatedPermission(entity); // 保存关联的权限
        }
        userMapper.add(entity);
    }

    @Override
    public void edit(UserVO userVO) {
        Map<String,Object> nullAbles = userVO.getNullAbles();
        nullAbles.put("phone", true);
        nullAbles.put("email", true);
        UserEntity entity = copyProperties(userVO, UserEntity.class);
        userMapper.deleteCorrelatedPermission(entity);  // 删除关联的权限
        if (!CollectionUtils.isEmpty(entity.getPermissions())) {
            userMapper.addCorrelatedPermission(entity); // 保存关联的权限
        }
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
        UserEntity entity = userMapper.findById(fieldId);
        if (entity == null) {
            throw new BizException(DefaultStatus.RECORD_NOT_EXISTS);
        }
        UserVO vo = copyProperties(entity, UserVO.class, "password");
        vo.setPermissions(copyPropertiesForList(userMapper.findCorrelatedPermission(fieldId), PermissionVO.class));
        return vo;
    }

    @Transactional
    @Override
    public void delete(IFieldIds fieldIds) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                userMapper.deleteCorrelatedPermission(IdVO.ofId(id));  // 删除关联的权限
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
                userMapper.update(copyProperties(userVO, UserEntity.class));
            }
        }
    }

    @Override
    public Boolean checkUniqueUsername(UserVO userVO) {
        UserEntity entity = userMapper.findByUsername(userVO.getUsername());
        return entity != null &&
                (StringUtil.isEmpty(userVO.getId()) || !userVO.getId().equals(entity.getId()));
    }
}
