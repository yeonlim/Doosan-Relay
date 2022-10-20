package com.daeu.suprema.io.IF_ERP_SFDC_REG_PACKING_LIST;

import lombok.Data;

import java.util.List;

@Data
public class IF_ERP_SFDC_DEL_PACKING_LIST_Req {
	/**
	 * 1. PACKING LIST 정보 List
	 */
	private List<PACKING_LIST_DEL> packingList;
}
