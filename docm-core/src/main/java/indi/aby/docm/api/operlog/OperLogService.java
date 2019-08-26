package indi.aby.docm.api.operlog;

import indi.rui.common.web.AbstractService;
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
