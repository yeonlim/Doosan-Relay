package com.daeu.suprema.io.IF_ERP_SFDC_REG_BL;

import lombok.Data;

import java.util.Map;

@Data
public class PRODUCT {
    /**
     *  1-9-1. 상품 ID (OrderProduct SFDC Key)
     */
    private String productId;

    /**
     *  1-9-2. 상품 H/S 코드
     */
    private String productHsCode;

    /**
     *  1-9-3. Record ID
     */
    private int recordId;

    public PRODUCT() { }

    public PRODUCT(Map<String, Object> p) {
        boolean flag = "D".equals(p.get("IF_ACT_CODE").toString());

        this.productId = p.get("SFDC_ORDERPRODUCTID").toString();
        this.productHsCode = flag ? null : p.get("HS_CD_D").toString();
        this.recordId = Integer.parseInt(p.get("IF_REC_ID").toString());
    }
}
