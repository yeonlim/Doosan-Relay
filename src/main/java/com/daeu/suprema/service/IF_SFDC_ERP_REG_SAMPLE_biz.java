package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE.IF_SFDC_ERP_REG_SAMPLE_Req;
import com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE.IF_SFDC_ERP_REG_SAMPLE_Res;
import com.daeu.suprema.repository.IF_SFDC_ERP_REG_SAMPLE_repo;
import com.daeu.suprema.util.WebCalloutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class IF_SFDC_ERP_REG_SAMPLE_biz extends WebCalloutUtil {

    @Autowired
    private IF_SFDC_ERP_REG_SAMPLE_repo repository;

    @Async("threadPoolTaskExecutor")
    public IF_SFDC_ERP_REG_SAMPLE_Res execute(IF_SFDC_ERP_REG_SAMPLE_Req objInput) {
        IF_SFDC_ERP_REG_SAMPLE_Res objOutput = new IF_SFDC_ERP_REG_SAMPLE_Res();

        boolean flag = repository.INSERT_SAMPLE_ORDER_LIST(objInput);

        objOutput.setResultCode(!flag ? "0000" : "9999");
        objOutput.setResultMessage(!flag ? "SUCCESS" : "ERROR");

        return objOutput;
    }
}
