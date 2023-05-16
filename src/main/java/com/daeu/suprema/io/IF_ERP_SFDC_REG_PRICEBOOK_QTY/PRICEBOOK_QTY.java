package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_QTY;

import lombok.Data;

import java.util.Map;

@Data
public class PRICEBOOK_QTY {
    /**
     *  1-1. 품목그룹코드
     */
    private String productGroupCode;

    /**
     *  1-2. 판매유형<br>
     *   - 거래코드(코드기준정보)
     */
    private String dealTypeCode;

    /**
     *  1-3. 수량(From)
     */
    private double quantityFrom;

    /**
     *  1-4. 수량(To)
     */
    private double quantityTo;

    /**
     *  1-5. 통화<br>
     *   - USD, KRW, EUR, JPY
     */
    private String currencyCode;

    /**
     *  1-6. 단가
     */
    private double unitPrice;

    /**
     *  1-7. CUD 타입 (C, D)<br>
     *   - C: Insert, D: Delete
     */
    private String cudType;

    /**
     * 1-8. Record ID
     */
    private int recordId;

    public PRICEBOOK_QTY() { }

    public PRICEBOOK_QTY(Map<String, Object> pq) {
        this.productGroupCode = pq.get("ITEM_GROUP_CD").toString();
        this.dealTypeCode = pq.get("DEAL_TYPE").toString();
        this.quantityFrom = Double.parseDouble(pq.get("FROM_AMT").toString());
        this.quantityTo = Double.parseDouble(pq.get("TO_AMT").toString());
        this.currencyCode = pq.get("CURRENCY").toString();
        this.unitPrice = Double.parseDouble(pq.get("ITEM_PRICE").toString());
        this.cudType = pq.get("IF_ACT_CODE").toString();
        this.recordId = Integer.parseInt(pq.get("IF_REC_ID").toString());
    }
}
