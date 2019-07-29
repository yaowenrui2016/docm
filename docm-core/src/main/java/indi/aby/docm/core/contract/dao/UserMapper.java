package indi.aby.docm.core.contract.dao;

import indi.aby.docm.core.contract.entity.UserEntity;
import indi.aby.docm.core.permission.entity.PermissionEntity;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.web.dao.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends CommonMapper<UserEntity> {
    UserEntity findByUsername(String username);
    void addCorrelatedPermission(UserEntity userEntity);
    void deleteCorrelatedPermission(IFieldId fieldId);
    List<PermissionEntity> findCorrelatedPermission(IFieldId fieldId);
}
