package indi.aby.docm.core.controller;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.DocmVO;
import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
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
}
