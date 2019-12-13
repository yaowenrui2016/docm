package indi.aby.docm.core.contract;

import indi.aby.docm.core.operlog.constant.OperName;
import indi.aby.docm.core.operlog.annotation.OperLog;
import indi.aby.docm.core.permission.annotation.Permission;
import indi.rui.common.base.dto.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 合同管理controller
 */
@RestController
@RequestMapping("docm")
public class ContractController {
    @Getter
    @Setter
    @Autowired
    private IContractApi contractApi;

    @PutMapping
    @OperLog(name = OperName.ADD, module = "contract")
    @Permission(id = "DOCM_ADD_OPER", name = "项目合同_新建权限", validator = "simplePermValidator", module = "项目合同", desc = "新建合同")
    public Response<?> add(@RequestBody ContractVO vo) {
        getContractApi().add(vo);
        return Response.ok();
    }

    @PostMapping
    @OperLog(name = OperName.UPDATE, module = "contract")
    @Permission(id = "DOCM_EDIT_OPER", name = "项目合同_编辑权限", validator = "simplePermValidator", module = "项目合同", desc = "编辑合同")
    public Response<?> edit(@RequestBody ContractVO contractVO) {
        getContractApi().edit(contractVO);
        return Response.ok();
    }

    @PostMapping("list")
    @OperLog(name = OperName.LIST, module = "contract")
    @Permission(id = "DOCM_LIST_VIEW", name = "项目合同_查询列表权限", validator = "simplePermValidator", module = "项目合同", desc = "可以查询所有科室的合同列表")
    @Permission(id = "DOCM_LIST_DEPT_VIEW", name = "项目合同_仅本科室查询列表权限", validator = "docmListByDept", module = "项目合同", desc = "仅可以查询本科室的合同列表")
    public Response<QueryResult<ContractVO>> list(@RequestBody QueryRequest queryRequest) {
        return Response.ok(getContractApi().list(queryRequest));
    }

    @GetMapping
    @OperLog(name = OperName.VIEW, module = "contract")
    @Permission(id = "DOCM_DETAIL_VIEW", name = "项目合同_查看详情权限", validator = "simplePermValidator", module = "项目合同", desc = "查看合同的详情")
    public Response<ContractVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(getContractApi().get(idVO));
    }

    @DeleteMapping
    @OperLog(name = OperName.DELETE, module = "contract")
    @Permission(id = "DOCM_DELETE_OPER", name = "项目合同_删除权限", validator = "simplePermValidator", module = "项目合同", desc = "删除合同记录（包括上传的文件）")
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        getContractApi().delete(idsVO);
        return Response.ok();
    }

    @GetMapping("type/list")
    public Response<?> types() {
        return Response.ok(getContractApi().getAllType());
    }

    @PostMapping("contract-num/list")
    public Response<?> contractNums(@RequestBody ContractVO vo) {
        return Response.ok(getContractApi().findAllContractNum(vo.getContractNum()));
    }


    //---------付款项----------//

    @PutMapping("pay-item")
    @OperLog(name = OperName.ADD, module = "contract")
    @Permission(id = "DOCM_EDIT_OPER", name = "项目合同_编辑权限", validator = "simplePermValidator", module = "项目合同", desc = "编辑合同")
    public Response<?> addPayItem(@RequestBody PayItemVO vo) {
        getContractApi().addPayItem(vo);
        return Response.ok();
    }

    @PostMapping("pay-item")
    @OperLog(name = OperName.ADD, module = "contract")
    @Permission(id = "DOCM_EDIT_OPER", name = "项目合同_编辑权限", validator = "simplePermValidator", module = "项目合同", desc = "编辑合同")
    public Response<?> editPayItem(@RequestBody PayItemVO vo) {
        getContractApi().editPayItem(vo);
        return Response.ok();
    }

    @DeleteMapping("pay-item")
    @OperLog(name = OperName.DELETE, module = "contract")
    @Permission(id = "DOCM_EDIT_OPER", name = "项目合同_编辑权限", validator = "simplePermValidator", module = "项目合同", desc = "编辑合同")
    public Response<?> deletePayItem(@ModelAttribute IdsVO idsVO) {
        getContractApi().deletePayItem(idsVO);
        return Response.ok();
    }
}
