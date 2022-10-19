package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_QTY;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class IF_ERP_SFDC_REG_PRICEBOOK_QTY_Req {
	/**
	 * 1. 수량별 단가 정보 List
	 */
	private List<PRICEBOOK_QTY> pricebookQtyList;
}
