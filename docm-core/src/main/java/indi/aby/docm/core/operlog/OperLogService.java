package indi.aby.docm.core.operlog;

import indi.aby.docm.core.operlog.constant.OperName;
import indi.aby.docm.core.operlog.dto.OperLogVO;
import indi.aby.docm.core.operlog.entity.OperLogEntity;
import indi.rui.common.web.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OperLogService extends AbstractService<OperLogMapper, OperLogEntity, OperLogVO>
    implements IOperLogServiceApi {
    @Autowired
    @Override
    protected void setMapper(OperLogMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<String> getAllName() {
        return Arrays.stream(OperName.values()).map(operName -> operName.getValue()).collect(Collectors.toList());
    }
}
