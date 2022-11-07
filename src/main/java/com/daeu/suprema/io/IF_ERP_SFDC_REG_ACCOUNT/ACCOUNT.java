package com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT;

import lombok.Data;

import java.util.Map;

@Data
public class ACCOUNT {
    /**
     * 1-1. 거래처 코드<br>
     *  - ERP 거래처 코드
     */
    private String accountId;

    /**
     * 1-2. 사업자등록번호
     */
    private String businessNum;

    /**
     * 1-3. 거래처전명
     */
    private String accountFullName;

    /**
     * 1-4. 영문주소
     */
    private String addressEng;

    /**
     * 1-5. 전화번호
     */
    private String phoneNum;

    /**
     * 1-6. 국가코드<br>
     *  - 국가코드(코드기준정보)
     */
    private String countryCode;

    /**
     * 1-7. 거래처담당자명
     */
    private String accountRep;

    /**
     * 1-8. 통화코드<br>
     *  - USD, KRW, EUR, JPY
     */
    private String currencyCode;

    /**
     * 1-9. 결제방법(영업)<br>
     *  - 결제조건(코드기준정보)
     */
    private String paymentMethod;

    /**
     * 1-10. 판매유형
     */
    private String dealType;

    /**
     * 1-12. CUD 타입 (C, U, D)<br>
     *  - C: Insert, U: Update, D: Delete
     */
    private String cudType;

    /**
     * 1-12. Record ID
     */
    private int recordId;

    public ACCOUNT() { }

    public ACCOUNT(Map<String, Object> a) {
        this.accountId = a.get("BP_CD").toString();
        this.businessNum = a.get("BP_RGST_NO").toString();
        this.accountFullName = a.get("BP_FULL_NM").toString();
        this.addressEng = a.get("ADDR_ENG").toString();
        this.phoneNum = a.get("TEL_NO1").toString();
        this.countryCode = a.get("CONTRY_CD").toString();
        this.accountRep = a.get("BP_PRSN_NM").toString();
        this.currencyCode = a.get("CURRENCY").toString();
        this.paymentMethod = a.get("PAY_METH").toString();
        this.dealType = a.get("DEAL_TYPE").toString();
        this.cudType = a.get("IF_ACT_CODE").toString();
        this.recordId = Integer.parseInt(a.get("IF_REC_ID").toString());
    }
}
