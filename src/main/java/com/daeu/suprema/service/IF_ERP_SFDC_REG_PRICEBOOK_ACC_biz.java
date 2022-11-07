package com.daeu.suprema.service;

import com.daeu.suprema.io.Error;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_ACC.IF_ERP_SFDC_REG_PRICEBOOK_ACC_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_ACC.IF_ERP_SFDC_REG_PRICEBOOK_ACC_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_ACC.PRICEBOOK_ACC;
import com.daeu.suprema.repository.IF_ERP_SFDC_REG_PRICEBOOK_ACC_repo;
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
public class IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.PRICEBOOK.ACC.PATH}")
    private String IF_ERP_SFDC_REG_PRICEBOOK_ACC;

    @Autowired
    private IF_ERP_SFDC_REG_PRICEBOOK_ACC_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        int prcCnt = 0;
        Gson gson = new Gson();

        logger.info("=========================== [{}] ===========================", "IF_ERP_SFDC_REG_PRICEBOOK_ACC");
        logger.info("### Requst URL : {}", IF_ERP_SFDC_REG_PRICEBOOK_ACC);

        while (true) {
            prcCnt++;

            // 1. 고객별 할인율 정보 조회
            List<Map<String, Object>> pricebookAccListMap = repository.SELECT_PRICEBOOK_ACC_LIST(prcCnt);
            if(pricebookAccListMap == null || pricebookAccListMap.isEmpty()) {
                logger.info("Terminate the batch as there are no Rows to be interfaced.");
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<PRICEBOOK_ACC> pricebookAccList = new ArrayList<>();
            List<Integer> ifRecIdList = new ArrayList<>();
            for(Map<String, Object> pa : pricebookAccListMap) {
                pricebookAccList.add(new PRICEBOOK_ACC(pa));
                ifRecIdList.add(Integer.parseInt(pa.get("IF_REC_ID").toString()));
            }

            IF_ERP_SFDC_REG_PRICEBOOK_ACC_Req objReq = new IF_ERP_SFDC_REG_PRICEBOOK_ACC_Req();
            objReq.setPricebookAccList(pricebookAccList);

            // 3. 요청
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_PRICEBOOK_ACC, objReq);
            logger.info("response : {}", responseStr);

            IF_ERP_SFDC_REG_PRICEBOOK_ACC_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_REG_PRICEBOOK_ACC_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if(objRes.getErrorList() != null && objRes.getErrorList().size() > 0) {
                for(Error error : objRes.getErrorList()) {
                    ifRecIdList.remove(new Integer(error.getRecordId()));
                }
                repository.UPDATE_PRICEBOOK_ACC_ERROR_LIST(objRes.getErrorList(), prcCnt);
            }
            if(!ifRecIdList.isEmpty()) repository.UPDATE_PRICEBOOK_ACC_LIST(ifRecIdList, prcCnt);

            try {
                Thread.sleep(1000);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("=========================================================================");
    }
}
