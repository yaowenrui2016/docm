package indi.aby.docm.core.contract.entity;

import indi.rui.common.base.dto.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String phone;
    private String email;
    private Boolean activate;
    private Boolean frozen;
}