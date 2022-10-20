package com.daeu.suprema.io.IF_ERP_SFDC_REG_SOA;

import lombok.Data;

import java.util.List;

@Data
public class IF_ERP_SFDC_DEL_SOA_Req {
	/**
	 * 	1. 매출채권 정보 List
	 */
	private List<SOA_DEL> soaList;
}
