package com.daeu.suprema.repository;

import com.daeu.suprema.io.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class IF_ERP_SFDC_REG_PRODUCT_repo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_REC_ID", "IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS", "IF_ERR_MSG"};
    private final String[] IF_FIELDS = {"S_ITEM_CD", "S_ITEM_NM", "ITEM_GROUP_CD", "SPEC", "UNIT_WEIGHT", "HSCode_c", "IIF(ForecastTarget = 'Y', 'True', 'False') AS forecastTarget", "CountryofOrigin", "IIF(USE_YN = 'Y', 'True', 'False') AS useYn"};

    private final String SELECT_PRODUCT_LIST =
            "SELECT TOP 40 " + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", IF_FIELDS) +
                    " FROM dbo.IF_ERP_SFDC_INFO_PRODUCT" +
                    " WHERE IF_STATUS = 'R'" +
                    " ORDER BY IF_REC_ID ASC";

    private final String UPDATE_PRODUCT_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_PRODUCT" +
                    " SET IF_STATUS = 'P', IF_PRC_DT = GETDATE(), IF_ERR_MSG = 'OK'" +
                    " WHERE IF_REC_ID IN (:ifRecIdList)";

    private final String UPDATE_PRODUCT_ERROR_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_PRODUCT" +
                    " SET IF_STATUS = 'E', IF_PRC_DT = GETDATE(), IF_ERR_MSG = CONCAT('[', :errorCode, '] ', :errorMessage)" +
                    " WHERE IF_REC_ID = :recordId";

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_PRODUCT_LIST(int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, SELECT_PRODUCT_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_PRODUCT_LIST);
        logger.info("### Result #{} : {}", prcCnt, result);
        return result;
    }

    public boolean UPDATE_PRODUCT_LIST(List<Integer> ifRecIdList, int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, UPDATE_PRODUCT_LIST);
        logger.info("### Data #{} : {}", prcCnt, ifRecIdList);

        MapSqlParameterSource inQueryParams = new MapSqlParameterSource();
        inQueryParams.addValue("ifRecIdList", ifRecIdList);

        int result = primaryNamedJdbcTemplate.update(UPDATE_PRODUCT_LIST, inQueryParams);
        logger.info("### Result #{} : {}", prcCnt, result);

        return result == 0;
    }

    public boolean UPDATE_PRODUCT_ERROR_LIST(List<Error> errorList, int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, UPDATE_PRODUCT_ERROR_LIST);
        logger.info("### Data #{} : {}", prcCnt, errorList);

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(errorList);
        int[] result = primaryNamedJdbcTemplate.batchUpdate(UPDATE_PRODUCT_ERROR_LIST, batch);
        logger.info("### Result #{} : {}", prcCnt, result);

        return Arrays.asList(result).contains(0);
    }
}