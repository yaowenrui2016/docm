package indi.aby.docm.core.contract.controller;

import indi.aby.docm.api.IAuthServiceApi;
import indi.aby.docm.api.dto.UserVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("account")
public class LoginController {
    @Autowired
    private IAuthServiceApi loginServiceApi;

    @PostMapping("login")
    public Response login(@RequestBody UserVO userVO, HttpServletResponse servletResponse) {
        return Response.ok(loginServiceApi.login(userVO,servletResponse));
    }
}
