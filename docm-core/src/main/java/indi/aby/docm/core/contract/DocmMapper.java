package indi.aby.docm.core.contract;

import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DocmMapper extends IMapper<DocmEntity> {
    List<String> findAllType();
}
