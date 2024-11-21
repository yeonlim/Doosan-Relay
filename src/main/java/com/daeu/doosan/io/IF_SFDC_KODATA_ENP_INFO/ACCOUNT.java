package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

import java.util.Map;

@Data
public class ACCOUNT {

    /**
     * 1-1. 기업식별 ID (KoData)
     */
    private String enpID;

    /**
     * 1-2. 기업체명 (KoData)
     */
    private String enpName;

    /**
     * 1-3. 기업체명 TradeName (KoData)
     */
    private String enpTradeName;

    /**
     * 1-4. 기업체명 영문 (KoData)
     */
    private String enpEnglishName;

    /**
     * 1-5. 사업자등록번호
     */
    private String enpRegNum;

    /**
     * 1-6. 법인 주민등록번호
     */
    private String enpResidentRegNum;

    /**
     * 1-7. 대표자명
     */
    private String enpRepName;

    /**
     * 1-8. 기업유형
     */
    private String enpType;

    /**
     * 1-9. 기업규모
     */
    private String enpSize;

    /**
     * 1-10. 기업형태
     */
    private String enpForm;

    /**
     * 1-11. 기업공개
     */
    private String enpIpo;

    /**
     * 1-12. 기업상태
     */
    private String enpStatus;

    /**
     * 1-13. 기업상태 변경일
     */
    private String enpStatusChageDate;

    /**
     * 1-14. 기업형태 앞뒤구분
     */
    private String enpFormClassif;

    /**
     * 1-15. 업종코드
     */
    private String bizTypeCode;

    /**
     * 1-16. 업종명
     */
    private String bizTypeName;

    /**
     * 1-17. 우편번호
     */
    private String postalCode;

    /**
     * 1-18. 주소
     */
    private String address;

    /**
     * 1-19. 상세주소
     */
    private String addressDetail;

    /**
     * 1-20. 전화번호
     */
    private String telNum;

    /**
     * 1-21. 팩스번호
     */
    private String faxNum;

    /**
     * 1-22. 홈페이지
     */
    private String homepage;

    /**
     * 1-23. 대표 email
     */
    private String email;

    /**
     * 1-24. 설립일자
     */
    private String establishDate;

    /**
     * 1-25. 그룹명
     */
    private String groupName;

    /**
     * 1-26. 주 생산품
     */
    private String mainProduct;

    /**
     * 1-27. 정보기준일자
     */
    private String refDate;

    /**
     * 1-28. 결산기준월일
     */
    private String closingAccountsMMDD;

    /**
     * 1-29. 주거래여신기관코드
     */
    private String mainCreditInstCode;

    /**
     * 1-30. 주거래 여신기관
     */
    private String mainCreditInst;

    /**
     * 1-31. 당좌거래은행코드
     */
    private String currentDepositBankCode;

    /**
     * 1-32. 당좌거래은행
     */
    private String currentDepositBank;

    /**
     * 1-33. 업종명_대분류
     */
    private String bizTypeNameCategory1;

    /**
     * 1-34. 한시적중소기업 졸업예정일
     */
    private String tempSmallBizEstFinDate;

    /**
     * 1-35. 정부 및 공공기관 구분
     */
    private String publicEnp;

    /**
     * 1-36. 벤처기업 유무
     */
    private String ventureEnpYN;

    /**
     * 1-37. 재무업종코드
     */
    private String financeBizCode;

    /**
     * 1-38. 무역업 신고번호
     */
    private String tradeBizReportNum;

    /**
     * 1-39. 상장일자
     */
    private String listedDate;

    /**
     * 1-40. 상장폐지일자
     */
    private String unListedDate;

    public ACCOUNT() { }

    public ACCOUNT(Map<String, Object> a) {
        this.enpID                  = a.get("kedcd").toString();
        this.enpName                = a.get("enp_nm").toString();
        this.enpTradeName           = a.get("enp_nm_trd").toString();
        this.enpEnglishName         = a.get("enp_nm_eng").toString();
        this.enpRegNum              = a.get("bzno").toString();
        this.enpResidentRegNum      = a.get("cono_pid").toString();
        this.enpRepName             = a.get("reper_name").toString();
        this.enpType                = a.get("enp_typ").toString();
        this.enpSize                = a.get("enp_sze").toString();
        this.enpForm                = a.get("enp_fcd").toString();
        this.enpIpo                 = a.get("ipo_cd").toString();
        this.enpStatus              = a.get("enp_scd").toString();
        this.enpStatusChageDate     = a.get("enp_scd_chg_dt").toString();
        this.enpFormClassif         = a.get("enp_form_fr").toString();
        this.bizTypeCode            = a.get("bzc_cd").toString();
        this.bizTypeName            = a.get("bzc_nm").toString();
        this.postalCode             = a.get("loc_zip").toString();
        this.address                = a.get("loc_addra").toString();
        this.addressDetail          = a.get("loc_addrb").toString();
        this.telNum                 = a.get("tel_no").toString();
        this.faxNum                 = a.get("fax_no").toString();
        this.homepage               = a.get("hpage_url").toString();
        this.email                  = a.get("email").toString();
        this.establishDate          = a.get("estb_dt").toString();
        this.groupName              = a.get("grp_nm").toString();
        this.mainProduct            = a.get("mpd").toString();
        this.refDate                = a.get("std_dt").toString();
        this.closingAccountsMMDD    = a.get("acct_eddt").toString();
        this.mainCreditInstCode     = a.get("mtx_bnk_cd").toString();
        this.mainCreditInst         = a.get("mtx_bnk_nm").toString();
        this.currentDepositBankCode = a.get("ovd_tx_bnk_cd").toString();
        this.currentDepositBank     = a.get("ovd_tx_bnk_nm").toString();
        this.bizTypeNameCategory1   = a.get("bzc_nm_l").toString();
        this.tempSmallBizEstFinDate = a.get("grdt_pln_dt").toString();
        this.publicEnp              = a.get("pubi_fcd").toString();
        this.ventureEnpYN           = a.get("venp_yn").toString();
        this.financeBizCode         = a.get("fs_gzc_cd").toString();
        this.tradeBizReportNum      = a.get("trdbz_rpt_no").toString();
        this.listedDate             = a.get("list_dt").toString();
        this.unListedDate           = a.get("dlist_dt").toString();
    }

}
