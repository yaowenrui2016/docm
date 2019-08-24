package indi.aby.docm.api.operlog;

import indi.rui.common.base.dto.AbstractVO;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class OperLogVO extends AbstractVO {
    private String module;
    private String result;
    private String operator;
    private String userAgent;
    private String ip;
    private String method;
    private String url;
    private Boolean status;
    private String content;
}
