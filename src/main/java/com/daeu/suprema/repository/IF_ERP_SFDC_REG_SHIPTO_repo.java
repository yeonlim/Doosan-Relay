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
public class IF_ERP_SFDC_REG_SHIPTO_repo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_REC_ID", "IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS", "IF_ERR_MSG"};
    private final String[] ACCOUNT_FIELDS = {"BP_CD AS PARTNER_BP_CD", "BP_RGST_NO", "BP_FULL_NM", "ADDR_ENG", "TEL_NO1", "CONTRY_CD", "BP_PRSN_NM", "CURRENCY", "PAY_METH", "DEAL_TYPE", "BOOS_ORDER_YN"};

    private final String SELECT_SHIPTO_LIST =
            "SELECT TOP 40 IESIS.IF_REC_ID, IESIS.IF_ACT_CODE, IESIS.IF_CRT_DT, IESIS.IF_STATUS, IESIS.IF_ERR_MSG, IESIS.BP_CD, IESIS.PARTNER_BP_CD, IIF(IESIS.USE_FG = 'Y', 'True', 'False') AS USE_FG, BP_RGST_NO, BP_FULL_NM, ADDR_ENG, TEL_NO1, CONTRY_CD, BP_PRSN_NM, CURRENCY, PAY_METH, DEAL_TYPE" +
                    " FROM dbo.IF_ERP_SFDC_INFO_SHIPTO IESIS" +
                    "     INNER JOIN (" +
                    "         SELECT BP_CD, BP_RGST_NO, BP_FULL_NM, ADDR_ENG, TEL_NO1, CONTRY_CD, BP_PRSN_NM, CURRENCY, PAY_METH, DEAL_TYPE" +
                    "         FROM dbo.IF_ERP_SFDC_INFO_ACCOUNT" +
                    "         WHERE IF_REC_ID IN (" +
                    "             SELECT MAX(IF_REC_ID)" +
                    "             FROM dbo.IF_ERP_SFDC_INFO_ACCOUNT" +
                    "             WHERE BP_CD IN (SELECT TOP 40 PARTNER_BP_CD FROM IF_ERP_SFDC_INFO_SHIPTO WHERE IF_STATUS = 'R' ORDER BY IF_REC_ID ASC)" +
                    "             GROUP BY BP_CD" +
                    "         )" +
                    "     )IESIA ON IESIS.PARTNER_BP_CD = IESIA.BP_CD" +
                    " WHERE IESIS.IF_STATUS = 'R'" +
                    " ORDER BY IESIS.IF_REC_ID ASC";

    private final String UPDATE_SHIPTO_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_SHIPTO" +
                    " SET IF_STATUS = 'P', IF_PRC_DT = GETDATE(), IF_ERR_MSG = 'OK'" +
                    " WHERE IF_REC_ID IN (:ifRecIdList)";

    private final String UPDATE_SHIPTO_ERROR_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_SHIPTO" +
                    " SET IF_STATUS = 'E', IF_PRC_DT = GETDATE(), IF_ERR_MSG = CONCAT('[', :errorCode, '] ', :errorMessage)" +
                    " WHERE IF_REC_ID = :recordId";

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_SHIPTO_LIST(int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, SELECT_SHIPTO_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_SHIPTO_LIST);
        logger.info("### Result #{} : {}", prcCnt, result);
        return result;
    }

    public boolean UPDATE_SHIPTO_LIST(List<Integer> ifRecIdList, int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, UPDATE_SHIPTO_LIST);
        logger.info("### Data #{} : {}", prcCnt, ifRecIdList);

        MapSqlParameterSource inQueryParams = new MapSqlParameterSource();
        inQueryParams.addValue("ifRecIdList", ifRecIdList);

        int result = primaryNamedJdbcTemplate.update(UPDATE_SHIPTO_LIST, inQueryParams);
        logger.info("### Result #{} : {}", prcCnt, result);

        return result == 0;
    }

    public boolean UPDATE_SHIPTO_ERROR_LIST(List<Error> errorList, int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, UPDATE_SHIPTO_ERROR_LIST);
        logger.info("### Data #{} : {}", prcCnt, errorList);

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(errorList);
        int[] result = primaryNamedJdbcTemplate.batchUpdate(UPDATE_SHIPTO_ERROR_LIST, batch);
        logger.info("### Result #{} : {}", prcCnt, result);

        return Arrays.asList(result).contains(0);
    }
}
