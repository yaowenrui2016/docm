package indi.aby.docm.core.service;

import indi.aby.docm.api.IDocmServiceApi;
import indi.aby.docm.api.dto.DocmVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.field.IFieldId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocmService implements IDocmServiceApi {
    @Override
    public void add(DocmVO docmVO) {

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
}
