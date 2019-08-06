package indi.aby.docm.api.dto;

import indi.rui.common.base.dto.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionGroupedVO extends BaseVO {
    private String group;
    private List<PermissionVO> permissions;
}
