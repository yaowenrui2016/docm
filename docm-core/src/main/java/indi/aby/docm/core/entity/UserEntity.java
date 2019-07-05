package indi.aby.docm.core.entity;

import indi.rui.common.base.dto.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String phone;
}
