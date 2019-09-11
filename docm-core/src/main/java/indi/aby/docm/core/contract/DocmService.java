package indi.aby.docm.core.contract;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import indi.aby.docm.api.contract.AttachmentVO;
import indi.aby.docm.api.contract.DocmVO;
import indi.aby.docm.api.contract.IDocmServiceApi;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.SnowflakeIDGenerator;
import indi.rui.common.web.AbstractService;
import indi.rui.common.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocmService extends AbstractService<DocmMapper, DocmEntity, DocmVO> implements IDocmServiceApi {
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    @Autowired
    protected void setMapper(DocmMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void add(DocmVO docmVO) {
        DocmEntity entity = BeanUtil.copyProperties(docmVO, DocmEntity.class);
        entity.setId(String.valueOf(SnowflakeIDGenerator.genId()));
        saveAttachments(entity); // 保存附件
        mapper.add(entity);
    }

    @Transactional
    @Override
    public void edit(DocmVO vo) {
        DocmEntity entity = BeanUtil.copyProperties(vo, DocmEntity.class);
        Map<String, Object> nullAbles = entity.getNullAbles();
        nullAbles.put("dept", true);
        saveAttachments(entity); // 保存附件
        mapper.update(entity);
    }

    private void saveAttachments(DocmEntity entity) {
        /* 保存附件是先清除旧的附件，然后插入新增的附件 */
        attachmentMapper.delete(entity); // 按docm_id删除附件
        if (!CollectionUtils.isEmpty(entity.getAttachments())) {
            List<AttachmentVO> attaches = entity.getAttachments().stream().map(attachment -> {
                attachment.setId(String.valueOf(SnowflakeIDGenerator.genId()));
                attachment.setDocmId(entity.getId());
                return attachment;
            }).collect(Collectors.toList());
            attachmentMapper.add(BeanUtil.copyPropertiesForList(attaches, AttachmentEntity.class));
        }
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
    public List<String> findAllType() {
        return mapper.findAllType();
    }
}
