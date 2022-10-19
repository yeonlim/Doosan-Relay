package com.daeu.suprema.io.IF_ERP_SFDC_REG_SOA;

import lombok.Data;

import java.util.Map;

@Data
public class SOA {
    /**
     *  1. Order SFDC Key
     */
    private String orderId;

    /**
     *  2. Invoice NO
     */
    private String invoiceNo;

    /**
     *  3. 거래처 코드<br>
     *   - ERP 거래처 코드
     */
    private String accountId;

    /**
     *  4. 통화<br>
     *   - USD, KRW, EUR, JPY
     */
    private String currencyCode;

    /**
     *  5. 채권금액
     */
    private long amount;

    /**
     *  6. 반제금액
     */
    private long reimbursement;

    /**
     *  7. 매출확정일
     */
    private String salesConfirmDate;

    /**
     *  8. 결제만기일
     */
    private String paymentDueDate;

    /**
     *  9. 비고
     */
    private String remark;

    /**
     *  10. Invoice Date
     */
    private String invoiceDate;

    /**
     * 1-11. CUD 타입 (C, U, D)<br>
     *  - C: Insert, U: Update, D: Delete
     */
    private String cudType;

    public SOA() { }

    public SOA(Map<String, Object> s) {
        this.orderId = s.get("SFDC_ORDERID").toString();
        this.invoiceNo = s.get("IV_NO").toString();
        this.accountId = s.get("BP_CD").toString();
        this.currencyCode = s.get("CURRENCY").toString();
        this.amount = Long.parseLong(s.get("BILL_AMT").toString());
        this.reimbursement = Long.parseLong(s.get("COLLECT_AMT").toString());
        this.salesConfirmDate = s.get("BILL_DT").toString();
        this.paymentDueDate = s.get("INCOME_PLAN_DT").toString();
        this.remark = s.get("REMARK").toString();
        this.invoiceDate = s.get("INVOICE_DT").toString();
        this.cudType = s.get("IF_ACT_CODE").toString();
    }
}
