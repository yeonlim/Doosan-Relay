package com.daeu.suprema.repository.test;

import com.daeu.suprema.io.test.IF_TEST_003.IF_TEST_003_Req;
import com.daeu.suprema.util.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IF_TEST_003_repo extends RepositoryUtil {
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;

	private final String INSERT_USER_LIST = "INSERT INTO dbo.sample_tbl(id, name, age) VALUES(:id, :name, :age)";

	@Transactional(readOnly = true)
    public int[] INSERT_USER_LIST(IF_TEST_003_Req objInput) {

		List<Map<String, Object>> objList = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Map<String, Object> params = new HashMap<>();
			params.put("id", "test" + i);
			params.put("name", "test" + i);
			params.put("age", i);

			objList.add(params);
		}
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(objList);
		return primaryNamedJdbcTemplate.batchUpdate(INSERT_USER_LIST, batch);
    }
}
