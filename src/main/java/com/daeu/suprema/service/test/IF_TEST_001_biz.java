package com.daeu.suprema.service.test;

import com.daeu.suprema.io.test.IF_TEST_001.IF_TEST_001_Req;
import com.daeu.suprema.io.test.IF_TEST_001.TestObj;
import com.daeu.suprema.util.WebCalloutUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IF_TEST_001_biz extends WebCalloutUtil {

    @Async("threadPoolTaskExecutor")
    public List<TestObj> execute(IF_TEST_001_Req objInput) {
        List<TestObj> objList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            TestObj testOBj = new TestObj();
            testOBj.setNum(i);
            testOBj.setStr("test" + i);
            objList.add(testOBj);
        }

        return objList;
    }
}

