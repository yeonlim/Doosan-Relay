package com.daeu.suprema.web;

import com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE.IF_SFDC_ERP_REG_SAMPLE_Req;
import com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE.IF_SFDC_ERP_REG_SAMPLE_Res;
import com.daeu.suprema.service.IF_SFDC_ERP_REG_SAMPLE_biz;
import com.daeu.suprema.util.WebCalloutUtil;
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
import java.util.ArrayList;

@RestController
public class IF_SFDC_ERP_REG_SAMPLE extends WebCalloutUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IF_SFDC_ERP_REG_SAMPLE_biz service;

    private final String IFID = "IF_SFDC_ERP_REG_SAMPLE";

    @RequestMapping(value = "/V1/IF_SFDC_ERP_REG_SAMPLE", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public IF_SFDC_ERP_REG_SAMPLE_Res doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) {
        IF_SFDC_ERP_REG_SAMPLE_Req objInput;
        IF_SFDC_ERP_REG_SAMPLE_Res objOutput = new IF_SFDC_ERP_REG_SAMPLE_Res();

        try {
            ObjectMapper mapper = new ObjectMapper();
            objInput = mapper.readValue(fRequestBody, IF_SFDC_ERP_REG_SAMPLE_Req.class);
            objInput.paramValidChk(objOutput);

            if(!"1000".equals(objOutput.getResultCode())) {
                /* Business logic start */
                objOutput = service.execute(objInput);
                /* Business logic end */
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            objOutput = new IF_SFDC_ERP_REG_SAMPLE_Res();
            objOutput.setResultCode("9999");
            objOutput.setResultMessage("Internal Server Error");
            objOutput.setErrorList(new ArrayList<>());
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            pw.close();
        }

        return objOutput;
    }
}
