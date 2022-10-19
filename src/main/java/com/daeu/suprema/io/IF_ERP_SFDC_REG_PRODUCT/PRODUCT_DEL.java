package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRODUCT;

import lombok.Data;

import java.util.Map;

@Data
public class PRODUCT_DEL {
    /**
     *  1-1. 대표품목명코드
     */
    private String productCode;

    public PRODUCT_DEL() { }

    public PRODUCT_DEL(Map<String, Object> p) {
        this.productCode = p.get("S_ITEM_CD").toString();
    }
}
