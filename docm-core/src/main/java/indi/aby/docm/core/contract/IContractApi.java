package indi.aby.docm.core.contract;

import indi.rui.common.web.IApi;

import java.util.List;

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
}
