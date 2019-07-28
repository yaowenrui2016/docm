package indi.aby.docm.core.contract.controller;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.aby.docm.core.auth.annotation.Permission;
import indi.rui.common.base.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Permission(id = "ACCOUNT_MANAGE", name = "账号_管理权限", module = "账号", desc = "账号模块的管理权限")
public class AccountController {
    @Autowired
    private IUserServiceApi userServiceApi;

    @PutMapping
    public Response add(@RequestBody UserVO userVO) {
        userServiceApi.add(userVO);
        return Response.ok();
    }

    @PostMapping
    public Response<?> edit(@RequestBody UserVO userVO) {
        userServiceApi.edit(userVO);
        return Response.ok();
    }

    @PostMapping("list")
    public Response<QueryResult<UserVO>> list(@RequestBody QueryRequest queryRequest) {
        return Response.ok(userServiceApi.list(queryRequest));
    }

    @GetMapping
    public Response<UserVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(userServiceApi.get(idVO));
    }

    @DeleteMapping
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        userServiceApi.delete(idsVO);
        return Response.ok();
    }

    @PostMapping("freeze")
    public Response<?> freeze(@RequestBody IdsVO idsVO) {
        userServiceApi.freeze(idsVO, true);
        return Response.ok();
    }

    @PostMapping("unfreeze")
    public Response<?> unfreeze(@RequestBody IdsVO idsVO) {
        userServiceApi.freeze(idsVO, false);
        return Response.ok();
    }
}
