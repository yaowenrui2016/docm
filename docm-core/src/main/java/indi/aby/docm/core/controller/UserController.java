package indi.aby.docm.core.controller;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserServiceApi userServiceApi;

    @PostMapping("add")
    public Response add(UserVO userVO) {
        userServiceApi.add(userVO);
        return Response.ok();
    }
}
