package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRODUCT;

import lombok.Data;

import java.util.Map;

@Data
public class PRODUCT {
    /**
     *  1-1. 대표품목명코드
     */
    private String productCode;

    /**
     *  1-2. 대표품목명
     */
    private String productName;

    /**
     *  1-3. 품목그룹코드
     */
    private String productGroupCode;

    /**
     *  1-4. 순중량
     */
    private double netWeight;

    /**
     *  1-5. H/S 코드<br>
     *   - H/S 코드(코드기준정보)
     */
    private String hsCode;

    /**
     *  1-6. 설명
     */
    private String spec;

    /**
     *  1-7. Forcast 표시 여부
     */
    private boolean forcastTarget;

    /**
     *  1-8. Country of Origin
     */
    private String countryOfOrigin;

    /**
     *  1-9. 단종여부
     */
    private boolean useYn;

    /**
     *  1-10. CUD 타입 (C, U, D)<br>
     *   - C: Insert, U: Update, D: Delete
     */
    private String cudType;

    /**
     * 1-11. Record ID
     */
    private int recordId;

    public PRODUCT() { }

    public PRODUCT(Map<String, Object> p) {
        this.productCode = p.get("S_ITEM_CD").toString();
        this.productName = p.get("S_ITEM_NM").toString();
        this.productGroupCode = p.get("ITEM_GROUP_CD") == null ? null : p.get("ITEM_GROUP_CD").toString();
        this.netWeight = p.get("UNIT_WEIGHT") == null ? 0 : Double.parseDouble(p.get("UNIT_WEIGHT").toString());
        this.hsCode = p.get("HSCode_c") == null ? null : p.get("HSCode_c").toString();
        this.spec = p.get("SPEC") == null ? null : p.get("SPEC").toString();
        this.forcastTarget = p.get("forcastTarget") == null ? false : Boolean.parseBoolean(p.get("forcastTarget").toString());
        this.countryOfOrigin = p.get("CountryofOrigin") == null ? null : p.get("CountryofOrigin").toString();
        this.useYn = Boolean.parseBoolean(p.get("useYn").toString());
        this.cudType = p.get("IF_ACT_CODE").toString();
        this.recordId = Integer.parseInt(p.get("IF_REC_ID").toString());
    }
}
