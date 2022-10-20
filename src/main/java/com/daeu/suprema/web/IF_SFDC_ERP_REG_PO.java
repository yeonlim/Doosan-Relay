package com.daeu.suprema.web;

import com.daeu.suprema.io.IF_SFDC_ERP_REG_PO.IF_SFDC_ERP_REG_PO_Req;
import com.daeu.suprema.io.IF_SFDC_ERP_REG_PO.IF_SFDC_ERP_REG_PO_Res;
import com.daeu.suprema.service.IF_SFDC_ERP_REG_PO_biz;
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

@RestController
public class IF_SFDC_ERP_REG_PO extends WebCalloutUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IF_SFDC_ERP_REG_PO_biz service;

    private final String IFID = "IF_SFDC_ERP_REG_PO";

    @RequestMapping(value = "/V1/IF_SFDC_ERP_REG_PO", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public IF_SFDC_ERP_REG_PO_Res doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) {
        IF_SFDC_ERP_REG_PO_Req objInput = new IF_SFDC_ERP_REG_PO_Req();
        IF_SFDC_ERP_REG_PO_Res objOutput = new IF_SFDC_ERP_REG_PO_Res();

        try {
            ObjectMapper mapper = new ObjectMapper();
            objInput = mapper.readValue(fRequestBody, IF_SFDC_ERP_REG_PO_Req.class);
            objInput.paramValidChk(objOutput);

            if("1000".equals(objOutput.getResultCode())) {
                /* Business logic start */
                objOutput = service.execute(objInput);
                /* Business logic end */
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            objOutput.setResultCode("9999");
            objOutput.setResultMessage("RELAY SERVER ERROR");

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
