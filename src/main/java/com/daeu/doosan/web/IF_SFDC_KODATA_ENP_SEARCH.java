package com.daeu.doosan.web;


import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_SEARCH.IF_SFDC_KODATA_ENP_SEARCH_Req;
import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_SEARCH.IF_SFDC_KODATA_ENP_SEARCH_Res;
import com.daeu.doosan.service.IF_SFDC_KODATA_ENP_SEARCH_biz;
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
public class IF_SFDC_KODATA_ENP_SEARCH {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IF_SFDC_KODATA_ENP_SEARCH_biz service;

    @RequestMapping(value = "/V1/IF_SFDC_KODATA_ENP_SEARCH")
    @ResponseStatus(value = HttpStatus.OK)
    public IF_SFDC_KODATA_ENP_SEARCH_Res doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) {

        IF_SFDC_KODATA_ENP_SEARCH_Req objInput = new IF_SFDC_KODATA_ENP_SEARCH_Req();
        IF_SFDC_KODATA_ENP_SEARCH_Res objOutput = new IF_SFDC_KODATA_ENP_SEARCH_Res();

        try {
            ObjectMapper mapper = new ObjectMapper();
            objInput = mapper.readValue(fRequestBody, IF_SFDC_KODATA_ENP_SEARCH_Req.class);

            objOutput = service.execute(objInput);

        } catch (Exception e) {
            logger.error(e.getMessage());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            objOutput.setResultCode("9999"); // ERROR
            objOutput.setResultMessage(String.format("Internal Server Error - %s", e.getMessage()));

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
