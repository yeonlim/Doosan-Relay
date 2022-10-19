package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_PACKING_LIST.IF_ERP_SFDC_REG_PACKING_LIST_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PACKING_LIST.IF_ERP_SFDC_REG_PACKING_LIST_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PACKING_LIST.PACKING_LIST;
import com.daeu.suprema.repository.IF_ERP_SFDC_PACKING_LIST_repo;
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
public class IF_ERP_SFDC_REG_PACKING_LIST_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.PACKINGLIST.PATH}")
    private String IF_ERP_SFDC_REG_PACKING_LIST;

    @Autowired
    private IF_ERP_SFDC_PACKING_LIST_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        int prcCnt = 0;
        Gson gson = new Gson();

        logger.info("=========================== [{}] ===========================", "IF_ERP_SFDC_REG_PACKING_LIST");
        logger.info("### Requst URL : {}", IF_ERP_SFDC_REG_PACKING_LIST);

        while (true) {
            prcCnt++;

            // 1. 고객별 할인율 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> packingListMap = repository.SELECT_PACKING_REG_LIST(prcCnt);
            if(packingListMap == null || packingListMap.isEmpty()) {
                logger.info("Terminate the batch as there are no Rows to be interfaced.");
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<PACKING_LIST> packingList = new ArrayList<>();
            for(Map<String, Object> pl : packingListMap) {
                packingList.add(new PACKING_LIST(pl));
            }

            IF_ERP_SFDC_REG_PACKING_LIST_Req objReq = new IF_ERP_SFDC_REG_PACKING_LIST_Req();
            objReq.setPackingList(packingList);

            // 3. 요청
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_PACKING_LIST, objReq);
            logger.info("response : {}", responseStr);

            IF_ERP_SFDC_REG_PACKING_LIST_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_REG_PACKING_LIST_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_PACKING_LIST(packingListMap, prcCnt);
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
