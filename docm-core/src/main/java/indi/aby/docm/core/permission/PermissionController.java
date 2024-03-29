package indi.aby.docm.core.permission;

import indi.aby.docm.core.AbstractController;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("perm")
public class PermissionController extends AbstractController<IPermissionServiceApi, PermissionVO> {
    @Autowired
    private IPermissionServiceApi permissionServiceApi;

    @GetMapping("all")
    public Response<List<PermissionGroupedVO>> findAll() {
        return Response.ok(permissionServiceApi.findAll());
    }
}
