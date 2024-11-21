package com.daeu.doosan.aop;

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

    @Pointcut("execution(public * com.daeu.doosan.web.*.*(..))") // com.daeu.doosan.web 패키지 내의 모든 public 메소드에 대해 AOP를 적용
    public void cut() { }

    @Before("cut()") // 메소드 실행 전에 실행됨. cut()이라는 Pointcut을 적용받은 메소드들에 대해 실행됨
    public void before(JoinPoint jp) throws IOException {
        // 요청 파라미터를 로깅
        Object[] params = jp.getArgs();

        Object param = params[0];
        HttpServletRequest request = (HttpServletRequest) params[1];
        HttpServletResponse response = (HttpServletResponse) params[2];

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(param.toString(), Map.class);

        logger.info("=========================== [{}] ===========================", request.getRequestURI());
        logger.info("request param : {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
    }

    @AfterReturning(value = "cut()", returning = "returnValue") // 지정된 메소드가 정상적으로 종료된 후 실행되는 메소드 , "returnValue"는 메소드 실행 결과값을 받기 위한 변수
    public void afterReturning(JoinPoint jp, Object returnValue) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        logger.info("response param : {}", gson.toJson(returnValue));
        logger.info("=========================================================================");
    }
}
