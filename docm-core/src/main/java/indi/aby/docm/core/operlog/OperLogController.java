package indi.aby.docm.core.operlog;

import indi.aby.docm.api.operlog.IOperLogServiceApi;
import indi.aby.docm.api.operlog.dto.OperLogVO;
import indi.aby.docm.core.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("operlog")
public class OperLogController extends AbstractController<IOperLogServiceApi, OperLogVO> {
}
