package indi.aby.docm.core.contract;

import indi.rui.common.web.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 付款项Entity
 * @author: yaowr
 * @create: 2019-11-11
 */
@Getter
@Setter
public class PayItemEntity extends BaseEntity {

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
}
