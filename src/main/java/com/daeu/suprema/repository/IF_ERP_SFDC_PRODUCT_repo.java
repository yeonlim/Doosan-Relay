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
public class IF_ERP_SFDC_PRODUCT_repo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_REC_ID", "IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS", "IF_ERR_MSG"};
    private final String[] REG_FIELDS = {"S_ITEM_CD", "S_ITEM_NM", "ITEM_GROUP_CD", "SPEC", "UNIT_WEIGHT", "HSCode_c", "IFF(ForecastTarget = 'Y', True, False) AS forcastTarget", "CountryofOrigin", "IFF(USE_YN = 'Y', True, False) AS isActive"};
    private final String[] DEL_FIELDS = {"S_ITEM_CD"};

    private final String SELECT_PRODUCT_REG_LIST =
            "SELECT TOP 1000 " + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", REG_FIELDS) +
                    " FROM dbo.IF_ERP_SFDC_INFO_PRODUCT" +
                    " WHERE IF_STATUS != 'P' AND IF_ACT_CODE != 'D'" +
                    " ORDER BY IF_CRT_DT ASC";

    private final String SELECT_PRODUCT_DEL_LIST =
            "SELECT TOP 1000 " + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", DEL_FIELDS) +
                    " FROM dbo.IF_ERP_SFDC_INFO_PRODUCT" +
                    " WHERE IF_STATUS != 'P' AND IF_ACT_CODE = 'D'" +
                    " ORDER BY IF_CRT_DT ASC";

    private final String UPDATE_PRODUCT_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_PRODUCT" +
            " SET IF_STATUS = 'P', IF_PRC_DT = GETDATE(), IF_ERR_MSG = 'OK'" +
            " WHERE IF_REC_ID = :IF_REC_ID";

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_PRODUCT_REG_LIST(int prcCnt) {
        logger.debug("### Query #{} : {}", prcCnt, SELECT_PRODUCT_REG_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_PRODUCT_REG_LIST);
        logger.debug("### Result #{} : {}", prcCnt, result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_PRODUCT_DEL_LIST(int prcCnt) {
        logger.debug("### Query #{} : {}", prcCnt, SELECT_PRODUCT_DEL_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_PRODUCT_DEL_LIST);
        logger.debug("### Result #{} : {}", prcCnt, result);
        return result;
    }

    public boolean UPDATE_PRODUCT_LIST(List<Map<String, Object>> productListMap, int prcCnt) {
        logger.debug("### Query #{} : {}", prcCnt, UPDATE_PRODUCT_LIST);
        logger.debug("### Data #{} : {}", prcCnt, productListMap.toString());

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(productListMap);
        int[] result = primaryNamedJdbcTemplate.batchUpdate(UPDATE_PRODUCT_LIST, batch);
        logger.debug("### Result #{} : {}", prcCnt, result);

        return Arrays.asList(result).contains(0);
    }
}