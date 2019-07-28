package indi.aby.docm.core.contract.dao;

import indi.aby.docm.core.contract.entity.UserEntity;
import indi.rui.common.web.dao.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends CommonMapper<UserEntity> {
    UserEntity findByUsername(String username);
}
