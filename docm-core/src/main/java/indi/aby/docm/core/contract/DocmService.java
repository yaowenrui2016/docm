package indi.aby.docm.core.contract;

import indi.aby.docm.api.contract.AttachmentVO;
import indi.aby.docm.api.contract.DocmVO;
import indi.aby.docm.api.contract.IDocmServiceApi;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.RandomUtil;
import indi.rui.common.base.util.SnowflakeIDGenerator;
import indi.rui.common.web.AbstractService;
import indi.rui.common.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocmService extends AbstractService<DocmMapper, DocmEntity, DocmVO> implements IDocmServiceApi {
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Transactional
    @Override
    public void add(DocmVO docmVO) {
        DocmEntity doc = BeanUtil.copyProperties(docmVO, DocmEntity.class);
        doc.setId(String.valueOf(SnowflakeIDGenerator.genId()));
        saveAttachments(doc);
        mapper.add(doc);
    }

    @Override
    public void edit(DocmVO docmVO) {
        DocmEntity doc = BeanUtil.copyProperties(docmVO, DocmEntity.class);
        saveAttachments(doc);
        mapper.update(doc);
    }

    private void saveAttachments(DocmEntity doc) {
        attachmentMapper.delete(doc);
        List<AttachmentEntity> attachments = BeanUtil.copyPropertiesForList(doc.getAttachments(), AttachmentEntity.class);
        if (!CollectionUtils.isEmpty(attachments)) {
            attachmentMapper.add(attachments.stream().map(attachment -> {
                attachment.setId(String.valueOf(SnowflakeIDGenerator.genId()));
                attachment.setDocmId(doc.getId());
                return attachment;
            }).collect(Collectors.toList()));
        }
    }

//    @Override
//    public QueryResult<DocmVO> list(QueryRequest queryRequest) {
//        Integer total = mapper.findTotalNum(queryRequest);
//        List<DocmVO> vos = BeanUtil.copyPropertiesForList(
//                mapper.findAll(queryRequest), DocmVO.class);
//        return new QueryResult<>(total, vos);
//    }

    @Override
    public DocmVO get(IFieldId fieldId) {
        DocmVO doc = BeanUtil.copyProperties(mapper.findById(fieldId), DocmVO.class);
        doc.setAttachments(BeanUtil.copyPropertiesForList(attachmentMapper.findById(doc), AttachmentVO.class));
        return doc;
    }

    @Transactional
    @Override
    public void delete(IFieldIds fieldIds) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                attachmentMapper.delete(IdVO.ofId(id));
                mapper.delete(IdVO.ofId(id));
            }
        }
    }

    @Override
    @Autowired
    protected void setMapper(DocmMapper mapper) {
        this.mapper = mapper;
    }
}
