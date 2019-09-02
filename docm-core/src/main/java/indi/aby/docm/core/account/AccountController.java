package indi.aby.docm.core.account;

import indi.aby.docm.api.account.IAccountServiceApi;
import indi.aby.docm.api.account.UserModPwdVO;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.aby.docm.api.operlog.constant.OperName;
import indi.aby.docm.api.operlog.annotation.OperLog;
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
    @OperLog(name = OperName.MOD_PWD, module = "account")
    public Response<?> modPwd(@RequestBody UserModPwdVO userModPwdVO) {
        accountServiceApi.modPwd(userModPwdVO);
        return Response.ok();
    }
}
