package indi.aby.docm.api.contract;

import indi.rui.common.web.IApi;

import java.util.List;

/**
 * 合同api
 */
public interface IContractServiceApi extends IApi<ContractVO> {
    List<String> findAllType();
}
