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
     * 1-6. Invoice No
     */
    private String ivNo;

    /**
     * 1-7. Invoice Date
     */
    private String ivDate;

    /**
     *  1-8. L/C No
     */
    private String lcNo;

    /**
     *  1-9. L/C Date
     */
    private String lcDate;

    /**
     *  1-10. L/C Issuing Bank
     */
    private String lcIssuingBank;

    /**
     *  1-11. 비고(Order)
     */
    private String orderRemark;

    /**
     *  1-12. 수주 상품 리스트
     */
    private List<PRODUCT> productList;

    /**
     *  1-13. 시리얼 번호 리스트
     */
    private List<SERIAL_NO> serialNoList;

    /**
     * 1-14. Record ID
     */
    private int recordId;

    /**
     *  1-15. CUD 타입 (C, D)<br>
     *  - C: Insert, D: Delete
     */
    private String cudType;

    public BL() { }

    public BL(Map<String, Object> b) {
        boolean flag = "D".equals(b.get("IF_ACT_CODE").toString());

        this.orderId                = b.get("SFDC_ORDERID").toString();
        this.orderHsCode            = flag || b.get("HS_CD_H") == null ? null : b.get("HS_CD_H").toString();
        this.blNum                  = flag || b.get("BL_DOC_NO") == null ? null : b.get("BL_DOC_NO").toString();
        this.releaseRequestDate     = flag ? null : b.get("INV_DT").toString();
        this.releaseExpectedDate    = flag ? null : b.get("PROMISE_DT").toString();
        this.lcNo                   = flag || b.get("LC_NO") == null ? null : b.get("LC_NO").toString();
        this.lcDate                 = flag || b.get("LC_DATE") == null ? null : b.get("LC_DATE").toString();
        this.lcIssuingBank          = flag || b.get("LC_ISSUE_BANK") == null ? null : b.get("LC_ISSUE_BANK").toString();
        this.orderRemark            = flag || b.get("REMARK_HDR") == null ? null : b.get("REMARK_HDR").toString();
        this.recordId               = Integer.parseInt(b.get("IF_REC_ID").toString());
        this.cudType                = flag ? "D" : "C";
        this.ivNo                   = flag || b.get("IV_NO") == null ? "" : b.get("IV_NO").toString();
        this.ivDate                 = flag || b.get("IV_CRT_DT") == null ? null : b.get("IV_CRT_DT").toString();
    }
}
