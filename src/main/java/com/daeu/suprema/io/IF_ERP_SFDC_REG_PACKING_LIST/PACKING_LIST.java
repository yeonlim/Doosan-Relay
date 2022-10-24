package com.daeu.suprema.io.IF_ERP_SFDC_REG_PACKING_LIST;

import lombok.Data;

import java.util.Map;

@Data
public class PACKING_LIST {
    /**
     *  1-1. 수주 ID (Order SFDC Key)
     */
    private String orderId;

    /**
     *  1-2. 대표품목명코드
     */
    private String productCode;

    /**
     *  1-3. 수량
     */
    private double quantity;

    /**
     *  1-4. 제품무게
     */
    private double productWeight;

    /**
     *  1-5. 총무게
     */
    private double totalWeight;

    /**
     *  1-6. Box 수
     */
    private double boxQuantity;

    /**
     *  1-7. Box 코드
     */
    private double boxDimension;

    /**
     *  1-8. View Seq
     */
    private int viewSeq;

    /**
     * 1-9. CUD 타입 (C, U, D)<br>
     *  - C: Insert, U: Update, D: Delete
     */
    private String cudType;

    /**
     * 1-10. Record ID
     */
    private int recordId;

    public PACKING_LIST() { }

    public PACKING_LIST(Map<String, Object> pl) {
        this.orderId = pl.get("SFDC_ORDERID").toString();
        this.productCode = pl.get("S_ITEM_CD").toString();
        this.quantity = Double.parseDouble(pl.get("QUANTITY_C").toString());
        this.productWeight = Double.parseDouble(pl.get("PRODUCTWEIGHT_C").toString());
        this.totalWeight = Double.parseDouble(pl.get("TOTALWEIGHT_C").toString());
        this.boxQuantity = Double.parseDouble(pl.get("BOXQUANTITY_C").toString());
        this.boxDimension = Double.parseDouble(pl.get("BOXDIMENSION_C").toString());
        this.viewSeq = Integer.parseInt(pl.get("VIEW_SEQ").toString());
        this.cudType = pl.get("IF_ACT_CODE").toString();
        this.recordId = Integer.parseInt(pl.get("IF_REC_ID").toString());
    }
}
