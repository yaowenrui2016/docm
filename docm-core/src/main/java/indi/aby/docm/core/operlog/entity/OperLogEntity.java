package indi.aby.docm.core.operlog.entity;

import indi.rui.common.base.dto.AbstractEntity;
import lombok.*;

@Getter
@Setter
public class OperLogEntity extends AbstractEntity {
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
