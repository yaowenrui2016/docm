package indi.aby.docm.core.dao;

import indi.aby.docm.core.entity.UserEntity;
import indi.rui.common.base.dto.QueryRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void add(UserEntity userEntity);
    List<UserEntity> list(QueryRequest queryRequest);
}
