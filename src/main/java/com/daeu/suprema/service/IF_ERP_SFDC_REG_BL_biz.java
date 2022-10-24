package com.daeu.suprema.service;

import com.daeu.suprema.io.Error;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.*;
import com.daeu.suprema.repository.IF_ERP_SFDC_REG_BL_repo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IF_ERP_SFDC_REG_BL_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.BL.PATH}")
    private String IF_ERP_SFDC_REG_BL;

    @Autowired
    private IF_ERP_SFDC_REG_BL_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        int prcCnt = 0;
        Gson gson = new Gson();

        logger.info("=========================== [{}] ===========================", "IF_ERP_SFDC_REG_BL");
        logger.info("### Requst URL : {}", IF_ERP_SFDC_REG_BL);

        while (true) {
            prcCnt++;

            // 1. BL 정보 조회
            List<Map<String, Object>> blListMap = repository.SELECT_BL_LIST(prcCnt);
            if(blListMap == null || blListMap.isEmpty()) {
                logger.info("Terminate the batch as there are no Rows to be interfaced.");
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<BL> blList = new ArrayList<>();
            List<Integer> ifRecIdList = new ArrayList<>();
            Map<String, List<Map<String, Object>>> blOrderMap = new HashMap<>();

            // 2-1. OrderId 매핑 작업
            for(Map<String, Object> b : blListMap) {
                String orderId = b.get("orderId").toString();
                ifRecIdList.add(Integer.parseInt(b.get("IF_REC_ID").toString()));

                List<Map<String, Object>> blOrderList = blOrderMap.get(orderId);
                if(blOrderList == null) {
                    blOrderList = new ArrayList<>();
                }

                blOrderList.add(b);
                blOrderMap.put(orderId, blOrderList);
            }

            // 2-2. Order별 ProductList, SerialNoList 등 설정
            for(List<Map<String, Object>> bList : blOrderMap.values()) {
                BL bl = new BL(bList.get(0));

                Map<String, PRODUCT> productMap = new HashMap<>();
                List<SERIAL_NO> serialNoList = new ArrayList<>();
                for(Map<String, Object> o : bList) {
                    String productId = o.get("SFDC_ORDERPRODUCTID").toString();

                    PRODUCT p = productMap.get(productId);
                    if(p == null) {
                        productMap.put(productId, new PRODUCT(o));
                    }

                    serialNoList.add(new SERIAL_NO(o));
                }

                bl.setProductList(new ArrayList<>(productMap.values()));
                bl.setSerialNoList(serialNoList);
                blList.add(bl);
            }

            IF_ERP_SFDC_REG_BL_Req objReq = new IF_ERP_SFDC_REG_BL_Req();
            objReq.setBlList(blList);

            // 3. 요청
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_BL, objReq);
            logger.info("response : {}", responseStr);

            IF_ERP_SFDC_BL_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_BL_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if(objRes.getErrorList() != null && objRes.getErrorList().size() > 0) {
                for(Error error : objRes.getErrorList()) {
                    ifRecIdList.remove(new Integer(error.getRecordId()));
                }
                repository.UPDATE_BL_ERROR_LIST(objRes.getErrorList(), prcCnt);
            }
            repository.UPDATE_BL_LIST(ifRecIdList, prcCnt);

            try {
                Thread.sleep(1000);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("=========================================================================");
    }
}