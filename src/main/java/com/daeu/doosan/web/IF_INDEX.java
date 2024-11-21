package com.daeu.doosan.web;

import com.daeu.doosan.util.WebCalloutUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class IF_INDEX extends WebCalloutUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String doPost(@RequestBody String fRequestBody, HttpServletRequest request, HttpServletResponse response) {
        logger.info("INDEX");
        return "INDEX";
    }
}
