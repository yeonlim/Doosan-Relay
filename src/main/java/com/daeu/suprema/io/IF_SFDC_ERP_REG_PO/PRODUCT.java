package com.daeu.suprema.io.IF_SFDC_ERP_REG_PO;

import lombok.Data;

@Data
public class PRODUCT {
    /**
     *  20-1. OrderProduct SFDC Key
     */
    private String orderProductId;

    /**
     *  20-2. 대표품목명코드
     */
    private String productCode;

    /**
     *  20-3. 원산지
     */
    private String originCode;

    /**
     *  20-4. 수주 수량
     */
    private double quantity;

    /**
     *  20-5. 수주 단가
     */
    private double unitPrice;

    /**
     *  20-6. 수주 금액
     */
    private double totalPrice;

    /**
     *  20-7. 할인 금액
     */
    private double discountAmt;

    /**
     *  20-8. 추가 할인율
     */
    private double discountRate;

    /**
     *  20-9. 추가 할인율
     */
    private String remark;
}
