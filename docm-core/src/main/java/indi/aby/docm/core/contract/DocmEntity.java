package indi.aby.docm.core.contract;

import indi.rui.common.web.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class DocmEntity extends BaseEntity {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 公司名称
     */
    private String company;

    /**
     *  合同号
     */
    private String contractNum;

    /**
     * 合同签订时间
     */
    private String contractTime;

    /**
     * 凭证号
     */
    private String credentialNum;

    /**
     * 凭证时间
     */
    private String credentialTime;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 附件
     */
    private List<AttachmentEntity> attachments;
}
