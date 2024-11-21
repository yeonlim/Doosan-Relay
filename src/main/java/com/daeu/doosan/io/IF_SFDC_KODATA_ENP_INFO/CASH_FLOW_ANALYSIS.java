package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class CASH_FLOW_ANALYSIS {
    /**
     * 10-1. 최근결산년_결산일
     */
    private String closingDateRecentFY;

    /**
     * 10-2. 최근결산년_현금영업이익(천원)
     */
    private double cashOperatingIncomeRecentFY;

    /**
     * 10-3. 최근결산년_경상활동후의현금흐름(천원)
     */
    private double cfoRecentFY;

    /**
     * 10-4. 최근결산년_투자활동후의현금흐름(천원)
     */
    private double cfiRecentFY;

    /**
     * 10-5. 최근결산년_현금흐름등급코드
     */
    private String cashFlowsGradeCodeRecentFY;

    /**
     * 10-6. 최근결산직전년_결산일
     */
    private String closingDatePreviousFY;

    /**
     * 10-7. 최근결산직전년_현금영업이익(천원)
     */
    private double cashOperatingIncomePreviousFY;

    /**
     * 10-8. 최근결산직전년_경상활동후의현금흐름(천원)
     */
    private double cfoPreviousFY;

    /**
     * 10-9. 최근결산직전년_투자활동후의현금흐름(천원)
     */
    private double cfiPreviousFY;

    /**
     * 10-10. 최근결산직전년_현금흐름등급코드
     */
    private String cashFlowsGradeCodePreviousFY;

    /**
     * 10-11. 최근결산직직전년_결산일
     */
    private String closingDateTheYearBeforeLastFY;

    /**
     * 10-12. 최근결산직직전년_현금영업이익(천원)
     */
    private double cashOperatingIncomeTheYearBeforeLastFY;

    /**
     * 10-13. 최근결산직직전년_경상활동후의현금흐름(천원)
     */
    private double cfoTheYearBeforeLastFY;

    /**
     * 10-14. 최근결산직직전년_투자활동후의현금흐름(천원)
     */
    private double cfiTheYearBeforeLastFY;

    /**
     * 10-15. 최근결산직직전년_현금흐름등급코드
     */
    private String cashFlowsGradeCodeTheYearBeforeLastFY;

    public CASH_FLOW_ANALYSIS() { }

    public CASH_FLOW_ANALYSIS(Map<String, Object> cfa) {
        this.closingDateRecentFY                    = cfa.get("cf_acct_dt").toString();
        this.cashOperatingIncomeRecentFY            = Double.parseDouble(cfa.get("cf_anal1").toString());
        this.cfoRecentFY                            = Double.parseDouble(cfa.get("cf_anal2").toString());
        this.cfiRecentFY                            = Double.parseDouble(cfa.get("cf_anal3").toString());
        this.cashFlowsGradeCodeRecentFY             = cfa.get("cf_anal4").toString();
        this.closingDatePreviousFY                  = cfa.get("cf1_acct_dt").toString();
        this.cashOperatingIncomePreviousFY          = Double.parseDouble(cfa.get("cf1_anal1").toString());
        this.cfoPreviousFY                          = Double.parseDouble(cfa.get("cf1_anal2").toString());
        this.cfiPreviousFY                          = Double.parseDouble(cfa.get("cf1_anal3").toString());
        this.cashFlowsGradeCodePreviousFY           = cfa.get("cf1_anal4").toString();
        this.closingDateTheYearBeforeLastFY         = cfa.get("cf2_acct_dt").toString();
        this.cashOperatingIncomeTheYearBeforeLastFY = Double.parseDouble(cfa.get("cf2_anal1").toString());
        this.cfoTheYearBeforeLastFY                 = Double.parseDouble(cfa.get("cf2_anal2").toString());
        this.cfiTheYearBeforeLastFY                 = Double.parseDouble(cfa.get("cf2_anal3").toString());
        this.cashFlowsGradeCodeTheYearBeforeLastFY  = cfa.get("cf2_anal4").toString();

    }

}
