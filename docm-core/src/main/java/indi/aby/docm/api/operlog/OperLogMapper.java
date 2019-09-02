package indi.aby.docm.api.operlog;

import indi.aby.docm.api.operlog.entity.OperLogEntity;
import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperLogMapper extends IMapper<OperLogEntity> {
}
