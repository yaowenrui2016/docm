package indi.aby.docm.api.dto;

import indi.rui.common.base.dto.BaseVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionVO extends BaseVO {
    private String module;
    private String desc;
}
