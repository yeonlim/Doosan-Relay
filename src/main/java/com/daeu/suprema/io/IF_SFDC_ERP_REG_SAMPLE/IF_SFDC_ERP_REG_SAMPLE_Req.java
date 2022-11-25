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
	 * 	4. 주문처 거래처코드
	 */
	private String soldToParty;

	/**
	 * 	5. SFDC Order 번호
	 */
	private String orderNumber;

	/**
	 * 	6. 수주 품목정보 List
	 */
	private List<PRODUCT> productList;

	public List<Map<String, Object>> getMapList() {
		List<Map<String, Object>> mapList = new ArrayList<>();

		for(PRODUCT p : productList) {
			Map<String, Object> product = new HashMap<>();
			product.put("orderId"			, this.orderId);
			product.put("startOrderDate"	, this.startOrderDate);
			product.put("remark"			, this.remark);
			product.put("soldToParty"		, this.soldToParty);
			product.put("orderNumber"		, this.orderNumber);
			product.put("orderProductId"	, p.getOrderProductId());
			product.put("productCode"		, p.getProductCode());
			product.put("quantity"			, p.getQuantity());

			mapList.add(product);
		}

		return mapList;
	}

	public Map<String, Object> getConfirmMap() {
		Map<String, Object> map = new HashMap<>();

		map.put("orderId", this.orderId);
		List<String> orderProductIdList = new ArrayList<>();
		for(PRODUCT p : this.productList) {
			orderProductIdList.add(p.getOrderProductId());
		}
		map.put("orderProductIdList", orderProductIdList);

		return map;
	}

    public void paramValidChk(IF_SFDC_ERP_REG_SAMPLE_Res objOutput) {
		if(this.orderId == null 			|| this.orderId.isEmpty()) 			{ objOutput.setResultCode("1000"); objOutput.setResultMessage("orderId is Empty"); 			}
		else if(this.startOrderDate == null || this.startOrderDate.isEmpty()) 	{ objOutput.setResultCode("1000"); objOutput.setResultMessage("startOrderDate is Empty"); 	}
		else if(this.soldToParty == null 	|| this.soldToParty.isEmpty())		{ objOutput.setResultCode("1000"); objOutput.setResultMessage("soldToParty is Empty"); 		}
		else if(this.orderNumber == null 	|| this.orderNumber.isEmpty())		{ objOutput.setResultCode("1000"); objOutput.setResultMessage("orderNumber is Empty"); 		}
		else if(this.productList == null 	|| this.productList.isEmpty())		{ objOutput.setResultCode("1000"); objOutput.setResultMessage("productList is Empty"); 		}

		if(!"1000".equals(objOutput.getResultCode())) {
			for(int i = 0; i < this.productList.size(); i++) {
				if(this.productList.get(i).getProductCode() == null 	|| "".equals(this.productList.get(i).getProductCode())) { objOutput.setResultCode("1000"); objOutput.setResultMessage(String.format("productList[%d].productCode is Empty", i)); break;	}
			}
		}
    }
}
