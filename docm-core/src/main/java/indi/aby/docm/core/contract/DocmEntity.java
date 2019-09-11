package indi.aby.docm.core.contract;

import java.math.BigDecimal;
import java.util.List;

import indi.aby.docm.api.account.DeptVO;
import indi.aby.docm.api.contract.AttachmentVO;
import indi.rui.common.web.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocmEntity extends BaseEntity {
    /**
     * 1.项目名称
     */
    private String projectName;

    /**
     * 2.项目类型
     */
    private String projectType;

    /**
     * 3.公司名称
     */
    private String company;

    /**
     *  4.合同号
     */
    private String contractNum;

    /**
     * 5.科室
     */
    private DeptVO dept;

    /**
     * 6.合同签订时间
     */
    private String contractTime;

    /**
     * 7.凭证号
     */
    private String credentialNum;

    /**
     * 8.凭证时间
     */
    private String credentialTime;

    /**
     * 9.金额
     */
    private BigDecimal money;

    /**
     * 10.附件
     */
    private List<AttachmentVO> attachments;
}
