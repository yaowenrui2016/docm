package indi.aby.docm.core.operlog;

import indi.aby.docm.api.operlog.IOperLogServiceApi;
import indi.aby.docm.api.operlog.dto.OperLogVO;
import indi.aby.docm.api.permission.PermissionGroupedVO;
import indi.aby.docm.core.AbstractController;
import indi.rui.common.base.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("operlog")
public class OperLogController extends AbstractController<IOperLogServiceApi, OperLogVO> {
    /**
     * 获取所有操作名称
     * @return
     */
    @GetMapping("name/list-all")
    public Response<List<PermissionGroupedVO>> getAllName() {
        return Response.ok(api.getAllName());
    }
}
