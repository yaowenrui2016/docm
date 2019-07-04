package indi.aby.docm.core.controller;

import com.rui.common.base.dto.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("login")
    public Response login() {
        return Response.ok();
    }
}
