package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_ACC;

import lombok.Data;

import java.util.Map;

@Data
public class PRICEBOOK_ACC_DEL {
    /**
     * 1-1. 거래처코드<br>
     *  - ERP 거래처 코드
     */
    private String accountId;

    /**
     * 1-2. 품목그룹코드
     */
    private String productGroupCode;

    public PRICEBOOK_ACC_DEL() { }

    public PRICEBOOK_ACC_DEL(Map<String, Object> pa) {
        this.accountId = pa.get("BP_CD").toString();
        this.productGroupCode = pa.get("ITEM_GROUP_CD").toString();
    }
}
