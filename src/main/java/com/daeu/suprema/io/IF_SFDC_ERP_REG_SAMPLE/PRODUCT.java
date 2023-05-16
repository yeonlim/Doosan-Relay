package com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE;

import lombok.Data;

@Data
public class PRODUCT {
    /**
     *  6-1. OrderProduct SFDC Key
     */
    private String orderProductId;

    /**
     *  6-2. 대표품목명코드
     */
    private String productCode;

    /**
     *  6-3. 요청 수량
     */
    private double quantity;
}
