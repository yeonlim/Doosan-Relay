package com.daeu.suprema.io.IF_SFDC_ERP_REG_PO;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class IF_SFDC_ERP_REG_PO_Req {
	/**
	 * 	1. Order SFDC Key
	 */
	private String orderId;

	/**
	 * 	2. startOrderDate
	 */
	private String startOrderDate;

	/**
	 * 	3. 고객주문번호
	 */
	private String custPoNo;

	/**
	 * 	4. 화폐
	 */
	private String curCode;

	/**
	 * 	5. 주문처
	 */
	private String soldToParty;

	/**
	 * 	6. 납품처<br>
	 * 	 - 납품처의 나라코드
	 */
	private String shipToParty;

	/**
	 * 	7. 판매유형<br>
	 * 	 - 거래코드(기준코드정보)
	 */
	private String dealType;

	/**
	 * 	8. 결제조건<br>
	 * 	 - 결제조건(기준코드정보)
	 */
	private String payMethod;

	/**
	 * 	9. 운동방법<br>
	 * 	 - 운송방법(기준코드정보)
	 */
	private String transMethod;

	/**
	 * 	10. 가격조건<br>
	 * 	 - 가격조건(기준코드정보)
	 */
	private String incoTerms;

	/**
	 * 	11. 도착항<br>
	 * 	 - 납품처의 나라코드
	 */
	private String dischgePortCode;

	/**
	 * 	12. 선적항<br>
	 * 	 - Incheon Airport, South Korea(INA) 고정값
	 */
	private String loadingPortCode;

	/**
	 * 	13. 국가코드<br>
	 * 	 - 국가코드(기준코드정보)
	 */
	private String countryCode;

	/**
	 * 	14. 거래처 담당자
	 */
	private String accountManager;

	/**
	 * 	15. 운송방법2
	 */
	private String carrier;

	/**
	 * 	16. 공장<br>
	 * 	 - 슈프리마 공장(P1)
	 */
	private String plantCode;

	/**
	 * 	17. 부가세유형<br>
	 * 	 - 국내 : A(세금계산서), 해외 : G(수출신고필증매출)
	 *
	 */
	private String vatType;

	/**
	 * 	18. 부가세금액<br>
	 * 	 - 국내 : 수주금액의 10%, 해외 : 0
	 */
	private double vatAmt;

	/**
	 * 	19. 주문 비고
	 */
	private String orderRemark;

	/**
	 * 	20. 수주 품목정보 List
	 */
	private List<PRODUCT> productList;

    public List<Map<String, Object>> getMapList() {
		List<Map<String, Object>> mapList = new ArrayList<>();

		for(PRODUCT p : productList) {
			Map<String, Object> product = new HashMap<>();
			product.put("orderId"			, this.orderId);
			product.put("startOrderDate"	, this.startOrderDate);
			product.put("custPoNo"			, this.custPoNo);
			product.put("curCode"			, this.curCode);
			product.put("soldToParty"		, this.soldToParty);
			product.put("shipToParty"		, this.shipToParty);
			product.put("dealType"			, this.dealType);
			product.put("payMethod"			, this.payMethod);
			product.put("transMethod"		, this.transMethod);
			product.put("incoTerms"			, this.incoTerms);
			product.put("dischgePortCode"	, this.dischgePortCode);
			product.put("loadingPortCode"	, this.loadingPortCode);
			product.put("countryCode"		, this.countryCode);
			product.put("accountManager"	, this.accountManager);
			product.put("carrier"			, this.carrier);
			product.put("plantCode"			, this.plantCode);
			product.put("vatType"			, this.vatType);
			product.put("vatAmt"			, this.vatAmt);
			product.put("orderRemark"		, this.orderRemark);
			product.put("orderProductId"	, p.getOrderProductId());
			product.put("productCode"		, p.getProductCode());
			product.put("originCode"		, p.getOriginCode());
			product.put("quantity"			, p.getQuantity());
			product.put("unitPrice"			, p.getUnitPrice());
			product.put("totalPrice"		, p.getTotalPrice());
			product.put("discountAmt"		, p.getDiscountAmt());
			product.put("discountRate"		, p.getDiscountRate());
			product.put("remark"			, p.getRemark());

			mapList.add(product);
		}

		return mapList;
    }
}
