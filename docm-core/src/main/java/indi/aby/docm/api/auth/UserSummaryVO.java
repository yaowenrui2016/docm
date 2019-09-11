package indi.aby.docm.api.auth;

import indi.aby.docm.api.account.DeptVO;
import indi.rui.common.web.BaseVO;
import indi.aby.docm.api.permission.PermissionVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryVO extends BaseVO {
    private String username;
    private String phone;
    private String email;
    private DeptVO dept;
    private List<PermissionVO> permissions;
    private Boolean activate;
}
