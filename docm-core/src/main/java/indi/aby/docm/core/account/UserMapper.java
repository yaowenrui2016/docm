package indi.aby.docm.core.account;

import indi.aby.docm.core.permission.PermissionEntity;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户mapper
 *
 * @author: yaowr
 * @create: 2019-09-06
 */
@Mapper
public interface UserMapper extends IMapper<UserEntity> {
    UserEntity findByUsername(String username);
    void addCorrelatedPermission(UserEntity userEntity);
    void deleteCorrelatedPermission(IFieldId fieldId);
    List<PermissionEntity> findCorrelatedPermission(IFieldId fieldId);
}
