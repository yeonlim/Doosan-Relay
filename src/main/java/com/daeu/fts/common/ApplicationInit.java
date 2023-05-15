package com.daeu.fts.common;

import com.daeu.fts.socket.server.FileMessageServer;
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
    public void init() throws Exception {
        logger.info("============================================");
        logger.info("SPRING.PROFILES.ACTIVE : {}", env.getProperty("SPRING.PROFILES.ACTIVE", String.class));
        logger.info("SERVER.SOCKET.PORT : {}", env.getProperty("SERVER.SOCKET.PORT", Integer.class));
        // logger.info("SERVER.PORT : {}", env.getProperty("SERVER.PORT", Integer.class));
        logger.info("============================================");

        FileMessageServer.getInstance().run(env);
    }
}
