package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRODUCT.IF_ERP_SFDC_REG_PRODUCT_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRODUCT.IF_ERP_SFDC_REG_PRODUCT_Res;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_PRODUCT.PRODUCT;
import com.daeu.suprema.repository.IF_ERP_SFDC_REG_PRODUCT_repo;
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
public class IF_ERP_SFDC_REG_PRODUCT_biz extends WebCalloutUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.ERP.SFDC.REG.PRODUCT.PATH}")
    private String IF_ERP_SFDC_REG_PRODUCT;

    @Autowired
    private IF_ERP_SFDC_REG_PRODUCT_repo repository;

    @Async("threadPoolTaskExecutor")
    public void execute() {
        Gson gson = new Gson();

        while (true) {
            // 1. PRODUCT 정보 조회 (최대 1000 Rows)
            List<Map<String, Object>> productListMap = repository.SELECT_PRODUCT_LIST();
            if(productListMap == null || productListMap.isEmpty()) {
                break;
            }

            // 2. API 요청 규격으로 Convert
            List<PRODUCT> productList = new ArrayList<>();
            for(Map<String, Object> p : productListMap) {
                productList.add(new PRODUCT(p));
            }
            IF_ERP_SFDC_REG_PRODUCT_Req objReq = new IF_ERP_SFDC_REG_PRODUCT_Req();
            objReq.setProductList(productList);

            // 3. 요청
            logger.info("IF_ERP_SFDC_REG_PRODUCT : {}", IF_ERP_SFDC_REG_PRODUCT);
            String responseStr = httpRequestUtil.doPost(IF_ERP_SFDC_REG_PRODUCT, objReq);
            IF_ERP_SFDC_REG_PRODUCT_Res objRes = gson.fromJson(responseStr, IF_ERP_SFDC_REG_PRODUCT_Res.class);

            // 4. 정상 응답 시, I/F Status 변경 (R -> P)
            if("0000".equals(objRes.getResultCode())) {
                repository.UPDATE_PRODUCT_LIST(productListMap);
            }
        }
    }
}
