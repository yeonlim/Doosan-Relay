package com.daeu.suprema;

import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.BL;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.IF_ERP_SFDC_REG_BL_Req;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.PRODUCT;
import com.daeu.suprema.io.IF_ERP_SFDC_REG_BL.SERIAL_NO;
import com.google.gson.Gson;

import java.util.*;

public class TestClass {
    public static void main(String[] args) {
        // TEST 001
        String[] strArr = {"a", "b", "c", "d", "e", "f"};
        System.out.println(String.join(", ", strArr));

        // TEST 002
        IF_ERP_SFDC_REG_BL_Req req = new IF_ERP_SFDC_REG_BL_Req();
        List<BL> blList = new ArrayList<>();
        List<Map<String, Object>> mapList = getMapList();

        // ORDER
        Map<String, List<Map<String, Object>>> order = new HashMap<>();
        for(Map<String, Object> map : mapList) {
            String orderId = map.get("orderId").toString();

            List<Map<String, Object>> orderList = order.get(orderId);
            if(orderList == null) {
                orderList = new ArrayList<>();
            }

            orderList.add(map);
            order.put(orderId, orderList);
        }
        System.out.println("order.keySet().size() : " + order.keySet().size());

        for(List<Map<String, Object>> orderList : order.values()) {
            BL bl = new BL();
            Map<String, Object> o1 = orderList.get(0);
            bl.setOrderId(o1.get("orderId").toString());

            Map<String, PRODUCT> productMap = new HashMap<>();
            List<SERIAL_NO> serialNoList = new ArrayList<>();

            for(Map<String, Object> o : orderList) {
                String productId = o.get("productId").toString();
                String productCode = o.get("itemCd").toString();
                String serialNo = o.get("serialNo").toString();

                PRODUCT p = productMap.get(productId);
                if(p == null) {
                    p = new PRODUCT();
                    p.setProductId(productId);

                    productMap.put(productId, p);
                }

                SERIAL_NO s = new SERIAL_NO();
                s.setSerialNo(serialNo);
                s.setProductCode(productCode);
                serialNoList.add(s);
            }

            bl.setProductList(new ArrayList<PRODUCT>(productMap.values()));
            bl.setSerialNoList(serialNoList);
            blList.add(bl);
        }
        System.out.println("blList.size() : " + blList.size());
        req.setBlList(blList);

        Gson gson = new Gson();
        System.out.println(gson.toJson(req.toString()));
    }

    // BL 정보 리스트
    public static List<Map<String, Object>> getMapList() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Random random = new Random();

        int orderCnt = random.nextInt(100) + 1;
        System.out.println("orderCnt : " + orderCnt);

        for(int idx1 = 0; idx1 < orderCnt; idx1++) {
            String orderId = getUKey();

            int itemCnt = random.nextInt(100) + 1;
            for(int idx2 = 0; idx2 < itemCnt; idx2++) {
                String itemCd = getUKey();
                String productId = getUKey();

                int serialNoCnt = random.nextInt(100) + 1;
                for(int idx3 = 0; idx3 < serialNoCnt; idx3++) {
                    String serialNo = getUKey();

                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", orderId);
                    map.put("itemCd", itemCd);
                    map.put("productId", productId);
                    map.put("serialNo", serialNo);
                    mapList.add(map);
                }
            }
        }

        return mapList;
    }

    public static String getUKey() {
        StringBuffer temp = new StringBuffer();

        Random rnd = new Random();
        for (int i = 0; i < 20; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }

        return temp.toString();
    }
}
