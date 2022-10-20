package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO.IF_ERP_SFDC_REG_SHIPTO_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO.IF_ERP_SFDC_SHIPTO_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO.SHIP_TO_REG;
import com.daeu.suprema.repository.IF_ERP_SFDC_SHIPTO_repo;
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
public class IF_ERP_SFDC_REG_SHIPTO_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.SHIPTO.PATH}")
    private String IF_ERP_SFDC_REG_SHIPTO;

    @Autowired
    private IF_ERP_SFDC_SHIPTO_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        int prcCnt = 0;
        Gson gson = new Gson();

        logger.info("=========================== [{}] ===========================", "IF_ERP_SFDC_REG_PACKING_LIST");
        logger.info("### Requst URL : {}", IF_ERP_SFDC_REG_SHIPTO);

        while (true) {
            // 1. 납품처 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> shiptoListMap = repository.SELECT_SHIPTO_REG_LIST(prcCnt);
            if(shiptoListMap == null || shiptoListMap.isEmpty()) {
                logger.info("Terminate the batch as there are no Rows to be interfaced.");
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<SHIP_TO_REG> shiptoList = new ArrayList<>();
            for(Map<String, Object> s : shiptoListMap) {
                shiptoList.add(new SHIP_TO_REG(s));
            }

            IF_ERP_SFDC_REG_SHIPTO_Req objReq = new IF_ERP_SFDC_REG_SHIPTO_Req();
            objReq.setShipToList(shiptoList);

            // 3. 요청
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_SHIPTO, objReq);
            logger.info("response : {}", responseStr);

            IF_ERP_SFDC_SHIPTO_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_SHIPTO_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_SHIPTO_LIST(shiptoListMap, prcCnt);
            } else {
                // TODO : 에러 발생시 응답 정보 UPDATE
            }

            try {
                Thread.sleep(1000);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("=========================================================================");
    }
}