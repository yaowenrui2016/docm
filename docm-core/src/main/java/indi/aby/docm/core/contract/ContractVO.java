package indi.aby.docm.core.contract;

import indi.aby.docm.core.account.DeptVO;
import indi.rui.common.web.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 合同VO
 */
@Getter
@Setter
public class ContractVO extends BaseVO {
    /**
     * 1.项目名称
     */
    private String projectName;

    /**
     * 2.项目类型
     */
    private String projectType;

    /**
     * 3.乙方（公司名称）
     */
    private String company;

    /**
     * 4.合同号
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
     * 7.付款项
     */
    private List<PayItemEntity> payItems;

    /**
     * 8.总金额
     */
    private BigDecimal money;

    /**
     * 9.附件
     */
    private List<AttachmentVO> attachments;

    /**
     * 10.备注
     */
    private String desc;
}
