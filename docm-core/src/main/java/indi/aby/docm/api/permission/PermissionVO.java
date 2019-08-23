package indi.aby.docm.api.permission;

import indi.rui.common.base.dto.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionVO extends AbstractVO {
    private String module;
    private String desc;
}
