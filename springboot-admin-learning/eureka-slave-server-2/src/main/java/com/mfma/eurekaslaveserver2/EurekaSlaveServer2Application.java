package com.mfma.eurekaslaveserver2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaSlaveServer2Application {
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaSlaveServer2Application.class, args);
	}
	
}
