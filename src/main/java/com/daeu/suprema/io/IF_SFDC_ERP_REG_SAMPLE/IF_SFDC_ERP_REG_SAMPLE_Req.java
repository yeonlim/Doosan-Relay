package com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class IF_SFDC_ERP_REG_SAMPLE_Req {
	/**
	 * 	1. Order SFDC Key
	 */
	private String orderId;

	/**
	 * 	2. 요청일자
	 */
	private String startOrderDate;

	/**
	 * 	3. 비고
	 */
	private String remark;

	/**
	 * 	4. 수주 품목정보 List
	 */
	private List<PRODUCT> productList;

	public List<Map<String, Object>> getMapList() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		for(PRODUCT p : productList) {
			Map<String, Object> product = new HashMap<>();
			product.put("orderId"			, this.orderId);
			product.put("startOrderDate"	, this.startOrderDate);
			product.put("remark"			, this.remark);
			product.put("orderProductId"	, p.getOrderProductId());
			product.put("productCode"		, p.getProductCode());
			product.put("quantity"			, p.getQuantity());

			mapList.add(product);
		}


		return mapList;
	}
}
