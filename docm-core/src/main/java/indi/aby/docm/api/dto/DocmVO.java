package indi.aby.docm.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DocmVO {
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

    private String docName;

    private String docPath;
}
