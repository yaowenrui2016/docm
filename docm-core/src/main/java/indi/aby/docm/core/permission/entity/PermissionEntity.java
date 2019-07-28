package indi.aby.docm.core.permission.entity;

import indi.rui.common.base.dto.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionEntity extends BaseEntity {
    private String module;
    private String desc;
}

