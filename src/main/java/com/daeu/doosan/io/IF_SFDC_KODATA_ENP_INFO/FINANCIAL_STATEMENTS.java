package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class FINANCIAL_STATEMENTS {
    /**
     * 11-1. 결산기준연원일
     */
    private String closingAccountsDate;

    /**
     * 11-2. 총자산(천원)
     */
    private int totalAsset;

    /**
     * 11-3. 납입자본금(천원)
     */
    private int paidInCapital;

    /**
     * 11-4. 자본총계(천원)
     */
    private int totalEquity;

    /**
     * 11-5. 매출액(천원)
     */
    private int grossProfit;

    /**
     * 11-6. 영업이익(천원)
     */
    private int operatingIncome;

    /**
     * 11-7. 당기순이익(천원)
     */
    private int netIncome;

    /**
     * 11-8. 부채총계(천원)
     */
    private int totalLiabilities;

    public FINANCIAL_STATEMENTS() { }

    public FINANCIAL_STATEMENTS(Map<String, Object> fs) {
        this.closingAccountsDate = fs.get("acct_dt").toString();
        this.totalAsset          = Integer.parseInt(fs.get("sumasset").toString());
        this.paidInCapital       = Integer.parseInt(fs.get("paymentfund").toString());
        this.totalEquity         = Integer.parseInt(fs.get("fundtotal").toString());
        this.grossProfit         = Integer.parseInt(fs.get("sales").toString());
        this.operatingIncome     = Integer.parseInt(fs.get("profit").toString());
        this.netIncome           = Integer.parseInt(fs.get("termnetprofit").toString());
        this.totalLiabilities    = Integer.parseInt(fs.get("totalliability").toString());
    }

}
