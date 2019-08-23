package indi.aby.docm.api.operlog.support;

import indi.aby.docm.api.operlog.OperLogEntity;
import indi.aby.docm.api.operlog.OperLogMapper;
import indi.aby.docm.api.operlog.OperLogVO;
import indi.rui.common.web.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OperLogService extends AbstractService<OperLogMapper, OperLogEntity, OperLogVO> implements IOperLogServiceApi {
    @Autowired
    @Override
    protected void setMapper(OperLogMapper mapper) {
        this.mapper = mapper;
    }
}
