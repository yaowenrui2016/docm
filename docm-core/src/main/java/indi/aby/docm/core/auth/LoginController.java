package indi.aby.docm.core.auth;

import indi.aby.docm.api.auth.IAuthServiceApi;
import indi.aby.docm.api.account.UserVO;
import indi.aby.docm.api.operlog.annotation.OperLog;
import indi.aby.docm.api.operlog.constant.OperName;
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
    @OperLog(name = OperName.LOGIN, module = "auth")
    public Response login(@RequestBody UserVO userVO, HttpServletResponse servletResponse) {
        return Response.ok(loginServiceApi.login(userVO,servletResponse));
    }
}
