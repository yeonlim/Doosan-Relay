package com.daeu.suprema.service;

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
        Gson gson = new Gson();

        while (true) {
            // 1. BL 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> blListMap = repository.SELECT_BL_LIST();
            if(blListMap == null || blListMap.isEmpty()) {
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<BL> blList = new ArrayList<>();
            Map<String, List<Map<String, Object>>> blOrderMap = new HashMap<>();

            // 2-1. OrderId 매핑 작업
            for(Map<String, Object> b : blListMap) {
                String orderId = b.get("orderId").toString();

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
            logger.info("IF_ERP_SFDC_REG_BL : {}", IF_ERP_SFDC_REG_BL);
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_BL, objReq);
            IF_ERP_SFDC_REG_BL_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_REG_BL_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_BL_LIST(blListMap);
            }
        }
    }
}