package indi.aby.docm.core.service;

import indi.aby.docm.api.IDocmServiceApi;
import indi.aby.docm.api.dto.AttachmentVO;
import indi.aby.docm.api.dto.DocmVO;
import indi.aby.docm.core.dao.AttachmentMapper;
import indi.aby.docm.core.dao.DocmMapper;
import indi.aby.docm.core.entity.AttachmentEntity;
import indi.aby.docm.core.entity.DocmEntity;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.QueryResult;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.RandomUtil;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocmService extends AbstractService implements IDocmServiceApi {
    @Autowired
    private DocmMapper docmMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Transactional
    @Override
    public void add(DocmVO docmVO) {
        DocmEntity doc = copyProperties(docmVO, DocmEntity.class);
        doc.setId(RandomUtil.uuid());
        saveAttachments(doc);
        docmMapper.add(doc);
    }

    @Override
    public void edit(DocmVO docmVO) {
        DocmEntity doc = copyProperties(docmVO, DocmEntity.class);
        saveAttachments(doc);
        docmMapper.update(doc);
    }

    private void saveAttachments(DocmEntity doc) {
        attachmentMapper.delete(doc);
        List<AttachmentEntity> attachments = copyPropertiesForList(doc.getAttachments(), AttachmentEntity.class);
        if (!CollectionUtils.isEmpty(attachments)) {
            attachmentMapper.add(attachments.stream().map(attachment -> {
                attachment.setId(RandomUtil.uuid());
                attachment.setDocmId(doc.getId());
                return attachment;
            }).collect(Collectors.toList()));
        }
    }

    @Override
    public QueryResult<DocmVO> list(QueryRequest queryRequest) {
        Integer total = docmMapper.findTotalNum(queryRequest);
        List<DocmVO> vos = copyPropertiesForList(
                docmMapper.findAll(queryRequest), DocmVO.class);
        return new QueryResult<>(total, vos);
    }

    @Override
    public DocmVO get(IFieldId fieldId) {
        DocmVO doc = copyProperties(docmMapper.findById(fieldId), DocmVO.class);
        doc.setAttachments(copyPropertiesForList(attachmentMapper.findById(doc), AttachmentVO.class));
        return doc;
    }

    @Transactional
    @Override
    public void delete(IFieldIds fieldIds) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                attachmentMapper.delete(IdVO.ofId(id));
                docmMapper.delete(IdVO.ofId(id));
            }
        }
    }
}
