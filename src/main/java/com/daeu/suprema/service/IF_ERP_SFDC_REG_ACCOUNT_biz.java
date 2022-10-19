package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT.ACCOUNT;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT.IF_ERP_SFDC_REG_ACCOUNT_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT.IF_ERP_SFDC_REG_ACCOUNT_Res;
import com.daeu.suprema.repository.IF_ERP_SFDC_REG_ACCOUNT_repo;
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
public class IF_ERP_SFDC_REG_ACCOUNT_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.ACCOUNT.PATH}")
    private String IF_ERP_SFDC_REG_ACCOUNT;

    @Autowired
    private IF_ERP_SFDC_REG_ACCOUNT_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        Gson gson = new Gson();

        while (true) {
            // 1. ACCOUNT 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> accountListMap = repository.SELECT_ACCOUNT_LIST();
            if(accountListMap == null || accountListMap.isEmpty()) {
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<ACCOUNT> accountList = new ArrayList<>();
            for(Map<String, Object> a : accountListMap) {
                accountList.add(new ACCOUNT(a));
            }
            IF_ERP_SFDC_REG_ACCOUNT_Req objReq = new IF_ERP_SFDC_REG_ACCOUNT_Req();
            objReq.setAccountList(accountList);

            // 3. 요청
            logger.info("IF_ERP_SFDC_REG_ACCOUNT : {}", IF_ERP_SFDC_REG_ACCOUNT);
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_ACCOUNT, objReq);
            IF_ERP_SFDC_REG_ACCOUNT_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_REG_ACCOUNT_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_ACCOUNT_LIST(accountListMap);
            }
        }
    }
}