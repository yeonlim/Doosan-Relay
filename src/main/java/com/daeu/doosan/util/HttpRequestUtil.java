package com.daeu.doosan.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

    public String doGet2(String path) {
        logger.info("doGet Method Start !");
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(path);
//
//        CloseableHttpResponse response = httpClient.execute(httpGet);

        RestTemplate restTemplete = new RestTemplate();
        ResponseEntity<String> result = restTemplete.getForEntity(path, String.class);

        logger.info("Response Status Code : {}", result.getStatusCode());
        logger.info("Response Body : {}", result.getBody());
        return result.getBody();
    }

    public String doGet(String path) {
        logger.info("doGet Method Start!");

        // Create HttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(path);

            // Execute GET request
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

                logger.info("Response Status Code: {}", statusCode);
                logger.info("Response Body: {}", responseBody);

                return responseBody;
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing HTTP GET request", e);
            return null;
        }
    }
}

