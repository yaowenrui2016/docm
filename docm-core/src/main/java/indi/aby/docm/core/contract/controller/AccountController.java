package indi.aby.docm.core.contract.controller;

import indi.aby.docm.api.IAccountServiceApi;
import indi.aby.docm.api.dto.UserModPwdVO;
import indi.aby.docm.api.dto.UserSummaryVO;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private IAccountServiceApi accountServiceApi;

    @GetMapping
    public Response<UserSummaryVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(accountServiceApi.get(idVO));
    }

    @PostMapping("mod-pwd")
    public Response<?> modPwd(@RequestBody UserModPwdVO userModPwdVO) {
        accountServiceApi.modPwd(userModPwdVO);
        return Response.ok();
    }
}
