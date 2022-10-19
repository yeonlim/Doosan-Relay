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
public class IF_ERP_SFDC_REG_PACKING_LIST_repo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_REC_ID", "IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS", "IF_ERR_MSG"};
    private final String[] IF_FIELDS = {"SFDC_ORDERID", "SFDC_ORDERPRODUCTID", "S_ITEM_CD", "QUANTITY_C", "PRODUCTWEIGHT_C", "TOTALWEIGHT_C", "BOXQUANTITY_C", "BOXDIMENSION_C", "VIEW_SEQ"};

    private final String SELECT_PACKING_LIST =
            "SELECT TOP 1 " + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", IF_FIELDS) +
            " FROM dbo.IF_ERP_SFDC_INFO_PACKING_LIST" +
            " WHERE IF_STATUS != 'P'" +
            " ORDER BY IF_CRT_DT ASC";

    private final String UPDATE_PACKING_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_PACKING_LIST" +
            " SET IF_STATUS = 'P', IF_PRC_DT = GETDATE(), IF_ERR_MSG = 'OK'" +
            " WHERE IF_REC_ID = :IF_REC_ID";

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_PACKING_LIST() {
        logger.info("### Query : {}", SELECT_PACKING_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_PACKING_LIST);
        logger.info("### Result : {}", result);
        return result;
    }

    public boolean UPDATE_PACKING_LIST(List<Map<String, Object>> packingListMap) {
        logger.info("### Query : {}", UPDATE_PACKING_LIST);
        logger.info("### Data : {}", packingListMap.toString());

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(packingListMap);
        int[] result = primaryNamedJdbcTemplate.batchUpdate(UPDATE_PACKING_LIST, batch);
        logger.info("### Result : {}", result);

        return Arrays.asList(result).contains(0);
    }
}
