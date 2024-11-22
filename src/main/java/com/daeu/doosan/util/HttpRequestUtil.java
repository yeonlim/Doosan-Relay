package com.daeu.doosan.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @Autowired
    SfdcJwtUtil sfdcJwtUtil;

    @Value("${SPRING.PROFILES.ACTIVE}")
    private String profile;

    @Value("${SFDC.SERVER.TYPE}")
    private String serverType;

    public String doPost(String path, Object body) {
        // [토큰타입, 토큰, 도메인]
        String[] jwtInfo = sfdcJwtUtil.getJWT(serverType, profile);

        // Url
        URI uri = UriComponentsBuilder
                .fromUriString(jwtInfo[2])          // 호출 도메인 설정
                .path("/services/apexrest" + path)  // URL Path 설정
                .encode()
                .build()
                .toUri();
        logger.info("Request URL : {}", uri.toString());

        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("%s %s", jwtInfo[0], jwtInfo[1]));
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("headers : {}", headers.toString());

        // Body
        logger.info("Request BODY : {}", new Gson().toJson(body));

        // 요청 Entity
        HttpEntity<Object> reqEntity = new HttpEntity<Object>(body, headers);

        RestTemplate restTemplete = new RestTemplate();
        ResponseEntity<String> result = restTemplete.postForEntity(uri, reqEntity, String.class);

        logger.info("Response Status Code : {}", result.getStatusCode());
        logger.info("Response Body : {}", result.getBody());

        return result.getBody();
    }

    public String doGet(String path) {
        // [토큰타입, 토큰, 도메인]
        String[] jwtInfo = sfdcJwtUtil.getJWT(serverType, profile);

        // Url
        URI uri = UriComponentsBuilder
                .fromUriString(jwtInfo[2])          // 호출 도메인 설정
                .path(path)                         // URL Path 설정
                .encode()
                .build()
                .toUri();
        logger.info("Request URL : {}", uri.toString());

        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("%s %s", jwtInfo[0], jwtInfo[1]));
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("headers : {}", headers.toString());

        // 요청 Entity
        HttpEntity<Object> reqEntity = new HttpEntity<Object>(headers);

        RestTemplate restTemplete = new RestTemplate();
        ResponseEntity<String> result = restTemplete.postForEntity(uri, reqEntity, String.class);

        logger.info("Response Status Code : {}", result.getStatusCode());
        logger.info("Response Body : {}", result.getBody());

        return result.getBody();
    }
}
