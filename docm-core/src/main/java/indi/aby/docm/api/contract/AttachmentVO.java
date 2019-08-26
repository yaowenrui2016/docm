package indi.aby.docm.api.contract;

import indi.rui.common.web.BaseVO;
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
