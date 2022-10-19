package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO.IF_ERP_SFDC_REG_SHIPTO_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO.IF_ERP_SFDC_REG_SHIPTO_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO.SHIP_TO;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_SOA.IF_ERP_SFDC_REG_SOA_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_SOA.IF_ERP_SFDC_REG_SOA_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_SOA.SOA;
import com.daeu.suprema.repository.IF_ERP_SFDC_REG_SHIPTO_repo;
import com.daeu.suprema.repository.IF_ERP_SFDC_REG_SOA_repo;
import com.daeu.suprema.util.HttpRequestUtil;
import com.daeu.suprema.util.WebCalloutUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IF_ERP_SFDC_REG_SOA_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.SOA.PATH}")
    private String IF_ERP_SFDC_REG_SOA;

    @Autowired
    private IF_ERP_SFDC_REG_SOA_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        Gson gson = new Gson();

        while (true) {
            // 1. 매출채권 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> soaListMap = repository.SELECT_SOA_LIST();
            if(soaListMap == null || soaListMap.isEmpty()) {
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<SOA> soaList = new ArrayList<>();
            for(Map<String, Object> s : soaListMap) {
                soaList.add(new SOA(s));
            }
            IF_ERP_SFDC_REG_SOA_Req objReq = new IF_ERP_SFDC_REG_SOA_Req();
            objReq.setSoaList(soaList);

            // 3. 요청
            logger.info("IF_ERP_SFDC_REG_SHIPTO : {}", IF_ERP_SFDC_REG_SOA);
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_SOA, objReq);
            IF_ERP_SFDC_REG_SOA_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_REG_SOA_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_SOA_LIST(soaListMap);
            }
        }
    }
}