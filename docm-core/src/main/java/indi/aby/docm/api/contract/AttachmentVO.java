package indi.aby.docm.api.contract;

import indi.rui.common.web.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 附件VO
 */
@Getter
@Setter
@NoArgsConstructor
public class AttachmentVO extends BaseVO {
    /**
     * 1.所属文档管理对象的id
     */
    private String docmId;

    /**
     * 2.文件路径
     */
    private String docPath;

    /**
     * 3.文件名
     */
    private String docName;

    public AttachmentVO(String docPath, String docName) {
        this.docPath = docPath;
        this.docName = docName;
    }
}
