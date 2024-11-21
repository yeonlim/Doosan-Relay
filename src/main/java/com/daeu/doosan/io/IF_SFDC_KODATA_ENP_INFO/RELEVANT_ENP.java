package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class RELEVANT_ENP {
    /**
     * 6-1. 정보기준일자
     */
    private String refDate;

    /**
     * 6-2. 관계회사명1
     */
    private String relevantEnp1Name;

    /**
     * 6-3. 출자비율1
     */
    private double investmentRatio1;

    /**
     * 6-4. 관계회사명2
     */
    private String relevantEnp2Name;

    /**
     * 6-5. 출자비율2
     */
    private double investmentRatio2;

    /**
     * 6-6. 관계회사명3
     */
    private String relevantEnp3Name;

    /**
     * 6-7. 출자비율3
     */
    private double investmentRatio3;

    /**
     * 1-4. CUD 타입 (C, D)
     */
    private String cudType;

    public RELEVANT_ENP() { }

    public RELEVANT_ENP(Map<String, Object> re) {
        this.refDate          = re.get("std_dt").toString();
        this.relevantEnp1Name = re.get("renp_nm1").toString();
        this.investmentRatio1 = Double.parseDouble(re.get("renp_eqrt1").toString());
        this.relevantEnp2Name = re.get("renp_nm2").toString();
        this.investmentRatio2 = Double.parseDouble(re.get("renp_eqrt2").toString());
        this.relevantEnp3Name = re.get("renp_nm3").toString();
        this.investmentRatio3 = Double.parseDouble(re.get("renp_eqrt4").toString());
    }

}
