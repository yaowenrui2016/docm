package indi.aby.docm.api.dto;

import indi.rui.common.base.dto.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryVO extends BaseVO {
    private String username;
    private String phone;
    private String email;
}
