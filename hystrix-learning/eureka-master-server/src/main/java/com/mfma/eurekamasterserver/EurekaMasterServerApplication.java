package com.mfma.eurekamasterserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaMasterServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaMasterServerApplication.class, args);
	}
	
}
