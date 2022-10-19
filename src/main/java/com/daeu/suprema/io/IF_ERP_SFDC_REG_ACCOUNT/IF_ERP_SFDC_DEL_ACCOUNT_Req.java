package com.daeu.suprema.io.IF_ERP_SFDC_REG_ACCOUNT;

import lombok.Data;

import java.util.List;

@Data
public class IF_ERP_SFDC_DEL_ACCOUNT_Req {
    /**
     * 1. 거래처 정보 List
     */
    private List<ACCOUNT_DEL> accountList;
}
