package indi.aby.docm.core.permission;

import indi.aby.docm.api.dto.BaseEntity;
import indi.rui.common.base.dto.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionEntity extends BaseEntity {
    private String module;
    private String desc;
}

