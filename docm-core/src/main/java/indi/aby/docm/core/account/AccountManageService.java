package indi.aby.docm.core.account;

import indi.aby.docm.api.account.IAccountManageServiceApi;
import indi.aby.docm.api.permission.PermissionVO;
import indi.aby.docm.api.account.UserVO;
import indi.aby.docm.api.util.ErrorCode;
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

import static indi.aby.docm.constant.ApplicationConstant.CIPHER_PWD;

/**
 * 账号管理service
 *
 * @author: yaowr
 * @create: 2019-09-06
 */
@Slf4j
@Service
public class AccountManageService extends AbstractService<UserMapper, UserEntity, UserVO>
    implements IAccountManageServiceApi {
    @Override
    @Autowired
    protected void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void add(UserVO userVO) {
        if (checkUniqueUsername(userVO)) { // 用户名唯一校验
            throw new BizException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        UserEntity entity = BeanUtil.copyProperties(userVO, UserEntity.class);
        entity.setId(RandomUtil.nextUserId());
        entity.setActivate(true);
        saveCorrelatedPerm(entity);     // 保存权限
        mapper.add(entity);
    }

    @Transactional
    @Override
    public void edit(UserVO vo) {
        if (checkUniqueUsername(vo)) { // 用户名唯一校验
            throw new BizException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        UserEntity entity = BeanUtil.copyProperties(vo, UserEntity.class);
        Map<String, Object> nullAbles = entity.getNullAbles();
        nullAbles.put("phone", true);
        nullAbles.put("email", true);
        nullAbles.put("dept", true);
        saveCorrelatedPerm(entity);     // 保存权限
        mapper.update(entity);
    }

    private void saveCorrelatedPerm(UserEntity entity) {
        /* 保存权限是先清除旧的权限关联关系，然后插入新的权限关联关系 */
        mapper.deleteCorrelatedPermission(entity); // 按account_id删除关联的权限
        if (!CollectionUtils.isEmpty(entity.getPermissions())) {
            mapper.addCorrelatedPermission(entity);
        }
    }

    @Override
    public QueryResult<UserVO> list(QueryRequest request) {
        Integer total = mapper.findTotalNum(request);
        List<UserVO> vos = BeanUtil.copyPropertiesForList(mapper.findAll(request), UserVO.class, "password");
        return new QueryResult<>(request.getPageSize(), request.getCurrent(), total, vos);
    }

    @Override
    public UserVO get(IFieldId fieldId) {
        UserVO vo = super.get(fieldId);
        if (vo == null) {
            return null;
        }
        vo.setPassword(CIPHER_PWD);
        vo.setPermissions(BeanUtil.copyPropertiesForList(mapper.findCorrelatedPermission(fieldId), PermissionVO.class));
        return vo;
    }

    @Transactional
    @Override
    public void delete(IFieldIds fieldIds) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                mapper.deleteCorrelatedPermission(IdVO.ofId(id)); // 删除关联的权限
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
        return entity != null && (StringUtil.isEmpty(userVO.getId()) || !userVO.getId().equals(entity.getId()));
    }
}
