package com.pastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;


@AutoConfigurationPackage
@SpringBootApplication
public class WaterFlowControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterFlowControlApplication.class, args);
	}

}
