package com.daeu.suprema.io.IF_ERP_SFDC_REG_BL;

import lombok.Data;

import java.util.Map;

@Data
public class SERIAL_NO {
    /**
     *  1-10-1. 대표품목명코드
     */
    private String productCode;

    /**
     *  1-10-2. Serial No
     */
    private String serialNo;

    /**
     *  1-10-3. 출고일자
     */
    private String releaseDate;

    /**
     *  1-10-4. 출고수량
     */
    private int quantity;

    public SERIAL_NO() { }

    public SERIAL_NO(Map<String, Object> s) {
        this.productCode = s.get("S_ITEM_CD").toString();
        this.serialNo = s.get("SERIAL_NO").toString();
        this.releaseDate = s.get("ACTUAL_GI_DT").toString();
        this.quantity = Integer.parseInt(s.get("GI_QTY").toString());
    }
}
