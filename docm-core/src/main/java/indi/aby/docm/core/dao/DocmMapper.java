package indi.aby.docm.core.dao;

import indi.aby.docm.core.entity.DocmEntity;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.IdsVO;
import indi.rui.common.base.dto.QueryRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DocmMapper {
    void add(DocmEntity docmEntity);
    void update(DocmEntity docmEntity);
    List<DocmEntity> findAll(QueryRequest queryRequest);
    DocmEntity findById(IdVO idVO);
    void delete(IdVO idVO);
    void batchedDelete(IdsVO idsVO);
}
