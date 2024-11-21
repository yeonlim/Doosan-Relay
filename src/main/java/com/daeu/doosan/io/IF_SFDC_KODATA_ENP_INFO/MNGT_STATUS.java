package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class MNGT_STATUS {
    /**
     *  3-1. 정보기준일자
     */
    private String refDate;

    /**
     *  3-2. 성명
     */
    private String name;

    /**
     *  3-3. 경영진구분코드
     */
    private String mngtClassifCode;

    /**
     *  3-4. 등기여부
     */
    private String regYN;

    /**
     *  3-5. 직위
     */
    private String title;

    /**
     *  3-6. 경영실권자와의관계코드
     */
    private String relationWithAuthorityCode;

    public MNGT_STATUS() { }

    public MNGT_STATUS(Map<String, Object> ms) {
        this.refDate                   = ms.get("std_dt").toString();
        this.name                      = ms.get("name").toString();
        this.mngtClassifCode           = ms.get("mngr_ccd").toString();
        this.regYN                     = ms.get("reg_yn").toString();
        this.title                     = ms.get("ttl").toString();
        this.relationWithAuthorityCode = ms.get("mdm_rel_cd").toString();
    }
}
