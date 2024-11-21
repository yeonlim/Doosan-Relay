package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class TOTAL_CREDIT_GRADE {
    /**
     * 9-1. 신용등급
     */
    private String creditGradeCode;

    /**
     * 9-2. 신용등급명
     */
    private String name;

    /**
     * 9-3. 등급구분
     */
    private String gradeTypeCode;

    /**
     * 9-4. 등급구분명
     */
    private String gradeTypeName;

    /**
     * 9-5. 평가(산출)일자
     */
    private String evaluationDate;

    /**
     * 9-6. 결산기준일
     */
    private String closingAccountsDate;

    public TOTAL_CREDIT_GRADE() { }

    public TOTAL_CREDIT_GRADE(Map<String, Object> tcg) {
        this.creditGradeCode     = tcg.get("cr_grd").toString();
        this.name                = tcg.get("cr_grd_nm").toString();
        this.gradeTypeCode       = tcg.get("grd_cls").toString();
        this.gradeTypeName       = tcg.get("grd_cls_nm").toString();
        this.evaluationDate      = tcg.get("evl_dt").toString();
        this.closingAccountsDate = tcg.get("sttl_base_dd").toString();
    }

}
