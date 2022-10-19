package com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT;

import lombok.Data;

import java.util.Map;

@Data
public class ACCOUNT_DEL {
    /**
     * 1-1. 거래처 코드<br>
     *  - ERP 거래처 코드
     */
    private String accountId;

    public ACCOUNT_DEL() { }

    public ACCOUNT_DEL(Map<String, Object> a) {
        this.accountId = a.get("BP_CD").toString();
    }
}
