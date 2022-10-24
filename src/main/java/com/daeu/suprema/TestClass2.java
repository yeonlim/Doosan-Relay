package com.daeu.suprema;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.BL;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.IF_ERP_SFDC_REG_BL_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.PRODUCT;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.SERIAL_NO;
import com.google.gson.Gson;

import java.util.*;

public class TestClass2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for(int i = 1; i <= 20; i++) {
            list.add(i);
        }
        System.out.println(list.toString());

        list.remove(new Integer(15));
        System.out.println(list.toString());
    }
}
