package com.daeu.doosan.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class RepositoryUtil {
    public class ListRowMapper implements RowMapper<Map<String, Object>> {
        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            int colCnt = metaData.getColumnCount();
            Map<String, Object> map = new HashMap<String, Object>();
            String column;
            for(int indexOfcolumn = 0; indexOfcolumn < colCnt; indexOfcolumn++) {
                column = metaData.getColumnName(indexOfcolumn + 1);
                map.put(column.toUpperCase(), rs.getString(column));
            }
            return map;
        }
    }
}
