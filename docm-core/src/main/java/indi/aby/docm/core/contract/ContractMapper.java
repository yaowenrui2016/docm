package indi.aby.docm.core.contract;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合同Mapper
 */
@Mapper
public interface ContractMapper extends IMapper<ContractEntity> {
    List<String> findAllType();

    List<String> findAllContractNum(@Param("contractNum") String contractNum);

    ContractEntity findByProjectName(@Param("projectName") String projectName);

    ContractEntity findByContractNum(@Param("contractNum") String contractNum);
}
