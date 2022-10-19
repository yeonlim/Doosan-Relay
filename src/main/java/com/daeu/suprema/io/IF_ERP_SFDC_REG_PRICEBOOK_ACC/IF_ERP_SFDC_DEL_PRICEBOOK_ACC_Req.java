package com.daeu.suprema.io.IF_ERP_SFDC_REG_PRICEBOOK_ACC;

import lombok.Data;

import java.util.List;

@Data
public class IF_ERP_SFDC_DEL_PRICEBOOK_ACC_Req {
	/**
	 * 1. 고객별 할인율 정보 List
	 */
	private List<PRICEBOOK_ACC_DEL> pricebookAccList;
}
