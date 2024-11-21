package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class STOCKHOLDER_STATUS {
    /**
     * 4-1. 정보기준일자
     */
    private String refDate;

    /**
     * 4-2. 주요주주1
     */
    private String majorStockholder1;

    /**
     * 4-3. 지분율1
     */
    private double shareRatio1;

    /**
     * 4-4. 주요주주2
     */
    private String majorStockholder2;

    /**
     * 4-5. 지분율2
     */
    private double shareRatio2;

    /**
     * 4-6. 주요주주3
     */
    private String majorStockholder3;

    /**
     * 4-7. 지분율3
     */
    private double shareRatio3;

    public STOCKHOLDER_STATUS() { }

    public STOCKHOLDER_STATUS(Map<String, Object> ss) {
        this.refDate           = ss.get("std_dt").toString();
        this.majorStockholder1 = ss.get("sth_nm1").toString();
        this.shareRatio1       = Double.parseDouble(ss.get("sth_eqrt1").toString());
        this.majorStockholder2 = ss.get("sth_nm2").toString();
        this.shareRatio2       = Double.parseDouble(ss.get("sth_eqrt2").toString());
        this.majorStockholder3 = ss.get("sth_nm3").toString();
        this.shareRatio3       = Double.parseDouble(ss.get("sth_eqrt4").toString());
    }
}
