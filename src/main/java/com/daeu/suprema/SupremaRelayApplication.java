package com.daeu.suprema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ImportResource("classpath:batch-config.xml")
public class SupremaRelayApplication {
	public static void main(String[] args) {
		SpringApplication.run(SupremaRelayApplication.class, args);
	}
}
