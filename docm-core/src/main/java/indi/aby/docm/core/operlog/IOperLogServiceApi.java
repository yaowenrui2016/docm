package indi.aby.docm.core.operlog;

import indi.aby.docm.core.operlog.dto.OperLogVO;
import indi.rui.common.web.IApi;

import java.util.List;

public interface IOperLogServiceApi extends IApi<OperLogVO> {

    List<String> getAllName();
}
