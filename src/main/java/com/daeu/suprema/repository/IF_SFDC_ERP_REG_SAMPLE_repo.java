package com.daeu.suprema.repository;

import com.daeu.suprema.io.IF_SFDC_ERP_REG_SAMPLE.IF_SFDC_ERP_REG_SAMPLE_Req;
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
public class IF_SFDC_ERP_REG_SAMPLE_repo {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS"};
    private final String[] IF_FIELDS = {"SFDC_OrderId", "SFDC_OrderProductId", "REQ_DT", "S_ITEM_CD", "REQ_QTY", "REMARK"};

    private final String INSERT_SAMPLE_ORDER_LIST = "INSERT INTO IF_SFDC_ERP_ETC_ISSUE(" + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", IF_FIELDS) + ")" +
            " VALUES('C', GETDATE(), 'R', :orderId, :orderProductId, CONVERT(DATE, :startOrderDate), :productCode, :quantity, :remark)";

    @Transactional(readOnly = true)
    public boolean INSERT_SAMPLE_ORDER_LIST(IF_SFDC_ERP_REG_SAMPLE_Req objInput) {
        logger.info("### Query : {}", INSERT_SAMPLE_ORDER_LIST);
        logger.info("### Data : {}", objInput.getMapList().toString());

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(objInput.getMapList());
        int[] result = primaryNamedJdbcTemplate.batchUpdate(INSERT_SAMPLE_ORDER_LIST, batch);
        logger.info("### Result : {}", result);

        return Arrays.asList(result).contains(0);
    }
}
