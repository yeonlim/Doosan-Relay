package com.daeu.doosan.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Spring Boot 실행시, 실행되는 클래스 (시작 직후 단 한번)
 */
@Component
public class ApplicationInit {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        logger.info("============================================");
        logger.info("spring.profiles.active : {}", env.getProperty("SPRING.PROFILES.ACTIVE", String.class));
        logger.info("server.port : {}", env.getProperty("SERVER.PORT", Integer.class));
        logger.info("============================================");
    }
}
