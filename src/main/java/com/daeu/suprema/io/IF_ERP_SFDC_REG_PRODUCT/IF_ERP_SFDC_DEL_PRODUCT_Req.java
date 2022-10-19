package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRODUCT;

import lombok.Data;

import java.util.List;

@Data
public class IF_ERP_SFDC_DEL_PRODUCT_Req {
	/**
	 * 1. 품목 정보 List
	 */
	private List<PRODUCT_DEL> productList;
}
