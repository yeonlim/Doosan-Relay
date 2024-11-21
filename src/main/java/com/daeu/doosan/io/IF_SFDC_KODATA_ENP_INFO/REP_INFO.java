package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class REP_INFO {
    /*
     * 대표자 정보 (ENPrepe0003)
     */

    /**
     * 2-1. 순번
     */
    private String seq;

    /**
     * 2-2. 정보기준일자
     */
    private String refDate;

    /**
     * 2-3. 성명
     */
    private String name;

    /**
     * 2-4. 주민등록번호
     */
    private String residentRegNum;

    /**
     * 2-5. 대표자구분코드
     */
    private String repClassifCode;

    /**
     * 2-6. 표시대표자여부
     */
    private String repMarkYN;

    /**
     * 2-7. 취임일자
     */
    private String inaugurateDate;

    /**
     * 2-8. 퇴임일자
     */
    private String retireDate;

    /**
     * 2-9. 현직여부
     */
    private String currentStatusYN;

    /**
     * 2-10. 직위
     */
    private String title;

    /**
     * 2-11. 경영형태코드
     */
    private String mngtFormCode;

    /**
     * 2-12. 동업계종사연수대표자
     */
    private int repBeInSameIndustryYear;

    /**
     * 2-13. 동업계종사월수대표자
     */
    private int repBeInSameIndustryMonth;

    /**
     * 2-14. 동업계종사기간년
     */
    private int beInSameIndustryYear;

    /**
     * 2-15. 동업계종사기간월
     */
    private int beInSameIndustryMonth;

    /**
     * 2-16. 동업계평코드
     */
    private String sameIndustryRequtationCode;

    /**
     * 2-17. 경영실권자와의관계코드
     */
    private String relationWithAuthorityCode;

    /**
     * 2-18. 경영능력코드
     */
    private String mngtCapacityCode;

    /**
     * 2-19. 주식소유현황(보통주)
     */
    private int stockHoldingOrdinary;

    /**
     * 2-20. 주식소유현황(우선주)
     */
    private int stockHoldingPreferred;

    /**
     * 2-21. 스톡옵션부여(주식수)
     */
    private int stockOptionGrant;

    /**
     * 2-22. 비고
     */
    private String note;

    public REP_INFO() { }

    public REP_INFO(Map<String, Object> ri) {
        this.seq                        = ri.get("seq").toString();
        this.refDate                    = ri.get("std_dt").toString();
        this.name                       = ri.get("name").toString();
        this.residentRegNum             = ri.get("pid").toString();
        this.repClassifCode             = ri.get("reper_ccd").toString();
        this.repMarkYN                  = ri.get("mrk_reper_yn").toString();
        this.inaugurateDate             = ri.get("asmp_dt").toString();
        this.retireDate                 = ri.get("rtir_dt").toString();
        this.currentStatusYN            = ri.get("po_yn").toString();
        this.title                      = ri.get("ttl").toString();
        this.mngtFormCode               = ri.get("mng_fcd").toString();
        this.repBeInSameIndustryYear    = Integer.parseInt(ri.get("sbzc_eg_ycn_reper").toString());
        this.repBeInSameIndustryMonth   = Integer.parseInt(ri.get("sbzc_eg_mcn_reper").toString());
        this.beInSameIndustryYear       = Integer.parseInt(ri.get("sind_eg_prd_yy").toString());
        this.beInSameIndustryMonth      = Integer.parseInt(ri.get("sind_eg_prd_mm").toString());
        this.sameIndustryRequtationCode = ri.get("sbzc_evl_cd").toString();
        this.relationWithAuthorityCode  = ri.get("mdm_rel_cd").toString();
        this.mngtCapacityCode           = ri.get("reper_mabl_cd").toString();
        this.stockHoldingOrdinary       = Integer.parseInt(ri.get("cstk_own").toString());
        this.stockHoldingPreferred      = Integer.parseInt(ri.get("pstk_own").toString());
        this.stockOptionGrant           = Integer.parseInt(ri.get("so_reg_cn").toString());
        this.note                       = ri.get("rmk").toString();
    }

}
