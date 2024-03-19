package com.jumkid.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.jumkid.share", "com.jumkid.domain"})
public class DomainDataApplication implements CommandLineRunner {

	@Value("${spring.application.name}")
	private String appName;

	@Value("${server.port}")
	private String appPort;

	@Value("${spring.application.version}")
	private String version;

	public static void main(String[] args) {
		SpringApplication.run(DomainDataApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("{} v{} started at port {} ", appName, version, appPort);
	}
}
