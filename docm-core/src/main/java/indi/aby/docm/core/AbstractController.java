package indi.aby.docm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import indi.rui.common.base.dto.*;
import indi.rui.common.web.IApi;

/**
 * 公共controller
 *
 * @author: yaowr
 * @create: 2019-09-07
 */
public abstract class AbstractController<A extends IApi<V>, V extends AbstractVO> {
    @Autowired
    protected A api;

    /**
     * 新增
     * @param vo
     * @return
     */
    @PutMapping
    public Response add(@RequestBody V vo) {
        api.add(vo);
        return Response.ok();
    }

    /**
     * 编辑
     * @param vo
     * @return
     */
    @PostMapping
    public Response<?> edit(@RequestBody V vo) {
        api.edit(vo);
        return Response.ok();
    }

    /**
     * 列表分页
     * @param queryRequest
     * @return
     */
    @PostMapping("list")
    public Response<QueryResult<V>> list(@RequestBody QueryRequest<V> queryRequest) {
        return Response.ok(api.list(queryRequest));
    }

    /**
     * 详情
     * @param idVO
     * @return
     */
    @GetMapping
    public Response<V> get(@ModelAttribute IdVO idVO) {
        return Response.ok(api.get(idVO));
    }

    /**
     * 删除
     * @param idsVO
     * @return
     */
    @DeleteMapping
    public Response<?> delete(@ModelAttribute IdsVO idsVO) {
        api.delete(idsVO);
        return Response.ok();
    }
}
