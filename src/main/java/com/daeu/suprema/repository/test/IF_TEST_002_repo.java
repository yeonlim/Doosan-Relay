package com.daeu.suprema.repository.test;

import com.daeu.suprema.io.test.IF_TEST_002.IF_TEST_002_Req;
import com.daeu.suprema.util.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IF_TEST_002_repo extends RepositoryUtil {
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("primaryNamedJdbcTemplate")
    private NamedParameterJdbcTemplate primaryNamedJdbcTemplate;
	
	private final String SELECT_USER_LIST = "SELECT id, name, age FROM dbo.sample_tbl WHERE id = :id";
	
	@Transactional(readOnly = true)
    public List<Map<String, Object>> SELECT_USER_LIST(IF_TEST_002_Req objInput) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", objInput.getId());

		return primaryNamedJdbcTemplate.queryForList(SELECT_USER_LIST, params);
    }
}
