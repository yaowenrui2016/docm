package indi.aby.docm.core.account;

import indi.aby.docm.api.account.IAccountManageServiceApi;
import indi.aby.docm.api.account.UserVO;
import indi.aby.docm.api.operlog.annotation.OperLog;
import indi.aby.docm.api.operlog.constant.OperName;
import indi.rui.common.base.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 账号管理controller
 *
 * @author: yaowr
 * @create: 2019-09-06
 */
@RestController
@RequestMapping("user")
public class AccountManageController {
    @Autowired
    private IAccountManageServiceApi accountManageServiceApi;

    /**
     * 新增
     * @param userVO
     * @return
     */
    @PutMapping
    @OperLog(name = OperName.ADD, module = "account")
    public Response add(@RequestBody UserVO userVO) {
        accountManageServiceApi.add(userVO);
        return Response.ok();
    }

    /**
     * 编辑
     * @param userVO
     * @return
     */
    @PostMapping
    @OperLog(name = OperName.UPDATE, module = "account")
    public Response<?> edit(@RequestBody UserVO userVO) {
        accountManageServiceApi.edit(userVO);
        return Response.ok();
    }

    /**
     * 列表分页
     * @param queryRequest
     * @return
     */
    @PostMapping("list")
    @OperLog(name = OperName.LIST, module = "account")
    public Response<QueryResult<UserVO>> list(@RequestBody QueryRequest queryRequest) {
        return Response.ok(accountManageServiceApi.list(queryRequest));
    }

    /**
     * 详情
     * @param idVO
     * @return
     */
    @GetMapping
    @OperLog(name = OperName.VIEW, module = "account")
    public Response<UserVO> get(@ModelAttribute IdVO idVO) {
        return Response.ok(accountManageServiceApi.get(idVO));
    }

    /**
     * 删除
     * @param idsVO
     * @return
     */
    @DeleteMapping
    @OperLog(name = OperName.DELETE, module = "account")
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        accountManageServiceApi.delete(idsVO);
        return Response.ok();
    }

    /**
     * 冻结
     * @param idsVO
     * @return
     */
    @PostMapping("freeze")
    @OperLog(name = OperName.FREEZE, module = "account")
    public Response<?> freeze(@RequestBody IdsVO idsVO) {
        accountManageServiceApi.freeze(idsVO, true);
        return Response.ok();
    }

    /**
     * 解冻
     * @param idsVO
     * @return
     */
    @PostMapping("unfreeze")
    @OperLog(name = OperName.UN_FREEZE, module = "account")
    public Response<?> unfreeze(@RequestBody IdsVO idsVO) {
        accountManageServiceApi.freeze(idsVO, false);
        return Response.ok();
    }

    /**
     * 用户名唯一校验
     * @param userVO
     * @return
     */
    @GetMapping("check-unique-username")
    public Response<Boolean> checkUniqueUsername(@ModelAttribute UserVO userVO) {
        return Response.ok(accountManageServiceApi.checkUniqueUsername(userVO));
    }

    /**
     * 给用户选择器专用
     * @param queryRequest
     * @return
     */
    @PostMapping("search")
    public Response<QueryResult<UserVO>> search(@RequestBody QueryRequest queryRequest) {
        return Response.ok(accountManageServiceApi.list(queryRequest));
    }
}
