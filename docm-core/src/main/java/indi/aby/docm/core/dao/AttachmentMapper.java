package indi.aby.docm.core.dao;

import indi.aby.docm.core.entity.AttachmentEntity;
import indi.aby.docm.core.entity.DocmEntity;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
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
