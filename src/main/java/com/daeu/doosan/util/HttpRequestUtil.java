package com.daeu.doosan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * - HTTP Request Util Class
 */
@Component
public class HttpRequestUtil {

    private Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    @Value("${IF.SFDC.KODATA.ENP.INFO.PATH}")
    private String IF_SFDC_KODATA_ENP_INFO;

    public String doGet(String path) {
        logger.info("doGet Method Start !");
        URI uri = UriComponentsBuilder
                .fromUriString(IF_SFDC_KODATA_ENP_INFO)          // 호출 도메인 설정
                .path(path)                                     // URL Path 설정
                .encode()
                .build()
                .toUri();
        System.out.println("IF_SFDC_KODATA_ENP_INFO : " + IF_SFDC_KODATA_ENP_INFO);
        logger.info("Request URL : {}", uri.toString());
        System.out.println("URL : " + IF_SFDC_KODATA_ENP_INFO + path);

        RestTemplate restTemplete = new RestTemplate();
        ResponseEntity<String> result = restTemplete.getForEntity(IF_SFDC_KODATA_ENP_INFO + path, String.class);

        logger.info("Response Status Code : {}", result.getStatusCode());
        logger.info("Response Body : {}", result.getBody());
        return result.getBody();
    }
}
