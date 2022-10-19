package com.daeu.suprema.web.test;

import com.daeu.suprema.io.test.IF_TEST_003.IF_TEST_003_Req;
import com.daeu.suprema.io.test.IF_TEST_003.IF_TEST_003_Res;
import com.daeu.suprema.service.*;
import com.daeu.suprema.service.test.IF_TEST_003_biz;
import com.daeu.suprema.util.WebCalloutUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IF_DO_BATCH_001 extends WebCalloutUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IF_TEST_003_biz service;

    private final String IFID = "IF_DO_BATCH_001";

    @Autowired
    private IF_ERP_SFDC_REG_ACCOUNT_biz account;				// 거래처 정보

    @Autowired
    private IF_ERP_SFDC_REG_PRODUCT_biz product;				// 품목 정보

    @Autowired
    private IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz pricebookAcc;	    // 고객별 할인율 정보

    @Autowired
    private IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz pricebookQty;	    // 수량별 단가 정보

    @Autowired
    private IF_ERP_SFDC_REG_SHIPTO_biz shipto;					// 납품처 정보

    @Autowired
    private IF_ERP_SFDC_REG_SOA_biz soa;						// 매출채권 정보

    @Autowired
    private IF_ERP_SFDC_REG_BL_biz bl;							// BL 정보

    @Autowired
    private IF_ERP_SFDC_REG_PACKING_LIST_biz packingList;		// Packing List 정보

    @RequestMapping(value = "/V1/IF_DO_BATCH_001", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
    	Map<String, Object> objInput = new HashMap<>();
        Map<String, Object> objOutput = new HashMap<String, Object>() {{
            put("resultCode", "0000");
            put("resultMessage", "SUCCESS");
        }};

        ObjectMapper mapper = new ObjectMapper();
        objInput = mapper.readValue(fRequestBody, Map.class);

        String apiName = objInput.get("apiName").toString();
        logger.info("apiName : {}", apiName);

        switch (apiName) {
            case "ACCOUNT" : account.execute(); break;
            case "PRODUCT" : product.execute(); break;
            case "PRICEBOOKACC" : pricebookAcc.execute(); break;
            case "PRICEBOOKQTY" : pricebookQty.execute(); break;
            case "SOA" : soa.execute(); break;
            case "BL" :  bl.execute(); break;
            case "PACKINGLIST" : packingList.execute(); break;
            case "SHIPTO" : shipto.execute(); break;
            default:
        }

        logger.info("result : {}", objOutput);

        return objOutput;
    }
}
