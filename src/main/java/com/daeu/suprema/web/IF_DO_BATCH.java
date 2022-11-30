package com.daeu.suprema.web;

import com.daeu.suprema.service.*;
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
import java.util.HashMap;
import java.util.Map;

@RestController
public class IF_DO_BATCH extends WebCalloutUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String IFID = "IF_DO_BATCH_001";

    @Autowired
    private IF_ERP_SFDC_REG_ACCOUNT_biz IF_ERP_SFDC_REG_ACCOUNT_biz;				// 거래처 정보

    @Autowired
    private IF_ERP_SFDC_REG_PRODUCT_biz IF_ERP_SFDC_REG_PRODUCT_biz;				// 품목 정보

    @Autowired
    private IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz;	// 고객별 할인율 정보

    @Autowired
    private IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz;	// 수량별 단가 정보

    @Autowired
    private IF_ERP_SFDC_REG_SHIPTO_biz IF_ERP_SFDC_REG_SHIPTO_biz;					// 납품처 정보

    @Autowired
    private IF_ERP_SFDC_REG_SOA_biz IF_ERP_SFDC_REG_SOA_biz;						// 매출채권 정보

    @Autowired
    private IF_ERP_SFDC_REG_BL_biz IF_ERP_SFDC_REG_BL_biz;							// BL 정보

    @Autowired
    private IF_ERP_SFDC_REG_PACKING_LIST_biz IF_ERP_SFDC_REG_PACKING_LIST_biz;		// Packing List 정보

    @RequestMapping(value = "/V1/IF_DO_BATCH", method = RequestMethod.POST)
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
            case "IF_ERP_SFDC_REG_ACCOUNT" : case "IF_ERP_SFDC_REG_SHIPTO" :
                IF_ERP_SFDC_REG_ACCOUNT_biz.execute();
                IF_ERP_SFDC_REG_SHIPTO_biz.execute();
                break;
            case "IF_ERP_SFDC_REG_PRODUCT" :
                IF_ERP_SFDC_REG_PRODUCT_biz.execute();
                break;
            case "IF_ERP_SFDC_REG_PRICEBOOK_ACC" :
                IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz.execute();
                break;
            case "IF_ERP_SFDC_REG_PRICEBOOK_QTY" :
                IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz.execute();
                break;
            case "IF_ERP_SFDC_REG_SOA" :
                IF_ERP_SFDC_REG_SOA_biz.execute();
                break;
            case "IF_ERP_SFDC_REG_BL" :
                IF_ERP_SFDC_REG_BL_biz.execute();
                break;
            case "IF_ERP_SFDC_REG_PACKING_LIST" :
                IF_ERP_SFDC_REG_PACKING_LIST_biz.execute();
                break;
            case "IF_DO_BATCH" :
                IF_ERP_SFDC_REG_ACCOUNT_biz.execute();
                IF_ERP_SFDC_REG_SHIPTO_biz.execute();
                IF_ERP_SFDC_REG_PRODUCT_biz.execute();
                IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz.execute();
                IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz.execute();
                IF_ERP_SFDC_REG_SOA_biz.execute();
                IF_ERP_SFDC_REG_BL_biz.execute();
                IF_ERP_SFDC_REG_PACKING_LIST_biz.execute();
                break;
            default:
        }

        return objOutput;
    }
}
