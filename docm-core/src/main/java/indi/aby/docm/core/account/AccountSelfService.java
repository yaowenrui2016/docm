package indi.aby.docm.core.account;

import indi.aby.docm.core.permission.PermissionVO;
import indi.aby.docm.core.auth.UserSummaryVO;
import indi.aby.docm.util.ErrorCode;
import indi.rui.common.base.dto.DefaultStatus;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.util.StringUtil;
import indi.rui.common.web.exception.BizException;
import indi.rui.common.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号自助service
 *
 * @author: yaowr
 * @create: 2019-09-06
 */
@Slf4j
@Service
public class AccountSelfService implements IAccountServiceApi {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserSummaryVO get(IFieldId fieldId) {
        if (StringUtil.isEmpty(fieldId.getId())) {
            return UserHelper.getCurrentUser();
        }
        UserEntity entity = userMapper.findById(fieldId);
        if (entity == null) {
            throw new BizException(DefaultStatus.RECORD_NOT_EXISTS);
        }
        UserSummaryVO vo = BeanUtil.copyProperties(entity, UserSummaryVO.class);
        vo.setPermissions(BeanUtil.copyPropertiesForList(userMapper.findCorrelatedPermission(fieldId), PermissionVO.class));
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
        UserEntity entity = BeanUtil.copyProperties(userModPwdVO, UserEntity.class);
        userMapper.update(entity);
    }
}
