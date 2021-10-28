package com.mfma.serviceconsumer1.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author mfma
 */
@Configuration
public class BeanConfiguration {
	
	@Bean(name = "testTemplate")
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}
