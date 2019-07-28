package indi.aby.docm.core.contract.entity;

import indi.rui.common.base.dto.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentEntity extends BaseEntity {
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
}
