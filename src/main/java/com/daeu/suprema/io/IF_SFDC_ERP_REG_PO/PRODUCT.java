package com.daeu.suprema.io.IF_SFDC_ERP_REG_PO;

import lombok.Data;

@Data
public class PRODUCT {
    /**
     *  21-1. OrderProduct SFDC Key
     */
    private String orderProductId;

    /**
     *  21-2. 대표품목명코드
     */
    private String productCode;

    /**
     *  21-3. 원산지
     */
    private String originCode;

    /**
     *  21-4. 수주 수량
     */
    private double quantity;

    /**
     *  21-5. 수주 단가
     */
    private double unitPrice;

    /**
     *  21-6. 수주 금액
     */
    private double totalPrice;

    /**
     *  21-7. 할인 금액
     */
    private double discountAmt;

    /**
     *  21-8. 추가 할인율
     */
    private double discountRate;

    /**
     *  21-9. 추가 할인율
     */
    private String remark;
}
