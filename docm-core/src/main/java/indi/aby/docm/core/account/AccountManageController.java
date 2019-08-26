package indi.aby.docm.core.account;

import indi.aby.docm.api.account.IAccountManageServiceApi;
import indi.aby.docm.api.account.UserVO;
import indi.aby.docm.api.operlog.OperName;
import indi.aby.docm.api.operlog.annotation.OperLog;
import indi.aby.docm.api.permission.Permission;
import indi.rui.common.base.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Permission(id = "ACCOUNT_MGT_MANAGE", name = "账号管理_模块权限", module = "账号管理", desc = "账号管理的模块权限")
public class AccountManageController {
    @Autowired
    private IAccountManageServiceApi accountManageServiceApi;

    @PutMapping
    public Response add(@RequestBody UserVO userVO) {
        accountManageServiceApi.add(userVO);
        return Response.ok();
    }

    @PostMapping
    @OperLog(name = OperName.UPDATE, module = "account")
    public Response<?> edit(@RequestBody UserVO userVO) {
        accountManageServiceApi.edit(userVO);
        return Response.ok();
    }

    @PostMapping("list")
    public Response<QueryResult<UserVO>> list(@RequestBody QueryRequest<UserVO> queryRequest) {
        return Response.ok(accountManageServiceApi.list(queryRequest));
    }

    @GetMapping
    public Response<UserVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(accountManageServiceApi.get(idVO));
    }

    @DeleteMapping
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        accountManageServiceApi.delete(idsVO);
        return Response.ok();
    }

    @PostMapping("freeze")
    public Response<?> freeze(@RequestBody IdsVO idsVO) {
        accountManageServiceApi.freeze(idsVO, true);
        return Response.ok();
    }

    @PostMapping("unfreeze")
    public Response<?> unfreeze(@RequestBody IdsVO idsVO) {
        accountManageServiceApi.freeze(idsVO, false);
        return Response.ok();
    }

    @GetMapping("cku-username")
    public Response<?> checkUniqueUsername(@ModelAttribute UserVO userVO) {
        return Response.ok(accountManageServiceApi.checkUniqueUsername(userVO));
    }
}
