package indi.aby.docm.core.controller;

import indi.aby.docm.api.ILoginServiceApi;
import indi.aby.docm.api.IUserServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @Autowired
    private ILoginServiceApi loginServiceApi;

    @PostMapping("login")
    public Response login(@RequestBody UserVO userVO, HttpServletResponse servletResponse) {
        return Response.ok(loginServiceApi.login(userVO,servletResponse));
    }
}
