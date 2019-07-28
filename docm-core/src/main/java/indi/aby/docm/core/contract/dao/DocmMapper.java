package indi.aby.docm.core.contract.dao;

import indi.aby.docm.core.contract.entity.DocmEntity;
import indi.rui.common.web.dao.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DocmMapper extends CommonMapper<DocmEntity> {
}
