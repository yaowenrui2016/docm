package indi.aby.docm.core.dao;

import indi.aby.docm.core.entity.UserEntity;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void add(UserEntity entity);
    void update(UserEntity entity);
    Integer findTotalNum(QueryRequest queryRequest);
    List<UserEntity> findAll(QueryRequest queryRequest);
    UserEntity findById(IFieldId iFieldId);
    void delete(IFieldId iFieldId);;

    UserEntity findByUsername(String username);
}
