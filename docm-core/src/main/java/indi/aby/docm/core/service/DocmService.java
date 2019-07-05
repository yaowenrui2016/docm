package indi.aby.docm.core.service;

import indi.aby.docm.api.IDocmServiceApi;
import indi.aby.docm.api.dto.DocmVO;
import indi.aby.docm.core.dao.DocmMapper;
import indi.aby.docm.core.entity.DocmEntity;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.base.util.RandomUtil;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DocmService extends AbstractService implements IDocmServiceApi {
    @Autowired
    private DocmMapper docmMapper;

    @Override
    public void add(DocmVO docmVO) {
        DocmEntity entity = new DocmEntity();
        voToEntity(docmVO, entity);
        entity.setId(RandomUtil.uuid());
        docmMapper.add(entity);
        log.info("Add a docm success");
    }

    @Override
    public void edit(DocmVO docmVO) {

    }

    @Override
    public List<DocmVO> list(QueryRequest queryRequest) {
        return null;
    }

    @Override
    public DocmVO get(IFieldId fieldId) {
        return null;
    }

    @Override
    public void delete(IFieldId fieldId) {

    }

    @Override
    public void batchedDelete(IFieldIds idsVO) {

    }
}
