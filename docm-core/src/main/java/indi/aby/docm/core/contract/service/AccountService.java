package indi.aby.docm.core.contract.service;

import indi.aby.docm.api.IAccountServiceApi;
import indi.aby.docm.api.dto.PermissionVO;
import indi.aby.docm.api.dto.UserModPwdVO;
import indi.aby.docm.api.dto.UserSummaryVO;
import indi.aby.docm.api.util.ErrorCode;
import indi.aby.docm.core.contract.dao.UserMapper;
import indi.aby.docm.core.contract.entity.UserEntity;
import indi.rui.common.base.dto.DefaultStatus;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.web.exception.BizException;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountService extends AbstractService implements IAccountServiceApi {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserSummaryVO get(IFieldId fieldId) {
        UserEntity entity = userMapper.findById(fieldId);
        if (entity == null) {
            throw new BizException(DefaultStatus.RECORD_NOT_EXISTS);
        }
        UserSummaryVO vo = copyProperties(entity, UserSummaryVO.class);
        vo.setPermissions(copyPropertiesForList(userMapper.findCorrelatedPermission(fieldId), PermissionVO.class));
        return vo;
    }

    @Transactional
    @Override
    public void modPwd(UserModPwdVO userModPwdVO) {
        UserEntity userEntity = userMapper.findById(userModPwdVO);
        if (userEntity == null) {
            throw new BizException(DefaultStatus.RECORD_NOT_EXISTS);
        }
        if (!userEntity.getPassword().equals(userModPwdVO.getOldPassword())) {
            throw new BizException(ErrorCode.OLD_PASSWORD_WRONG);
        }
        if (userEntity.getPassword().equals(userModPwdVO.getPassword())) {
            throw new BizException(ErrorCode.CANNOT_EQUALS_OLD_PASSWORD);
        }
        UserEntity entity = copyProperties(userModPwdVO, UserEntity.class);
        userMapper.update(entity);
    }
}
