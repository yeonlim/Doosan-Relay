package com.daeu.suprema.common;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
	
	@Bean
	@Qualifier("primaryDataSource")
	@Primary
	@ConfigurationProperties(prefix="database.datasource")
	DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Qualifier("primaryJdbcTemplate")
	JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource")DataSource primaryDataSource) {
		return new JdbcTemplate(primaryDataSource);
	}
	
	@Bean
	@Qualifier("primaryNamedJdbcTemplate")
	NamedParameterJdbcTemplate primaryNamedJdbcTemplate(@Qualifier("primaryDataSource")DataSource primaryDataSource) {
		return new NamedParameterJdbcTemplate(primaryDataSource);
	}
}
