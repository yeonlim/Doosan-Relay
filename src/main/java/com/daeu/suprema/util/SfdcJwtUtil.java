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
                    .fromUriString("PROD".equals(profile) ? "https://login.salesforce.com" : "https://test.salesforce.com") // 호출 도메인 설정
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
                claimArray[1] = "daeu2023!!";                           // password (Interface용 계정정보)
                claimArray[2] = "3MVG9rnryk9FxFMVvmL8T77RuUnKcePVzU7UrW2PfrDv9yf7CBIfiqSt2KXqKp8yDKohD_FjC90qgQe3e7RvZ"; // Oauth App > Consumer Key
                claimArray[3] = "8A40471A831D0D0D1D8F69EB87CBA96AD883C6C95DC17AF6C511789B67591FF4";                      // Oauth App > Consumer Secret
            } else if("PARTIAL".equals(serverType)) {
                claimArray[0] = "interface@suprema.co.kr.ps";           // username (Interface용 계정정보)
                claimArray[1] = "daeu2022!!";                           // password (Interface용 계정정보)
                claimArray[2] = "3MVG9Po2PmyYruukUgouKjEAFdiCvge1FcLfq6yjr2m_MaKnrvjL3dcXbLzPSGl13KYNvfutGiWdKPAgMRm9u"; // Oauth App > Consumer Key
                claimArray[3] = "23265584F8C34A602A84168ED9ADFA86DA3E2AF42405B653C7D9C66828F0C394";                      // Oauth App > Consumer Secret
            } else if("FSB".equals(serverType)) {
                claimArray[0] = "interface@suprema.co.kr.fsb";          // username (Interface용 계정정보)
                claimArray[1] = "daeu2022!!";                           // password (Interface용 계정정보)
                claimArray[2] = "3MVG9Po2PmyYruun6G1tJUk56uDn5Qn8.rQBWOnqI3RMW2EgUdgKWm.EbNhhjVqYH1x9kTns9Xab58lR07IDj"; // Oauth App > Consumer Key
                claimArray[3] = "38AC3D3C18D83BF9D295E60B2C354B9E99A4006F0E757736989AEA8E538996E1";                      // Oauth App > Consumer Secret
            } else if("PROD".equals(serverType)) {
                claimArray[0] = "shryu@suprema.co.kr";                  // username (Interface용 계정정보)
                claimArray[1] = "fbtmdgus!1";                           // password (Interface용 계정정보)
                claimArray[2] = "3MVG9ZL0ppGP5UrDURMzPu5hyrQhCBxUFnnLfvxdOGaGashfy5gMcDOS3zEM.EJmtLMof8pyEH5vFhOy6V4dt"; // Oauth App > Consumer Key
                claimArray[3] = "2DEEF52F89A805582649565B5C0BC18B2CA360937318EA2C2B686F19C1714A4E";                      // Oauth App > Consumer Secret
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
