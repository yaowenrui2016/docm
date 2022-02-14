package indi.aby.docm.core.contract;

import indi.aby.docm.util.ErrorCode;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.IdsVO;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.SnowflakeIDGenerator;
import indi.rui.common.web.AbstractService;
import indi.rui.common.web.exception.BizException;
import indi.rui.common.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Value("${docm.filePath:./tmp/upload}")
    private String filePath;

    @Override
    @Autowired
    protected void setMapper(ContractMapper mapper) {
        this.mapper = mapper;
    }

    private void checkUniqueProjectName(ContractVO vo) {
        ContractEntity entity = mapper.findByProjectName(vo.getProjectName());
        if (entity != null && !entity.getId().equals(vo.getId())) {
            throw new BizException(ErrorCode.PROJECT_NAME_EXIST);
        }
    }

    private void checkUniqueContractNum(ContractVO vo) {
        ContractEntity entity = mapper.findByContractNum(vo.getContractNum());
        if (entity != null && !entity.getId().equals(vo.getId())) {
            throw new BizException(ErrorCode.CONTRACT_NUM_EXIST);
        }
    }

    @Transactional
    @Override
    public void add(ContractVO vo) {
        checkUniqueProjectName(vo);
        checkUniqueContractNum(vo);
        ContractEntity entity = BeanUtil.copyProperties(vo, ContractEntity.class);
        entity.setId(String.valueOf(SnowflakeIDGenerator.genId()));
        /* 保存附件 */
        saveAttachments(entity);
        mapper.add(entity);
    }

    @Transactional
    @Override
    public void edit(ContractVO vo) {
        checkUniqueProjectName(vo);
        checkUniqueContractNum(vo);
        ContractEntity entity = BeanUtil.copyProperties(vo, ContractEntity.class);
        Map<String, Object> nullAbles = entity.getNullAbles();
        nullAbles.put("dept", true);
        /* 保存附件 */
        saveAttachments(entity);
        mapper.update(entity);
    }

    private void saveAttachments(ContractEntity entity) {
        /* 保存附件是先清除旧的附件，然后插入新增的附件 */
        attachmentMapper.deleteByDocmId(entity); // 按docm_id删除附件
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
                attachmentMapper.deleteByDocmId(IdVO.ofId(id));
                mapper.delete(IdVO.ofId(id));
            }
        }
    }

    @Override
    public List<String> getAllType() {
        return mapper.findAllType();
    }

    @Override
    public List<String> findAllContractNum(String contractNum) {
        return mapper.findAllContractNum(contractNum);
    }

    @Override
    public void addPayItem(PayItemVO vo) {
        vo.setId(String.valueOf(SnowflakeIDGenerator.genId()));
        payItemMapper.add(BeanUtil.copyProperties(vo, PayItemEntity.class));
    }

    @Override
    public void editPayItem(PayItemVO vo) {
        payItemMapper.update(BeanUtil.copyProperties(vo, PayItemEntity.class));
    }

    @Override
    public void deletePayItem(IdsVO idsVO) {
        if (!CollectionUtils.isEmpty(idsVO.getIds())) {
            for (String id : idsVO.getIds()) {
                payItemMapper.delete(IdVO.ofId(id));
            }
        }
    }
}
