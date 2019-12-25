package com.pastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AutoConfigurationPackage
@SpringBootApplication
public class WaterFlowControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterFlowControlApplication.class, args);
	}

}
