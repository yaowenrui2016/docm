package indi.aby.docm.core.contract;

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

    /**
     * 4.文件类型
     */
    private String type;

    /**
     * 5.文件大小
     */
    private Long size;

    /**
     * 6.文件MD5
     */
    private String md5;

    public AttachmentVO(String docPath, String docName) {
        this.docPath = docPath;
        this.docName = docName;
    }
}
