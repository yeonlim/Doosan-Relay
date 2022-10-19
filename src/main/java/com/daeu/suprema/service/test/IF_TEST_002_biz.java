package com.daeu.suprema.service.test;

import com.daeu.suprema.io.test.IF_TEST_002.IF_TEST_002_Req;
import com.daeu.suprema.repository.test.IF_TEST_002_repo;
import com.daeu.suprema.util.WebCalloutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IF_TEST_002_biz extends WebCalloutUtil {

    @Autowired
    private IF_TEST_002_repo repository;

    @Async("threadPoolTaskExecutor")
    public List<Map<String, Object>> execute(IF_TEST_002_Req objInput) {
        List<Map<String, Object>> list = new ArrayList<>();

        try {
            list = repository.SELECT_USER_LIST(objInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

