package com.daeu.doosan.web;

import com.daeu.doosan.io.ResponseHeader;
import com.daeu.doosan.util.WebCalloutUtil;
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


public class IF_SFDC_KODATA_ENP_INFO {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/V1/IF_SFDC_KODATA_ENP_INFO", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseHeader doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) {
        ResponseHeader res = new ResponseHeader();

        try {
            res.setResultCode("0000"); // SUCCESS
            res.setResultMessage("SUCCESS");
            res.setBody(response);
        } catch (Exception e) {
            logger.error(e.getMessage());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            res.setResultCode("9999"); // ERROR
            res.setResultMessage(String.format("Internal Server Error - %s", e.getMessage()));

            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            pw.close();
        }

        return res;
    }
}