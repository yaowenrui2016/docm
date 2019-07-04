package indi.aby.docm.core.controller;

import indi.rui.common.base.dto.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @PostMapping("add")
    public Response add() {
        return Response.ok();
    }
}
