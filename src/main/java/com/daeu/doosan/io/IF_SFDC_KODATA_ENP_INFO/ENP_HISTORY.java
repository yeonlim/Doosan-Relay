package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class ENP_HISTORY {

    /**
     * 5-1. 정보기준일자
     */
    private String refDate;

    /**
     * 5-2. 연혁일련번호
     */
    private String name;

    /**
     * 5-3. 연혁일자
     */
    private String historyDate;

    /**
     * 5-4. 연혁내용
     */
    private String historyDetail;

    public ENP_HISTORY() { }

    public ENP_HISTORY(Map<String, Object> eh) {
        this.refDate       = eh.get("std_dt").toString();
        this.name          = eh.get("his_seq").toString();
        this.historyDate   = eh.get("his_dt").toString();
        this.historyDetail = eh.get("ctt").toString();
    }
}
