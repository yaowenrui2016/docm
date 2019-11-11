package indi.aby.docm.core.contract;

import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合同Mapper
 */
@Mapper
public interface ContractMapper extends IMapper<ContractEntity> {
    List<String> findAllType();
}
