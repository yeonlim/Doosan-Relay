package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_QTY.IF_ERP_SFDC_REG_PRICEBOOK_QTY_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_QTY.IF_ERP_SFDC_REG_PRICEBOOK_QTY_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_QTY.PRICEBOOK_QTY;
import com.daeu.suprema.repository.IF_ERP_SFDC_REG_PRICEBOOK_QTY_repo;
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
public class IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.PRICEBOOK.QTY.PATH}")
    private String IF_ERP_SFDC_REG_PRICEBOOK_QTY;

    @Autowired
    private IF_ERP_SFDC_REG_PRICEBOOK_QTY_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        Gson gson = new Gson();

        while (true) {
            // 1. 수량별 단가 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> pricebookQtyListMap = repository.SELECT_PRICEBOOK_QTY_LIST();
            if(pricebookQtyListMap == null || pricebookQtyListMap.isEmpty()) {
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<PRICEBOOK_QTY> pricebookQtyList = new ArrayList<>();
            for(Map<String, Object> pq : pricebookQtyListMap) {
                pricebookQtyList.add(new PRICEBOOK_QTY(pq));
            }
            IF_ERP_SFDC_REG_PRICEBOOK_QTY_Req objReq = new IF_ERP_SFDC_REG_PRICEBOOK_QTY_Req();
            objReq.setPricebookQtyList(pricebookQtyList);

            // 3. 요청
            logger.info("IF_ERP_SFDC_REG_PRICEBOOK_ACC : {}", IF_ERP_SFDC_REG_PRICEBOOK_QTY);
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_PRICEBOOK_QTY, objReq);
            IF_ERP_SFDC_REG_PRICEBOOK_QTY_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_REG_PRICEBOOK_QTY_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_PRICEBOOK_QTY_LIST(pricebookQtyListMap);
            }
        }
    }
}
