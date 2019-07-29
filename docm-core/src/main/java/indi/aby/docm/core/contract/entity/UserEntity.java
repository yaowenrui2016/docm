package indi.aby.docm.core.contract.entity;

import indi.aby.docm.core.permission.entity.PermissionEntity;
import indi.rui.common.base.dto.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String phone;
    private String email;
    private List<PermissionEntity> permissions;
    private Boolean activate;
    private Boolean frozen;
}
