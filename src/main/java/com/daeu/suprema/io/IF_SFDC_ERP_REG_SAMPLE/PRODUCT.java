package com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE;

import lombok.Data;

@Data
public class PRODUCT {
    /**
     *  4-1. OrderProduct SFDC Key
     */
    private String orderProductId;

    /**
     *  4-2. 대표품목명코드
     */
    private String productCode;

    /**
     *  4-3. 요청 수량
     */
    private double quantity;
}
