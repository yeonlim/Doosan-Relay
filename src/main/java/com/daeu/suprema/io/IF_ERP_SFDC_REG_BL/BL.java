package com.daeu.suprema.io.IF_ERP_SFDC_REG_BL;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BL {
    /**
     *  1-1. 수주 ID (Order SFDC Key)
     */
    private String orderId;

    /**
     *  1-2. 수주 H/S 코드
     */
    private String orderHsCode;

    /**
     *  1-3. BL 번호
     */
    private String blNum;

    /**
     *  1-4. 출고요청일
     */
    private String releaseRequestDate;

    /**
     *  1-5. 출고예정일
     */
    private String releaseExpectedDate;

    /**
     *  1-6. L/C No
     */
    private String lcNo;

    /**
     *  1-7. L/C Date
     */
    private String lcDate;

    /**
     *  1-8. L/C Issuing Bank
     */
    private String lcIssuingBank;

    /**
     *  1-9. 수주 상품 리스트
     */
    private List<PRODUCT> productList;

    /**
     *  1-10. 시리얼 번호 리스트
     */
    private List<SERIAL_NO> serialNoList;

    /**
     * 1-11. Record ID
     */
    private int recordId;

    public BL() { }

    public BL(Map<String, Object> b) {
        boolean flag = "D".equals(b.get("IF_ACT_CODE").toString());

        this.orderId = b.get("SFDC_ORDERID").toString();
        this.orderHsCode = flag ? null : b.get("HS_CD_H").toString();
        this.blNum = flag ? null : b.get("BL_DOC_NO").toString();
        this.releaseRequestDate = flag ? null : b.get("INV_DT").toString();
        this.releaseExpectedDate = flag ? null : b.get("PROMISE_DT").toString();
        this.lcNo = flag || b.get("LC_NO") == null ? null : b.get("LC_NO").toString();
        this.lcDate = flag || b.get("LC_DATE") == null ? null : b.get("LC_DATE").toString();
        this.lcIssuingBank = flag || b.get("LC_ISSUE_BANK") == null ? null : b.get("LC_ISSUE_BANK").toString();
        this.recordId = Integer.parseInt(b.get("IF_REC_ID").toString());
    }
}
