package com.mfma.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceConsumerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceConsumerApplication.class, args);
	}
	
}
