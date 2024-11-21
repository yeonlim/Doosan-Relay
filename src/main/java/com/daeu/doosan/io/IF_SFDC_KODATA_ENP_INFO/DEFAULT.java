package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class DEFAULT {
    /**
     * 12-1. 채무불이행건수
     */
    private String defaultCnt;

    /**
     * 12-2. 채무불이행 등록금액(천원)
     */
    private int defaultRegBalance;

    /**
     * 12-3. 채무불이행 연체금액(천원)
     */
    private int defaultOverdueBalance;

    /**
     * 12-4. 카드발급/당좌개설 건수
     */
    private int cardAcctIssueCnt;

    /**
     * 12-5. 법정관리/화의 건수
     */
    private int receivershipCnt;

    /**
     * 12-6. 당좌거래정지 건수
     */
    private int suspensionAcctCnt;

    public DEFAULT() { }

    public DEFAULT(Map<String, Object> d) {
        this.defaultCnt            = d.get("kfb_bad_cnt").toString();
        this.defaultRegBalance     = Integer.parseInt(d.get("kfb_bad_regamt").toString());
        this.defaultOverdueBalance = Integer.parseInt(d.get("kft_bad_ovamt").toString());
        this.cardAcctIssueCnt      = Integer.parseInt(d.get("kfb_fin_tx_cnt").toString());
        this.receivershipCnt       = Integer.parseInt(d.get("workout_cnt").toString());
        this.suspensionAcctCnt     = Integer.parseInt(d.get("dshovd_cnt").toString());
    }

}
