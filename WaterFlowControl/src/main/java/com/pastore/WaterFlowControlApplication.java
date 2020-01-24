package com.pastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@AutoConfigurationPackage
@SpringBootApplication
public class WaterFlowControlApplication extends SpringBootServletInitializer
{
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return builder.sources(WaterFlowControlApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WaterFlowControlApplication.class, args);
	}

}
