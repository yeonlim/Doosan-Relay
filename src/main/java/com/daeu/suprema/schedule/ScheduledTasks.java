package com.daeu.suprema.schedule;

import com.daeu.suprema.service.*;
import com.daeu.suprema.util.HttpRequestUtil;
import com.daeu.suprema.util.WebCalloutUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("ScheduledTasks")
public class ScheduledTasks extends WebCalloutUtil {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HttpRequestUtil httpRequestUtil;

	@Value("${INTERFACE.SCHEDULE.ENABLE}")
	private boolean scheduleEnable;

	@Autowired
	private IF_ERP_SFDC_REG_ACCOUNT_biz IF_ERP_SFDC_REG_ACCOUNT_biz;				// 거래처 정보

	@Autowired
	private IF_ERP_SFDC_REG_PRODUCT_biz IF_ERP_SFDC_REG_PRODUCT_biz;				// 품목 정보

	@Autowired
	private IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz;	// 고객별 할인율 정보

	@Autowired
	private IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz;	// 수량별 단가 정보

	@Autowired
	private IF_ERP_SFDC_REG_SHIPTO_biz IF_ERP_SFDC_REG_SHIPTO_biz;					// 납품처 정보

	@Autowired
	private IF_ERP_SFDC_REG_SOA_biz IF_ERP_SFDC_REG_SOA_biz;						// 매출채권 정보

	@Autowired
	private IF_ERP_SFDC_REG_BL_biz IF_ERP_SFDC_REG_BL_biz;							// BL 정보

	@Autowired
	private IF_ERP_SFDC_REG_PACKING_LIST_biz IF_ERP_SFDC_REG_PACKING_LIST_biz;		// Packing List 정보

	/**
	 *	거래처 정보 관리 Batch
	 */
	public void IF_ERP_SFDC_REG_ACCOUNT_BATCH() {
		if(!scheduleEnable) return;

		IF_ERP_SFDC_REG_ACCOUNT_biz.execute();
		IF_ERP_SFDC_REG_SHIPTO_biz.execute();
	}

	/**
	 *	품목 정보 관리 Batch
	 */
	public void IF_ERP_SFDC_REG_PRODUCT_BATCH() {
		if(!scheduleEnable) return;

		IF_ERP_SFDC_REG_PRODUCT_biz.execute();
	}

	/**
	 *	고객별 할인율 정보 관리 Batch
	 */
	public void IF_ERP_SFDC_REG_PRICEBOOK_ACC_BATCH() {
		if(!scheduleEnable) return;

		IF_ERP_SFDC_REG_PRICEBOOK_ACC_biz.execute();
	}

	/**
	 *	수량별 단가 정보 관리 Batch
	 */
	public void IF_ERP_SFDC_REG_PRICEBOOK_QTY_BATCH() {
		if(!scheduleEnable) return;

		IF_ERP_SFDC_REG_PRICEBOOK_QTY_biz.execute();
	}

	/**
	 *	매출채권 정보 관리 Batch
	 */
	public void IF_ERP_SFDC_REG_SOA_BATCH() {
		if(!scheduleEnable) return;

		IF_ERP_SFDC_REG_SOA_biz.execute();
	}

	/**
	 *	BL 정보 관리 Batch
	 */
	public void IF_ERP_SFDC_REG_BL_BATCH() {
		if(!scheduleEnable) return;

		IF_ERP_SFDC_REG_BL_biz.execute();
	}

	/**
	 *	Packing List 정보 관리 Batch
	 */
	public void IF_ERP_SFDC_REG_PACKING_LIST_BATCH() {
		if(!scheduleEnable) return;

		IF_ERP_SFDC_REG_PACKING_LIST_biz.execute();
	}
}