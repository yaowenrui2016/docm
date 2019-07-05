package indi.aby.docm.api;

import indi.aby.docm.api.dto.DocmVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.field.IFieldId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDocmServiceApi {
    void add(DocmVO docmVO);
    List<DocmVO> list(QueryRequest queryRequest);
    DocmVO get(IFieldId fieldId);
    void delete(IFieldId fieldId);
}
