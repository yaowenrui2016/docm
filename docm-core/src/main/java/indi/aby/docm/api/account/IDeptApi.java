package indi.aby.docm.api.account;

import indi.rui.common.web.IApi;

import java.util.List;

/**
 * 科室api
 *
 * @author: yaowr
 * @create: 2019-09-08
 */
public interface IDeptApi extends IApi<DeptVO> {
    /**
     * 查询所有科室
     * @return
     */
    List<DeptVO> list();

    /**
     * 名称唯一校验
     * @param vo
     * @return
     */
    Boolean checkUniqueName(DeptVO vo);
}
