package indi.aby.docm.core.account;

import indi.aby.docm.api.account.IAccountManageServiceApi;
import indi.aby.docm.api.permission.PermissionVO;
import indi.aby.docm.api.account.UserVO;
import indi.rui.common.base.dto.DefaultStatus;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.QueryResult;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.RandomUtil;
import indi.rui.common.base.util.StringUtil;
import indi.rui.common.web.exception.BizException;
import indi.rui.common.web.AbstractService;
import indi.rui.common.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AccountManageService extends AbstractService<UserMapper, UserEntity, UserVO> implements IAccountManageServiceApi {
    @Transactional
    @Override
    public void add(UserVO userVO) {
        UserEntity entity = BeanUtil.copyProperties(userVO, UserEntity.class);
        entity.setId(RandomUtil.nextUserId());
        entity.setActivate(true);
        if (!CollectionUtils.isEmpty(entity.getPermissions())) {
            mapper.addCorrelatedPermission(entity); // 保存关联的权限
        }
        mapper.add(entity);
    }

    @Override
    public void edit(UserVO userVO) {
        Map<String,Object> nullAbles = userVO.getNullAbles();
        nullAbles.put("phone", true);
        nullAbles.put("email", true);
        UserEntity entity = BeanUtil.copyProperties(userVO, UserEntity.class);
        mapper.deleteCorrelatedPermission(entity);  // 删除关联的权限
        if (!CollectionUtils.isEmpty(entity.getPermissions())) {
            mapper.addCorrelatedPermission(entity); // 保存关联的权限
        }
        mapper.update(entity);
    }

    @Override
    public QueryResult<UserVO> list(QueryRequest queryRequest) {
        Integer total = mapper.findTotalNum(queryRequest);
        List<UserVO> vos = BeanUtil.copyPropertiesForList(
                mapper.findAll(queryRequest), UserVO.class, "password");
        return new QueryResult<>(total, vos);
    }

    @Override
    public UserVO get(IFieldId fieldId) {
        UserEntity entity = mapper.findById(fieldId);
        if (entity == null) {
            throw new BizException(DefaultStatus.RECORD_NOT_EXISTS);
        }
        UserVO vo = BeanUtil.copyProperties(entity, UserVO.class, "password");
        vo.setPermissions(BeanUtil.copyPropertiesForList(mapper.findCorrelatedPermission(fieldId), PermissionVO.class));
        return vo;
    }

    @Transactional
    @Override
    public void delete(IFieldIds fieldIds) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                mapper.deleteCorrelatedPermission(IdVO.ofId(id));  // 删除关联的权限
                mapper.delete(IdVO.ofId(id));
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
                mapper.update(BeanUtil.copyProperties(userVO, UserEntity.class));
            }
        }
    }

    @Override
    public Boolean checkUniqueUsername(UserVO userVO) {
        UserEntity entity = mapper.findByUsername(userVO.getUsername());
        return entity != null &&
                (StringUtil.isEmpty(userVO.getId()) || !userVO.getId().equals(entity.getId()));
    }

    @Override
    @Autowired
    protected void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }
}
