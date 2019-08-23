package indi.aby.docm.api.permission;

import indi.rui.common.base.dto.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionGroupedVO extends AbstractVO {
    private String group;
    private List<PermissionVO> permissions;
}
