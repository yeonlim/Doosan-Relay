package com.daeu.suprema.web.test;

import com.daeu.suprema.io.test.IF_TEST_002.IF_TEST_002_Req;
import com.daeu.suprema.io.test.IF_TEST_002.IF_TEST_002_Res;
import com.daeu.suprema.service.test.IF_TEST_002_biz;
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
import java.util.List;
import java.util.Map;

@RestController
public class IF_TEST_002 extends WebCalloutUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IF_TEST_002_biz service;

    private final String IFID = "IF_TEST_002";

    @RequestMapping(value = "/V1/IF_TEST_002", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public IF_TEST_002_Res doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) {
    	IF_TEST_002_Req objInput = new IF_TEST_002_Req();
    	IF_TEST_002_Res objOutput = new IF_TEST_002_Res();

        try {
            ObjectMapper mapper = new ObjectMapper();
            objInput = mapper.readValue(fRequestBody, IF_TEST_002_Req.class);

            /* Business logic start */
            List<Map<String, Object>> list = service.execute(objInput);
            /* Business logic end */

            // result
            objOutput.setResultCode("0000");
            objOutput.setResultMessage("SUCCESS");

            // resultData
            objOutput.setUserList(list);
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
