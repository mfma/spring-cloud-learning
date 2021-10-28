package com.mfma.serviceconsumer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class ServiceConsumer1Application {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceConsumer1Application.class, args);
	}
	
}
