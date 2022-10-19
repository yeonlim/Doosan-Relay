package com.daeu.suprema.repository;

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
import java.util.List;
import java.util.Map;

@Repository
public class IF_ERP_SFDC_PRICEBOOK_QTY_repo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_REC_ID", "IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS", "IF_ERR_MSG"};
    private final String[] REG_FIELDS = {"ITEM_GROUP_CD", "DEAL_TYPE", "SALES_UNIT", "FROM_AMT", "TO_AMT", "CURRENCY", "ITEM_PRICE"};
    private final String[] DEL_FIELDS = {"ITEM_GROUP_CD", "DEAL_TYPE", "SALES_UNIT", "FROM_AMT", "TO_AMT", "CURRENCY", "ITEM_PRICE"};

    private final String SELECT_PRICEBOOK_QTY_REG_LIST =
            "SELECT TOP 1000 " + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", REG_FIELDS) +
                    " FROM dbo.IF_ERP_SFDC_INFO_PRICEBOOK_QTY" +
                    " WHERE IF_STATUS != 'P' AND IF_ACT_CODE != 'D'" +
                    " ORDER BY IF_CRT_DT ASC";

    private final String SELECT_PRICEBOOK_QTY_DEL_LIST =
            "SELECT TOP 1000 " + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", DEL_FIELDS) +
                    " FROM dbo.IF_ERP_SFDC_INFO_PRICEBOOK_QTY" +
                    " WHERE IF_STATUS != 'P' AND IF_ACT_CODE = 'D'" +
                    " ORDER BY IF_CRT_DT ASC";

    private final String UPDATE_PRICEBOOK_QTY_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_PRICEBOOK_QTY" +
            " SET IF_STATUS = 'P'" +
            " WHERE IF_REC_ID = :IF_REC_ID";

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_PRICEBOOK_QTY_REG_LIST(int prcCnt) {
        logger.debug("### Query #{} : {}", prcCnt, SELECT_PRICEBOOK_QTY_REG_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_PRICEBOOK_QTY_REG_LIST);
        logger.debug("### Result #{} : {}", prcCnt, result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_PRICEBOOK_QTY_DEL_LIST(int prcCnt) {
        logger.debug("### Query #{} : {}", prcCnt, SELECT_PRICEBOOK_QTY_DEL_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_PRICEBOOK_QTY_DEL_LIST);
        logger.debug("### Result #{} : {}", prcCnt, result);
        return result;
    }

    public boolean UPDATE_PRICEBOOK_QTY_LIST(List<Map<String, Object>> pricebookQtyListMap, int prcCnt) {
        logger.debug("### Query #{} : {}", prcCnt, UPDATE_PRICEBOOK_QTY_LIST);
        logger.debug("### Data #{} : {}", prcCnt, pricebookQtyListMap.toString());

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(pricebookQtyListMap);
        int[] result = primaryNamedJdbcTemplate.batchUpdate(UPDATE_PRICEBOOK_QTY_LIST, batch);
        logger.debug("### Result #{} : {}", prcCnt, result);

        return Arrays.asList(result).contains(0);
    }
}