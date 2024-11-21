package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class MAIN_PURCHASING_ENP {
    /**
     * 7-1. 정보기준일자
     */
    private String refDate;

    /**
     * 7-2. 구매처명1
     */
    private String purchasingEnp1Name;

    /**
     * 7-3. 거래비중1
     */
    private double transactionRatio1;

    /**
     * 7-4. 구매처명2
     */
    private String purchasingEnp2Name;

    /**
     * 7-5. 거래비중2
     */
    private double transactionRatio2;

    /**
     * 7-6. 구매처명3
     */
    private String purchasingEnp3Name;

    /**
     * 7-7. 거래비중3
     */
    private double transactionRatio3;

    public MAIN_PURCHASING_ENP() { }

    public MAIN_PURCHASING_ENP(Map<String, Object> mpe) {
        this.refDate            = mpe.get("std_dt").toString();
        this.purchasingEnp1Name = mpe.get("cust_nm1").toString();
        this.transactionRatio1  = Double.parseDouble(mpe.get("cust_rt1").toString());
        this.purchasingEnp2Name = mpe.get("cust_nm2").toString();
        this.transactionRatio2  = Double.parseDouble(mpe.get("cust_rt2").toString());
        this.purchasingEnp3Name = mpe.get("cust_nm3").toString();
        this.transactionRatio3  = Double.parseDouble(mpe.get("cust_rt3").toString());
    }
}
