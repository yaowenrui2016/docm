package indi.aby.docm.core.contract;

import indi.rui.common.web.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 附件实体
 */
@Getter
@Setter
public class AttachmentEntity extends BaseEntity {
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
}
