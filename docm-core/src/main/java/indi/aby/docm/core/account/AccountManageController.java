package indi.aby.docm.core.account;

import indi.aby.docm.api.account.IAccountManageServiceApi;
import indi.aby.docm.api.account.UserVO;
import indi.aby.docm.api.operlog.annotation.OperLog;
import indi.aby.docm.api.operlog.constant.OperName;
import indi.rui.common.base.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class AccountManageController {
    @Autowired
    private IAccountManageServiceApi accountManageServiceApi;

    @PutMapping
    @OperLog(name = OperName.ADD, module = "account")
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
    @OperLog(name = OperName.LIST, module = "account")
    public Response<QueryResult<UserVO>> list(@RequestBody QueryRequest<UserVO> queryRequest) {
        return Response.ok(accountManageServiceApi.list(queryRequest));
    }

    @GetMapping
    @OperLog(name = OperName.VIEW, module = "account")
    public Response<UserVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(accountManageServiceApi.get(idVO));
    }

    @DeleteMapping
    @OperLog(name = OperName.DELETE, module = "account")
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        accountManageServiceApi.delete(idsVO);
        return Response.ok();
    }

    @PostMapping("freeze")
    @OperLog(name = OperName.FREEZE, module = "account")
    public Response<?> freeze(@RequestBody IdsVO idsVO) {
        accountManageServiceApi.freeze(idsVO, true);
        return Response.ok();
    }

    @PostMapping("unfreeze")
    @OperLog(name = OperName.UN_FREEZE, module = "account")
    public Response<?> unfreeze(@RequestBody IdsVO idsVO) {
        accountManageServiceApi.freeze(idsVO, false);
        return Response.ok();
    }

    @GetMapping("cku-username")
    public Response<?> checkUniqueUsername(@ModelAttribute UserVO userVO) {
        return Response.ok(accountManageServiceApi.checkUniqueUsername(userVO));
    }
}
