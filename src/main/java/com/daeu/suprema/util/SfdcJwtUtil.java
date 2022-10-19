package com.daeu.suprema.util;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * SFDC OAuth App을 통해 JWT 토큰 발급
 */
@Component
public class SfdcJwtUtil {

    private Logger logger = LoggerFactory.getLogger(SfdcJwtUtil.class);

    final String CLAIM_TEMPLATE = "grant_type=password&username={0}&password={1}&client_id={2}&client_secret={3}";

    public String[] getJWT(String serverType, String profile) {
        logger.debug("serverType : {}, profile : {}", serverType, profile);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // Url
            URI uri = UriComponentsBuilder
                    .fromUriString("prod".equals(profile) ? "https://login.salesforce.com" : "https://test.salesforce.com") // 호출 도메인 설정
                    .path("/services/oauth2/token") // URL Path 설정
                    .encode()
                    .build()
                    .toUri();

            // Header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            logger.debug("headers : {}", headers.toString());

            // Body
            String[] claimArray = new String[4];

            if("DEV".equals(serverType)) {
                claimArray[0] = "interface@suprema.co.kr.supremadev";   // username (Interface용 계정정보)
                claimArray[1] = "daeu2022!!";                           // password (Interface용 계정정보)
                claimArray[2] = "3MVG9rnryk9FxFMVvmL8T77RuUnKcePVzU7UrW2PfrDv9yf7CBIfiqSt2KXqKp8yDKohD_FjC90qgQe3e7RvZ"; // Oauth App > Consumer Key
                claimArray[3] = "8A40471A831D0D0D1D8F69EB87CBA96AD883C6C95DC17AF6C511789B67591FF4";                      // Oauth App > Consumer Secret
            }

            logger.debug("username (Interface용 계정정보) : \"{}\"", claimArray[0]);
            logger.debug("password (Interface용 계정정보) : \"{}\"", claimArray[1]);
            logger.debug("Oauth App > Consumer Key : \"{}\"", claimArray[2]);
            logger.debug("Oauth App > Consumer Secret : \"{}\"", claimArray[3]);

            MessageFormat claims = new MessageFormat(CLAIM_TEMPLATE);
            String payload = claims.format(claimArray);
            logger.debug("payload : {}", payload);

            // 요청 Entity
            HttpEntity<String> reqEntity = new HttpEntity<String>(payload, headers);

            RestTemplate restTemplete = new RestTemplate();
            ResponseEntity<Map> result = null;

            try {
                result = restTemplete.postForEntity(uri, reqEntity, Map.class);
            } catch(Exception e) {
                logger.error(e.getMessage());
            }

            final int statusCode = result.getStatusCodeValue();
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("Error authenticating to Force.com : {}", statusCode);
                logger.error(result.toString());
                return null;
            }

            resultMap = result.getBody();
            logger.debug("Successful login");
            logger.debug("Response Status Code : {}", result.getStatusCode());
            logger.debug("Response Body : {}", result.getBody());
            logger.debug("- instance URL : {}", resultMap.get("instance_url"));
            logger.debug("- access token/session ID: {} {}", resultMap.get("token_type"), resultMap.get("access_token"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return new String[]{ resultMap.get("token_type").toString(), resultMap.get("access_token").toString(), resultMap.get("instance_url").toString() };
    }
}
