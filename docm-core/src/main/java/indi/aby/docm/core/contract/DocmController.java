package indi.aby.docm.core.contract;

import indi.aby.docm.api.IDocmServiceApi;
import indi.aby.docm.api.contract.DocmVO;
import indi.aby.docm.api.permission.Permission;
import indi.rui.common.base.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("docm")
public class DocmController {
    @Autowired
    private IDocmServiceApi docmServiceApi;

    @PutMapping
    @Permission(id = "DOCM_ADD_OPER", name = "项目合同_新建权限", module = "项目合同", desc = "项目合同信息的录入操作")
    public Response<?> add(@RequestBody DocmVO docmVO) {
        docmServiceApi.add(docmVO);
        return Response.ok();
    }

    @PostMapping
    @Permission(id = "DOCM_EDIT_OPER", name = "项目合同_编辑权限", module = "项目合同", desc = "项目合同信息的编辑操作")
    public Response<?> edit(@RequestBody DocmVO docmVO) {
        docmServiceApi.edit(docmVO);
        return Response.ok();
    }

    @PostMapping("list")
    @Permission(id = "DOCM_LIST_VIEW", name = "项目合同_列表查看权限", module = "项目合同", desc = "项目合同列表的查看")
    public Response<QueryResult<DocmVO>> list(@RequestBody QueryRequest queryRequest) {
        return Response.ok(docmServiceApi.list(queryRequest));
    }

    @GetMapping
    @Permission(id = "DOCM_DETAIL_VIEW", name = "项目合同_详情查看权限", module = "项目合同", desc = "项目合同详情的查看")
    public Response<DocmVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(docmServiceApi.get(idVO));
    }

    @DeleteMapping
    @Permission(id = "DOCM_DELETE_OPER", name = "项目合同_删除权限", module = "项目合同", desc = "项目合同数据的删除操作")
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        docmServiceApi.delete(idsVO);
        return Response.ok();
    }
}