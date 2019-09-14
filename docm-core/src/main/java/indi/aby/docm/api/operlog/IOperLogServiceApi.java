package indi.aby.docm.api.operlog;

import indi.aby.docm.api.operlog.dto.OperLogVO;
import indi.rui.common.web.IApi;

import java.util.List;

public interface IOperLogServiceApi extends IApi<OperLogVO> {

    List<String> getAllName();
}
