package indi.aby.docm.core.service;

import indi.aby.docm.api.IDocmServiceApi;
import indi.aby.docm.api.dto.DocmVO;
import indi.aby.docm.core.dao.DocmMapper;
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
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class DocmService extends AbstractService implements IDocmServiceApi {
    @Autowired
    private DocmMapper docmMapper;

    @Override
    public void add(DocmVO docmVO) {
        DocmEntity entity = copyProperties(docmVO, DocmEntity.class);
        entity.setId(RandomUtil.uuid());
        docmMapper.add(entity);
    }

    @Override
    public void edit(DocmVO docmVO) {

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
        return copyProperties(docmMapper.findById(fieldId), DocmVO.class);
    }

    @Override
    public void delete(IFieldIds fieldIds) {
        List<String> ids = fieldIds.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                docmMapper.delete(IdVO.ofId(id));
            }
        }
    }
}
