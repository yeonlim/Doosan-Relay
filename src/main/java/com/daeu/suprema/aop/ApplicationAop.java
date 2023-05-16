package com.daeu.suprema.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Aspect
@Component
public class ApplicationAop {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.daeu.suprema.web.*.*(..))")
    public void cut() { }

    @Before("cut()")
    public void before(JoinPoint jp) throws IOException {
        Object[] params = jp.getArgs();

        Object param = params[0];
        HttpServletRequest request = (HttpServletRequest) params[1];
        HttpServletResponse response = (HttpServletResponse) params[2];

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(param.toString(), Map.class);

        logger.info("=========================== [{}] ===========================", request.getRequestURI());
        logger.info("request param : {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
    }

    @AfterReturning(value = "cut()", returning = "returnValue")
    public void afterReturning(JoinPoint jp, Object returnValue) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        logger.info("response param : {}", gson.toJson(returnValue));
        logger.info("=========================================================================");
    }
}
