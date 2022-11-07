package com.daeu.suprema.io.IF_ERP_SFDC_REG_SOA;

import lombok.Data;

import java.util.Map;

@Data
public class SOA {
    /**
     *  1-1. Order SFDC Key
     */
    private String orderId;

    /**
     *  1-2. Invoice NO
     */
    private String invoiceNo;

    /**
     *  1-3. 거래처 코드<br>
     *   - ERP 거래처 코드
     */
    private String accountId;

    /**
     *  1-4. 통화<br>
     *   - USD, KRW, EUR, JPY
     */
    private String currencyCode;

    /**
     *  1-5. 채권금액
     */
    private double amount;

    /**
     *  1-6. 미반제금액
     */
    private double unpaidAmount;

    /**
     *  1-7. 반제금액
     */
    private double reimbursement;

    /**
     *  1-8. 결제여부
     */
    private String paymentStatus;

    /**
     *  1-9. 매출확정일
     */
    private String salesConfirmDate;

    /**
     *  1-10. 결제만기일
     */
    private String paymentDueDate;

    /**
     *  1-11. 비고
     */
    private String remark;

    /**
     *  1-12. Invoice Date
     */
    private String invoiceDate;

    /**
     * 1-13. CUD 타입 (C, U, D)<br>
     *  - C: Insert, U: Update, D: Delete
     */
    private String cudType;

    /**
     * 1-14. Record ID
     */
    private int recordId;

    public SOA() { }

    public SOA(Map<String, Object> s) {
        this.orderId = s.get("SFDC_ORDERID").toString();
        this.invoiceNo = s.get("IV_NO").toString();
        this.accountId = s.get("BP_CD").toString();
        this.currencyCode = s.get("CURRENCY").toString();
        this.amount = Double.parseDouble(s.get("BILL_AMT").toString());
        this.unpaidAmount = Double.parseDouble(s.get("unpaidAmount").toString());
        this.reimbursement = Double.parseDouble(s.get("COLLECT_AMT").toString());
        this.paymentStatus = s.get("paymentStatus").toString();
        this.salesConfirmDate = s.get("BILL_DT").toString();
        this.paymentDueDate = s.get("INCOME_PLAN_DT").toString();
        this.remark = s.get("REMARK").toString();
        this.invoiceDate = s.get("INVOICE_DT").toString();
        this.cudType = s.get("IF_ACT_CODE").toString();
        this.recordId = Integer.parseInt(s.get("IF_REC_ID").toString());
    }
}
