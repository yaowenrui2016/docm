package indi.aby.docm.core.dao;

import indi.aby.docm.core.entity.DocmEntity;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.IdsVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DocmMapper {
    void add(DocmEntity docmEntity);
    void update(DocmEntity docmEntity);
    Integer findTotalNum(QueryRequest queryRequest);
    List<DocmEntity> findAll(QueryRequest queryRequest);
    DocmEntity findById(IFieldId iFieldId);
    void delete(IFieldId iFieldId);
}
