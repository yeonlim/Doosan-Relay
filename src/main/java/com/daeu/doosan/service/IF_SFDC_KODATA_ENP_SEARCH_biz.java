package com.daeu.doosan.service;

import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_SEARCH.IF_SFDC_KODATA_ENP_SEARCH_Req;
import com.daeu.doosan.io.IF_SFDC_KODATA_ENP_SEARCH.IF_SFDC_KODATA_ENP_SEARCH_Res;
import com.daeu.doosan.util.HttpRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class IF_SFDC_KODATA_ENP_SEARCH_biz {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Value("${IF.SFDC.KODATA.ENP.SEARCH.PATH}")
    private String IF_SFDC_KODATA_ENP_SEARCH;

    public IF_SFDC_KODATA_ENP_SEARCH_Res excute(IF_SFDC_KODATA_ENP_SEARCH_Req objInput) {
        IF_SFDC_KODATA_ENP_SEARCH_Res objRes = new IF_SFDC_KODATA_ENP_SEARCH_Res();
        try {
            String path = IF_SFDC_KODATA_ENP_SEARCH;

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> mapParam = objectMapper.convertValue(objInput, Map.class);

            for (String key : mapParam.keySet()) {
                path += key + '=' + mapParam.get(key) + '&';
            }

            path = path.substring(0, path.length() - 1);

            // 요청
            String responseStr = httpRequestUtil.doGet(path);
            logger.info("response : {}", responseStr);

            if (!responseStr.isEmpty()) {
                objRes.setBody(responseStr);
                objRes.setResultCode("0000");
                objRes.setResultMessage("SUCCESS");
            } else {
                objRes.setResultCode("9999");
                objRes.setResultMessage("요청 실패");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

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
