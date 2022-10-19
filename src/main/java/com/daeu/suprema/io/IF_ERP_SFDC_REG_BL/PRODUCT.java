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

    public PRODUCT() { }

    public PRODUCT(Map<String, Object> p) {
        this.productId = p.get("SFDC_ORDERPRODUCTID").toString();
        this.productHsCode = p.get("HS_NO_D").toString();
    }
}
