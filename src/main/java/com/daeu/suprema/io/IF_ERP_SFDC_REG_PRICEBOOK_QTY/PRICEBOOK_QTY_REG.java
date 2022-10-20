package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_QTY;

import lombok.Data;

import java.util.Map;

@Data
public class PRICEBOOK_QTY_REG {
    /**
     *  1. 품목그룹코드
     */
    private String productGroupCode;

    /**
     *  2. 판매유형<br>
     *   - 거래코드(코드기준정보)
     */
    private String dealTypeCode;

    /**
     *  3. 단위
     */
    private String saleUnit;

    /**
     *  4. 수량(From)
     */
    private int quantityFrom;

    /**
     *  5. 수량(To)
     */
    private int quantityTo;

    /**
     *  6. 통화<br>
     *   - USD, KRW, EUR, JPY
     */
    private String currencyCode;

    /**
     *  7. 단가
     */
    private long unitPrice;

    /**
     *  8. CUD 타입 (C, D)<br>
     *   - C: Insert, D: Delete
     */
    private String cudType;

    public PRICEBOOK_QTY_REG() { }

    public PRICEBOOK_QTY_REG(Map<String, Object> pq) {
        this.productGroupCode = pq.get("ITEM_GROUP_CD").toString();
        this.dealTypeCode = pq.get("DEAL_TYPE").toString();
        this.saleUnit = pq.get("SALES_UNIT").toString();
        this.quantityFrom = Integer.parseInt(pq.get("FROM_AMT").toString());
        this.quantityTo = Integer.parseInt(pq.get("TO_AMT").toString());
        this.currencyCode = pq.get("CURRENCY").toString();
        this.unitPrice = Long.parseLong(pq.get("ITEM_PRICE").toString());
        this.cudType = pq.get("IF_ACT_CODE").toString();
    }
}