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
public class IF_ERP_SFDC_REG_SOA_repo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_REC_ID", "IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS", "IF_ERR_MSG"};
    private final String[] IF_FIELDS = {"SFDC_ORDERID", "IV_NO", "BP_CD", "CURRENCY", "BILL_AMT", "COLLECT_AMT", "BILL_DT", "INCOME_PLAN_DT", "REMARK", "INVOICE_DT", "BILL_NO"};

    private final String SELECT_SOA_LIST =
            "SELECT TOP 1000" + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", IF_FIELDS) +
            " FROM dbo.IF_ERP_SFDC_INFO_SOA" +
            " WHERE IF_STATUS != 'P'" +
            " ORDER BY IF_CRT_DT ASC";

    private final String UPDATE_SOA_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_SOA" +
            " SET IF_STATUS = 'P'" +
            " WHERE IF_REC_ID = :IF_REC_ID";

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_SOA_LIST() {
        logger.debug("### Query : {}", SELECT_SOA_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_SOA_LIST);
        logger.debug("### Result : {}", result);
        return result;
    }

    public boolean UPDATE_SOA_LIST(List<Map<String, Object>> soaListMap) {
        logger.debug("### Query : {}", UPDATE_SOA_LIST);
        logger.debug("### Data : {}", soaListMap.toString());

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(soaListMap);
        int[] result = primaryNamedJdbcTemplate.batchUpdate(UPDATE_SOA_LIST, batch);
        logger.debug("### Result : {}", result);

        return Arrays.asList(result).contains(0);
    }
}
