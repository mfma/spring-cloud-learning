package com.mfma.serviceprovider1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableEurekaClient
public class ServiceProvider1Application {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceProvider1Application.class, args);
	}
	
}
