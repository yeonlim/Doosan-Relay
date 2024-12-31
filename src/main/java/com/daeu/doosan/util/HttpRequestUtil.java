package com.daeu.doosan.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * - HTTP Request Util Class
 */
@Component
public class HttpRequestUtil {

    private Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    public Map<String, Object> doGet(String path) {
        logger.info("doGet Method Start!");

        // Create HttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8.toString());

            HttpGet httpGet = new HttpGet(encodedPath);

            // Execute GET request
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

                logger.info("Response Status Code: {}", statusCode);
                logger.info("Response Body: {}", responseBody);

                Gson gson = new Gson();
                Map<String, Object> mapJsonBody = gson.fromJson(responseBody,  new TypeToken<Map<String, Object>>(){}.getType());

                return mapJsonBody;
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing HTTP GET request", e);
            return null;
        }
    }
}

