package com.daeu.doosan.common;

import com.daeu.doosan.DoosanRelayApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * war 확장자로 배포할 경우 사용
 */
public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DoosanRelayApplication.class);
	}
}
