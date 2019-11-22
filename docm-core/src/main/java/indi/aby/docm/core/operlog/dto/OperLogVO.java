package indi.aby.docm.core.operlog.dto;

import indi.rui.common.web.BaseVO;
import lombok.*;

@Getter
@Setter
public class OperLogVO extends BaseVO {
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
