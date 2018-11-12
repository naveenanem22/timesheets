package com.tmsht.application;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.tmsht.*" })
public class Application {
	@PostConstruct
	public void init() {
		// Set application TimeZone to UTC
		//TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
