package indi.aby.docm.core.dao;

import indi.aby.docm.core.entity.DocmEntity;
import indi.rui.common.base.dto.QueryRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DocmMapper {
    void insert();
    List<DocmEntity> findAll(QueryRequest queryRequest);
    DocmEntity findById(String id);
    void update(DocmEntity docmEntity);
}
