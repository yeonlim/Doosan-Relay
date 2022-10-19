package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_ACC;

import lombok.Data;

import java.util.Map;

@Data
public class PRICEBOOK_ACC {
    /**
     * 1-1. 거래처코드<br>
     *  - ERP 거래처 코드
     */
    private String accountId;

    /**
     * 1-2. 품목그룹코드
     */
    private String productGroupCode;

    /**
     * 1-3. 할인율
     */
    private int discountRate;

    /**
     * 1-4. CUD 타입 (C, D)<br>
     *  - C: Insert, D: Delete
     */
    private String cudType;

    public PRICEBOOK_ACC() { }

    public PRICEBOOK_ACC(Map<String, Object> pa) {
        this.accountId = pa.get("BP_CD").toString();
        this.productGroupCode = pa.get("ITEM_GROUP_CD").toString();
        this.discountRate = Integer.parseInt(pa.get("DC_RATE").toString());
        this.cudType = pa.get("IF_ACT_CODE").toString();
    }
}
