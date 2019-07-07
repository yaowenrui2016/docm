package indi.aby.docm.core.controller;

import indi.aby.docm.api.IDocmServiceApi;
import indi.aby.docm.api.dto.DocmVO;
import indi.rui.common.base.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("docm")
public class DocmController {
    @Autowired
    private IDocmServiceApi docmServiceApi;

    @PutMapping
    public Response<?> add(@RequestBody DocmVO docmVO) {
        docmServiceApi.add(docmVO);
        return Response.ok();
    }

    @PostMapping
    public Response<?> edit(@RequestBody DocmVO docmVO) {
        docmServiceApi.edit(docmVO);
        return Response.ok();
    }

    @PostMapping("list")
    public Response<QueryResult<DocmVO>> list(@RequestBody QueryRequest queryRequest) {
        return Response.ok(docmServiceApi.list(queryRequest));
    }

    @GetMapping
    public Response<DocmVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(docmServiceApi.get(idVO));
    }

    @DeleteMapping
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        docmServiceApi.delete(idsVO);
        return Response.ok();
    }
}
