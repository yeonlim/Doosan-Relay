package com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO;

import lombok.Data;

import java.util.Map;

@Data
public class SHIP_TO {
    /**
     *  1-1. 거래처 코드<br>
     *   - ERP 거래처 코드
     */
    private String accountId;

    /**
     *  1-2. 파트너 거래처 코드<br>
     *   - ERP 거래처 코드
     */
    private String shipToAccountId;

    /**
     *  1-3. 파트너 거래처명
     */
    private String shipToName;

    /**
     *  1-4. 파트너 거래처 영문주소
     */
    private String shipToAddressEng;

    /**
     *  1-5. 파트너 거래처 담당자명
     */
    private String shipToManager;

    /**
     *  1-6. 파트너 거래처 전화번호
     */
    private String shipToPhone;

    /**
     *  1-7. 사용유무
     */
    private boolean shipToUseYn;

    /**
     *  1-8. 파트너 거래처 국가코드
     */
    private String shipToCountryCode;

    /**
     * 1-9. CUD 타입 (C, U, D)<br>
     *  - C: Insert, U: Update, D: Delete
     */
    private String cudType;

    /**
     * 1-10. Record ID
     */
    private int recordId;

    public SHIP_TO() { }

    // SHIP_TO > ACCOUNT
    public SHIP_TO(Map<String, Object> s) {
        this.accountId = s.get("BP_CD").toString();
        this.shipToAccountId = s.get("PARTNER_BP_CD").toString();
        this.shipToName = s.get("BP_FULL_NM").toString();
        this.shipToAddressEng = s.get("ADDR_ENG").toString();
        this.shipToManager = s.get("BP_PRSN_NM").toString();
        this.shipToPhone = s.get("TEL_NO1").toString();
        this.shipToUseYn = Boolean.parseBoolean(s.get("USE_FG").toString());
        this.shipToCountryCode = s.get("CONTRY_CD").toString();
        this.cudType = s.get("IF_ACT_CODE").toString();
        this.recordId = Integer.parseInt(s.get("IF_REC_ID").toString());
    }
}
