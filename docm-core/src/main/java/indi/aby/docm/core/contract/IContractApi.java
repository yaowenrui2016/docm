package indi.aby.docm.core.contract;

import java.util.List;

import indi.rui.common.base.dto.IdsVO;
import indi.rui.common.web.IApi;

/**
 * 合同api
 */
public interface IContractApi extends IApi<ContractVO> {
    /**
     * 获取所有类型
     * @return
     */
    List<String> getAllType();

    /**
     * 新增付款项
     * @param vo
     */
    void addPayItem(PayItemVO vo);

    /**
     * 更新付款项
     * @param vo
     */
    void editPayItem(PayItemVO vo);

    /**
     * 删除付款项
     * @param idsVO
     */
    void deletePayItem(IdsVO idsVO);

    /**
     * 查询合同号
     * @param contractNum
     * @return
     */
    List<String> findAllContractNum(String contractNum);
}
