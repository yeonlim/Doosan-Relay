package com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO;

import lombok.Data;

@Data
public class IF_SFDC_KODATA_ENP_INFO_Req {
    private String userid;      //아이디 
    private String process;     //처리구분
    private String bzno;        //요청사업자번호 
    private String pidagryn;    //개인정보조회동의여부  - Y / N
    private String jmno;        //전문상세코드
    private String format;      //데이터 제공방식 

}
