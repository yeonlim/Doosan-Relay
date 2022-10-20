package com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO;

import lombok.Data;

import java.util.Map;

@Data
public class SHIP_TO_DEL {
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
    private String shipToUseYn;

    /**
     *  1-8. 파트너 거래처 국가코드
     */
    private String shipToCountryCode;

    /**
     * 1-9. CUD 타입 (C, U, D)<br>
     *  - C: Insert, U: Update, D: Delete
     */
    private String cudType;

    public SHIP_TO_DEL() { }

    public SHIP_TO_DEL(Map<String, Object> s) {

    }
}
