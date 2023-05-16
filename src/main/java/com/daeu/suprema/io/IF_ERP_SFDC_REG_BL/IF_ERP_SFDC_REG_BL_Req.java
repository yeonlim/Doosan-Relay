package com.daeu.suprema.io.IF_ERP_SFDC_REG_BL;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class IF_ERP_SFDC_REG_BL_Req {
	/**
	 * 	BL 정보 List
	 */
	private List<BL> blList;
}
