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
public class IF_ERP_SFDC_REG_BL_repo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

    private final String[] COMMON_FIELDS = {"IF_REC_ID", "IF_ACT_CODE", "IF_CRT_DT", "IF_STATUS", "IF_ERR_MSG"};
    private final String[] IF_FIELDS = {"SFDC_ORDERID", "SFDC_ORDERPRODUCTID", "HS_CD_H", "BL_DOC_NO", "FORMAT(INV_DT, 'yyyy-MM-dd') INV_DT", "FORMAT(PROMISE_DT, 'yyyy-MM-dd') PROMISE_DT", "FORMAT(ACTUAL_GI_DT, 'yyyy-MM-ddTHH:mm:ss') ACTUAL_GI_DT", "S_ITEM_CD", "SERIAL_NO", "GI_QTY", "HS_CD_D", "LC_NO", "FORMAT(LC_DATE, 'yyyy-MM-ddTHH:mm:ss') LC_DATE", "LC_ISSUE_BANK", "FW_VER", "FORMAT(PRODUCTION_DT, 'yyyy-MM-ddTHH:mm:ss') PRODUCTION_DT", "IV_NO", "FORMAT(IV_CRT_DT, 'yyyy-MM-ddTHH:mm:ss') IV_CRT_DT", "REMARK_HDR", "REMARK_DTL"};

    private final String SELECT_BL_LIST =
            "SELECT TOP 40 " + String.join(", ", COMMON_FIELDS) + ", " + String.join(", ", IF_FIELDS) +
                    " FROM dbo.IF_ERP_SFDC_INFO_BL" +
                    " WHERE IF_STATUS = 'R'" +
                    " ORDER BY IF_CRT_DT ASC";

    private final String UPDATE_BL_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_BL" +
                    " SET IF_STATUS = 'P', IF_PRC_DT = GETDATE(), IF_ERR_MSG = 'OK'" +
                    " WHERE IF_REC_ID IN (:ifRecIdList)";

    private final String UPDATE_BL_ERROR_LIST =
            "UPDATE dbo.IF_ERP_SFDC_INFO_BL" +
                    " SET IF_STATUS = 'E', IF_PRC_DT = GETDATE(), IF_ERR_MSG = CONCAT('[', :errorCode, '] ', :errorMessage)" +
                    " WHERE IF_REC_ID = :recordId";

    @Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_BL_LIST(int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, SELECT_BL_LIST);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_BL_LIST);
        logger.info("### Result #{} : {}", prcCnt, result);
        return result;
    }

    public boolean UPDATE_BL_LIST(List<Integer> ifRecIdList, int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, UPDATE_BL_LIST);
        logger.info("### Data #{} : {}", prcCnt, ifRecIdList);

        MapSqlParameterSource inQueryParams = new MapSqlParameterSource();
        inQueryParams.addValue("ifRecIdList", ifRecIdList);

        int result = primaryNamedJdbcTemplate.update(UPDATE_BL_LIST, inQueryParams);
        logger.info("### Result #{} : {}", prcCnt, result);

        return result == 0;
    }

    public boolean UPDATE_BL_ERROR_LIST(List<Error> errorList, int prcCnt) {
        logger.info("### Query #{} : {}", prcCnt, UPDATE_BL_ERROR_LIST);
        logger.info("### Data #{} : {}", prcCnt, errorList);

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(errorList);
        int[] result = primaryNamedJdbcTemplate.batchUpdate(UPDATE_BL_ERROR_LIST, batch);
        logger.info("### Result #{} : {}", prcCnt, result);

        return Arrays.asList(result).contains(0);
    }
}
