package com.daeu.suprema.io.IF_ERP_SFDC_REG_PACKING_LIST;

import lombok.Data;

import java.util.Map;

@Data
public class PACKING_LIST_DEL {
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

    public PACKING_LIST_DEL() { }

    public PACKING_LIST_DEL(Map<String, Object> pl) {
        this.orderId = pl.get("SFDC_ORDERID").toString();
        this.productCode = pl.get("S_ITEM_CD").toString();
        this.quantity = Double.parseDouble(pl.get("QUANTITY_C").toString());
        this.productWeight = Double.parseDouble(pl.get("PRODUCTWEIGHT_C").toString());
        this.totalWeight = Double.parseDouble(pl.get("TOTALWEIGHT_C").toString());
        this.boxQuantity = Double.parseDouble(pl.get("BOXQUANTITY_C").toString());
        this.boxDimension = Double.parseDouble(pl.get("BOXDIMENSION_C").toString());
        this.viewSeq = Integer.parseInt(pl.get("VIEW_SEQ").toString());
    }
}
