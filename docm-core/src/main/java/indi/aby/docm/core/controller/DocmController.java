package indi.aby.docm.core.controller;

import indi.rui.common.base.dto.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("docm")
public class DocmController {
    @PostMapping("list")
    public Response list() {
        return Response.ok();
    }
}
