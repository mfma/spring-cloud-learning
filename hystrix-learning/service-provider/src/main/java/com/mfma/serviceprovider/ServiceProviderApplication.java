package com.mfma.serviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author mfma
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderApplication.class, args);
	}
	
}
