package com.daeu.suprema.repository;

import com.daeu.suprema.io.IF_SFDC_ERP_REG_PO.IF_SFDC_ERP_REG_PO_Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Repository
public class IF_SFDC_ERP_REG_PO_repo {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS"};
    private final String[] IF_FIELDS = {"SFDC_OrderId", "SFDC_OrderProductId", "SO_DT", "CUST_PO_NO", "CUR", "SOLD_TO_PARTY", "SHIP_TO_PARTY", "DEAL_TYPE",
            "PAY_METH", "TRANS_METH", "CARRIER_KO799", "INCOTERMS", "DISCHGE_PORT_CD", "LOADING_PORT_CD", "COUNTRY_CD_KO799", "BP_PRSN_NM_KO799", "ORIGIN_CD",
            "S_ITEM_CD", "PLANT_CD", "SO_QTY", "SO_PRICE", "NET_AMT", "DC_AMT", "DC_RATE_PRC", "VAT_TYPE", "VAT_AMT", "REMARK", "ITEM_ETC"};

    private final String INSERT_PO_LIST = "INSERT INTO IF_SFDC_ERP_REG_PO(" + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", IF_FIELDS) + ")" +
            " VALUES('C', GETDATE(), 'R', :orderId, :orderProductId, CONVERT(DATE, :startOrderDate), :custPoNo, :curCode, :soldToParty, :shipToParty, :dealType, " +
            ":payMethod, :transMethod, :carrier, :incoTerms, :dischgePortCode, :loadingPortCode, :countryCode, :accountManager, :originCode, " +
            ":productCode, :plantCode, :quantity, :unitPrice, :totalPrice, :discountAmt, :discountRate, :vatType, :vatAmt, :orderRemark, :remark)";

    @Transactional(readOnly = true)
    public boolean INSERT_PO_LIST(IF_SFDC_ERP_REG_PO_Req objInput) {
        logger.info("### Query : {}", INSERT_PO_LIST);
        logger.info("### Data : {}", objInput.getMapList().toString());

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(objInput.getMapList());
        int[] result = primaryNamedJdbcTemplate.batchUpdate(INSERT_PO_LIST, batch);
        logger.info("### Result : {}", result);

        return Arrays.asList(result).contains(0);
    }
}
