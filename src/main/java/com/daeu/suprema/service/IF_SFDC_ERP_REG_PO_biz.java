package com.daeu.suprema.service;

import com.daeu.suprema.io.IF_SFDC_ERP_REG_PO.IF_SFDC_ERP_REG_PO_Req;
import com.daeu.suprema.io.IF_SFDC_ERP_REG_PO.IF_SFDC_ERP_REG_PO_Res;
import com.daeu.suprema.repository.IF_SFDC_ERP_REG_PO_repo;
import com.daeu.suprema.util.WebCalloutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IF_SFDC_ERP_REG_PO_biz extends WebCalloutUtil {

    @Autowired
    private IF_SFDC_ERP_REG_PO_repo repository;

    @Async("threadPoolTaskExecutor")
    public IF_SFDC_ERP_REG_PO_Res execute(IF_SFDC_ERP_REG_PO_Req objInput) {
        IF_SFDC_ERP_REG_PO_Res objOutput = new IF_SFDC_ERP_REG_PO_Res();

        // 기 등록된 수주 정보인지 Valid Check
        boolean registered = repository.CONFIRM_REG_PO_LIST(objInput);
        if(!registered) {
            objOutput.setResultCode("1003");
            objOutput.setResultMessage("This is already registered order information.");
        } else {
            boolean flag = repository.INSERT_PO_LIST(objInput);

            objOutput.setResultCode(!flag ? "0000" : "9999");
            objOutput.setResultMessage(!flag ? "SUCCESS" : "ERROR");
        }

        return objOutput;
    }
}
