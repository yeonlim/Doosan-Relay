package com.daeu.suprema.io.IF_ERP_SFDC_REG_SHIPTO;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class IF_ERP_SFDC_REG_SHIPTO_Req {
	/**
	 * 	1. 파트너 거래처 정보 List
	 */
	private List<SHIP_TO> shipToList;
}
