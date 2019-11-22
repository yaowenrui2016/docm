package indi.aby.docm.core.account;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import indi.aby.docm.core.AbstractController;
import indi.rui.common.base.dto.Response;

/**
 * 科室controller
 *
 * @author: yaowr
 * @create: 2019-09-07
 */
@RestController
@RequestMapping("dept")
public class DeptController extends AbstractController<IDeptApi, DeptVO> {
    /**
     * 查询所有科室
     * @return
     */
    @PostMapping("list-all")
    public Response<List<DeptVO>> list() {
        return Response.ok(api.list());
    }

    /**
     * 检查是否被引用
     * @param vo
     * @return
     */
    @GetMapping("check-ref")
    public Response<Boolean> checkRef(@ModelAttribute DeptVO vo) {
        return Response.ok(api.checkRef(vo));
    }
}
