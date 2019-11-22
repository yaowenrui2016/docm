package indi.aby.docm.core.contract;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.SnowflakeIDGenerator;
import indi.rui.common.web.AbstractService;
import indi.rui.common.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 合同管理service
 */
@Slf4j
@Service
public class ContractService
        extends AbstractService<ContractMapper, ContractEntity, ContractVO>
        implements IContractApi {
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private PayItemMapper payItemMapper;

    @Override
    @Autowired
    protected void setMapper(ContractMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void add(ContractVO contractVO) {
        ContractEntity entity = BeanUtil.copyProperties(contractVO, ContractEntity.class);
        entity.setId(String.valueOf(SnowflakeIDGenerator.genId()));
        /* 保存附件 */
        saveAttachments(entity);
        mapper.add(entity);
    }

    @Transactional
    @Override
    public void edit(ContractVO vo) {
        ContractEntity entity = BeanUtil.copyProperties(vo, ContractEntity.class);
        Map<String, Object> nullAbles = entity.getNullAbles();
        nullAbles.put("dept", true);
        /* 保存附件 */
        saveAttachments(entity);
        mapper.update(entity);
    }

    private void saveAttachments(ContractEntity entity) {
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
    public List<String> getAllType() {
        return mapper.findAllType();
    }

    @Override
    public void addPayItem(PayItemVO vo) {
        payItemMapper.add(BeanUtil.copyProperties(vo, PayItemEntity.class));
    }
}
