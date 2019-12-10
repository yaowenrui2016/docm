package indi.aby.docm.core.contract;

import java.math.BigDecimal;

import indi.rui.common.web.BaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 付款项VO
 * @author: yaowr
 * @create: 2019-11-11
 */
@Getter
@Setter
public class PayItemVO extends BaseVO {
    /**
     * 1.序号
     */
    private Integer order;

    /**
     * 2.金额
     */
    private BigDecimal money;

    /**
     * 3.凭证号
     */
    private String credentialNum;

    /**
     * 4.凭证时间
     */
    private String credentialTime;

    /**
     * 5.付款时间
     */
    private String payTime;

    /**
     * 6.所属合同
     */
    private String contractId;

    /**
     * 7.备注
     */
    private String desc;
}
