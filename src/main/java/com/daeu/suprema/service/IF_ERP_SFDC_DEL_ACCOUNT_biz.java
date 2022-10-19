package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT.ACCOUNT_DEL;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT.IF_ERP_SFDC_ACCOUNT_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT.IF_ERP_SFDC_DEL_ACCOUNT_Req;
import com.daeu.suprema.repository.IF_ERP_SFDC_ACCOUNT_repo;
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
public class IF_ERP_SFDC_DEL_ACCOUNT_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.DEL.ACCOUNT.PATH}")
    private String IF_ERP_SFDC_DEL_ACCOUNT;

    @Autowired
    private IF_ERP_SFDC_ACCOUNT_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        int prcCnt = 0;
        Gson gson = new Gson();

        logger.info("=========================== [{}] ===========================", "IF_ERP_SFDC_DEL_ACCOUNT");
        logger.info("### Requst URL : {}", IF_ERP_SFDC_DEL_ACCOUNT);

        while (true) {
            prcCnt++;

            // 1. ACCOUNT 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> accountListMap = repository.SELECT_ACCOUNT_DEL_LIST(prcCnt);
            if(accountListMap == null || accountListMap.isEmpty()) {
                logger.info("Terminate the batch as there are no Rows to be interfaced.");
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<ACCOUNT_DEL> accountList = new ArrayList<>();
            for(Map<String, Object> a : accountListMap) {
                accountList.add(new ACCOUNT_DEL(a));
            }

            IF_ERP_SFDC_DEL_ACCOUNT_Req objReq = new IF_ERP_SFDC_DEL_ACCOUNT_Req();
            objReq.setAccountList(accountList);

            // 3. 요청
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_DEL_ACCOUNT, objReq);
            logger.info("response : {}", responseStr);

            IF_ERP_SFDC_ACCOUNT_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_ACCOUNT_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_ACCOUNT_LIST(accountListMap, prcCnt);
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