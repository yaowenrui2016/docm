package indi.aby.docm.api.dto;

import indi.rui.common.base.dto.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentVO extends BaseVO {
    private String docPath;
    private String docName;
}
