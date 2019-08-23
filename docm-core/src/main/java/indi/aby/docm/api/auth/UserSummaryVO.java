package indi.aby.docm.api.auth;

import indi.aby.docm.api.permission.PermissionVO;
import indi.rui.common.base.dto.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryVO extends AbstractVO {
    private String username;
    private String phone;
    private String email;
    private List<PermissionVO> permissions;
    private Boolean activate;
}
