package com.daeu.doosan.web;


import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_SEARCH.IF_SFDC_KODATA_ENP_SEARCH_Req;
import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_SEARCH.IF_SFDC_KODATA_ENP_SEARCH_Res;
import com.daeu.doosan.io.ResponseHeader;
import com.daeu.doosan.service.IF_SFDC_KODATA_ENP_SEARCH_biz;
import com.daeu.doosan.util.WebCalloutUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class IF_SFDC_KODATA_ENP_SEARCH extends WebCalloutUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    IF_SFDC_KODATA_ENP_SEARCH_biz service = new IF_SFDC_KODATA_ENP_SEARCH_biz();

    @RequestMapping(value = "/V1/IF_SFDC_KODATA_ENP_SEARCH", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public IF_SFDC_KODATA_ENP_SEARCH_Res doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) {

        IF_SFDC_KODATA_ENP_SEARCH_Req objInput = new IF_SFDC_KODATA_ENP_SEARCH_Req();
        IF_SFDC_KODATA_ENP_SEARCH_Res objOutput = new IF_SFDC_KODATA_ENP_SEARCH_Res();

        try {
            ObjectMapper mapper = new ObjectMapper();
            objInput = mapper.readValue(fRequestBody, IF_SFDC_KODATA_ENP_SEARCH_Req.class);

            objOutput = service.excute(objInput);

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
