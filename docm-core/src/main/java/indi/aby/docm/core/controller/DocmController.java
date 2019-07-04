package indi.aby.docm.core.controller;

import indi.aby.docm.api.IDocmServiceApi;
import indi.aby.docm.api.dto.DocmVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("docm")
public class DocmController {
    @Autowired
    private IDocmServiceApi docmServiceApi;

    @PostMapping("list")
    public Response<DocmVO> list(QueryRequest queryRequest) {
        return Response.ok(docmServiceApi.list(queryRequest));
    }
}
