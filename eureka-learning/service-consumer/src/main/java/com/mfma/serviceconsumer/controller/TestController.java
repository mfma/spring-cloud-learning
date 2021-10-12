package com.mfma.serviceconsumer.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mfma
 */
@Slf4j
@RestController
public class TestController {

	
	private final RestTemplate restTemplate;
	
	private final EurekaClient eurekaClient;
	
	private final DiscoveryClient discoveryClient;
	
	@Autowired
	public TestController(RestTemplate restTemplate,EurekaClient eurekaClient,DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.eurekaClient = eurekaClient;
		this.discoveryClient =discoveryClient;
	}
	
	@RequestMapping("/test1")
	public String test1(){
		log.debug("===========尝试使用RestTemplate直接访问服务");
		return restTemplate.getForObject("http://localhost:8081/user/hello",String.class);
	}
	
	@RequestMapping("/test2")
	public String test2(){
		log.debug("===========尝试使用RestTemplate通过Eureka间接访问服务");
		return restTemplate.getForObject("http://service-provider/user/hello",String.class);
	}
	
	/**
	 * 通过Eureka Client 获取服务的元数据
	 * @return
	 */
	@RequestMapping("/test3")
	public Object getMetadata1(){
		return eurekaClient.getInstancesByVipAddress("service-provider",false);
	}
	
	/**
	 * 通过Discovery Client 获取服务的元数据
	 * @return
	 */
	@RequestMapping("/test4")
	public Object getMetadata2(){
		log.debug("getMetadata2被调用");
		return discoveryClient.getInstances("service-provider");
	}
}
