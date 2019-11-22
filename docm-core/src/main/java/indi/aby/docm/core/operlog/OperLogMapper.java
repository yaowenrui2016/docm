package indi.aby.docm.core.operlog;

import indi.aby.docm.core.operlog.entity.OperLogEntity;
import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperLogMapper extends IMapper<OperLogEntity> {
}
