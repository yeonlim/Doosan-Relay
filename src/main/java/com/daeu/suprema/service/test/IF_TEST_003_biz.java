package com.daeu.suprema.service.test;

import com.daeu.suprema.io.test.IF_TEST_003.IF_TEST_003_Req;
import com.daeu.suprema.io.test.IF_TEST_003.IF_TEST_003_Res;
import com.daeu.suprema.repository.test.IF_TEST_003_repo;
import com.daeu.suprema.util.WebCalloutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class IF_TEST_003_biz extends WebCalloutUtil {

    @Autowired
    private IF_TEST_003_repo repository;

    @Async("threadPoolTaskExecutor")
    public int[] execute(IF_TEST_003_Req objInput) {
        IF_TEST_003_Res objOutput = new IF_TEST_003_Res();

        int[] result = null;

        try {
            result = repository.INSERT_USER_LIST(objInput);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

