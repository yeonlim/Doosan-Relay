package com.daeu.doosan.service;

import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO.IF_SFDC_KODATA_ENP_INFO_Req;
import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_INFO.IF_SFDC_KODATA_ENP_INFO_Res;
import com.daeu.doosan.util.HttpRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IF_SFDC_KODATA_ENP_INFO_biz {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    public IF_SFDC_KODATA_ENP_INFO_Res execute(IF_SFDC_KODATA_ENP_INFO_Req objInput) {
        IF_SFDC_KODATA_ENP_INFO_Res objRes = new IF_SFDC_KODATA_ENP_INFO_Res();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> mapParam = objectMapper.convertValue(objInput, Map.class);

            String path = mapParam.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            logger.info("path : " + path);

            // 요청
            String responseStr = httpRequestUtil.doGet(path);
            logger.info("response : {}", responseStr);

            if (!responseStr.isEmpty()) {
                objRes.setResultCode("0000");
                objRes.setResultMessage("SUCCESS");
                objRes.setBody(responseStr);
            } else {
                objRes.setResultCode("9999");
                objRes.setResultMessage("요청 실패");
            }
        } catch(Exception e) {
            logger.error("ERROR : " + e.getMessage() + " / Line : " + e.getStackTrace()[0].getLineNumber());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            pw.close();
        }
        return objRes;
    }
}
