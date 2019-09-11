package indi.aby.docm.api.contract;

import indi.rui.common.web.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentVO extends BaseVO {
    /**
     * 所属文档管理对象的id
     */
    private String docmId;

    /**
     * 文件路径
     */
    private String docPath;

    /**
     * 文件名
     */
    private String docName;

    public AttachmentVO(String docPath, String docName) {
        this.docPath = docPath;
        this.docName = docName;
    }
}
