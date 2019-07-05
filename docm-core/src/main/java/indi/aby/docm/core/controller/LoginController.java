package indi.aby.docm.core.controller;

import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private IUserServiceApi userServiceApi;

    @PostMapping("login")
    public Response login(UserVO userVO) {
        userServiceApi.get(userVO);
        return Response.ok();
    }
}
