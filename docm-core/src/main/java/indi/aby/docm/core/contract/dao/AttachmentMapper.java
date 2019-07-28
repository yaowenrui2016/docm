package indi.aby.docm.core.contract.dao;

import indi.aby.docm.core.contract.entity.AttachmentEntity;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.web.dao.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    void add(@Param("attachments")  List<AttachmentEntity> attachments);
    void update(AttachmentEntity attachmentEntity);
    Integer findTotalNum(QueryRequest queryRequest);
    List<AttachmentEntity> findAll(QueryRequest queryRequest);
    List<AttachmentEntity> findById(IFieldId iFieldId);
    void delete(IFieldId iFieldId);

    AttachmentEntity findByPath(String docPath, String docName);
}
