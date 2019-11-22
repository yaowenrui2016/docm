package indi.aby.docm.core.permission;

import indi.rui.common.web.BaseVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionVO extends BaseVO {
    private String module;
    private String desc;
}
