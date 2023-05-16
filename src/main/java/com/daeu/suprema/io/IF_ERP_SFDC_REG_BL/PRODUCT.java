package com.daeu.suprema.io.IF_ERP_SFDC_REG_BL;

import lombok.Data;

import java.util.Map;

@Data
public class PRODUCT {
    /**
     *  1-11-1. 상품 ID (OrderProduct SFDC Key)
     */
    private String productId;

    /**
     *  1-11-2. 상품 H/S 코드
     */
    private String productHsCode;

    /**
     *  1-11-3. 상품 H/S 코드
     */
    private String productRemark;

    /**
     *  1-11-4. Record ID
     */
    private int recordId;

    public PRODUCT() { }

    public PRODUCT(Map<String, Object> p) {
        boolean flag = "D".equals(p.get("IF_ACT_CODE").toString());

        this.productId      = p.get("SFDC_ORDERPRODUCTID").toString();
        this.productHsCode  = flag || p.get("HS_CD_D") == null ? null : "*".equals(p.get("HS_CD_D").toString()) ? null : p.get("HS_CD_D").toString();
        this.productRemark  = flag || p.get("REMARK_DTL") == null ? null : p.get("REMARK_DTL").toString();
        this.recordId       = Integer.parseInt(p.get("IF_REC_ID").toString());
    }
}
