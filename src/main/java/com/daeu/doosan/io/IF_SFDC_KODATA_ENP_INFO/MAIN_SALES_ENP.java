package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class MAIN_SALES_ENP {
    /**
     * 8-1. 정보기준일자
     */
    private String refDate;

    /**
     * 8-2. 판매처명1
     */
    private String salesEnp1Name;

    /**
     * 8-3. 거래비중1
     */
    private double transactionRatio1;

    /**
     * 8-4. 판매처명2
     */
    private String salesEnp2Name;

    /**
     * 8-5. 거래비중2
     */
    private double transactionRatio2;

    /**
     * 8-6. 판매처명3
     */
    private String salesEnp3Name;

    /**
     * 8-7. 거래비중3
     */
    private double transactionRatio3;

    public MAIN_SALES_ENP() { }

    public MAIN_SALES_ENP(Map<String, Object> mse) {
        this.refDate           = mse.get("std_dt").toString();
        this.salesEnp1Name     = mse.get("supp_nm1").toString();
        this.transactionRatio1 = Double.parseDouble(mse.get("supp_rt1").toString());
        this.salesEnp2Name     = mse.get("supp_nm2").toString();
        this.transactionRatio2 = Double.parseDouble(mse.get("supp_rt2").toString());
        this.salesEnp3Name     = mse.get("supp_nm3").toString();
        this.transactionRatio3 = Double.parseDouble(mse.get("supp_rt3").toString());
    }
}
