package indi.aby.docm.core.permission;

import indi.rui.common.web.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionEntity extends BaseEntity {
    private String module;
    private String desc;
}

