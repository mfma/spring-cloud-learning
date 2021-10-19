package com.mfma.serviceconsumer.controller;


import com.mfma.serviceconsumer.service.RemoteService;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author mfma
 */
@Slf4j
@RestController
public class TestController {
	
	private final RestTemplate restTemplate;
	
	private final EurekaClient eurekaClient;
	
	private final DiscoveryClient discoveryClient;
	
	private final LoadBalancerClient loadBalancer;
	
	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	public TestController(
			@Qualifier(value = "testTemplate") RestTemplate restTemplate, EurekaClient eurekaClient, DiscoveryClient discoveryClient, LoadBalancerClient loadBalancer) {
		this.restTemplate = restTemplate;
		this.eurekaClient = eurekaClient;
		this.discoveryClient = discoveryClient;
		this.loadBalancer = loadBalancer;
	}
	
	@RequestMapping("/test1")
	public String test1() {
		log.debug("===========尝试使用RestTemplate直接访问服务");
		return restTemplate.getForObject("http://localhost:8081/user/hello", String.class);
	}
	
	@RequestMapping("/test2")
	@HystrixCommand(fallbackMethod = "defaultCallHello")
	public String test2() {
		log.debug("===========尝试使用RestTemplate通过Eureka间接访问服务");
		return restTemplate.getForObject("http://service-provider/user/hello", String.class);
	}
	
	public String defaultCallHello() {
		return "fail";
	}
	
	/**
	 * 通过Eureka Client 获取服务的元数据
	 *
	 * @return
	 */
	@RequestMapping("/test3")
	public Object getMetadata1() {
		return eurekaClient.getInstancesByVipAddress("service-provider", false);
	}
	
	/**
	 * 通过Discovery Client 获取服务的元数据
	 *
	 * @return
	 */
	@RequestMapping("/test4")
	public Object getMetadata2() {
		log.debug("getMetadata2被调用");
		return discoveryClient.getInstances("service-provider");
	}
	
	/**
	 * 测试DiscoveryClient与restTemplate配合调用服务
	 *
	 * @return
	 */
	@RequestMapping("/test5")
	public String test5() {
		log.debug("===========尝试使用RestTemplate通过Eureka间接访问服务");
		List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
		for (ServiceInstance serviceInstance : instances) {
			log.debug("******************************");
			log.error("服务实例信息：");
			log.warn("服务 ServiceId：" + serviceInstance.getServiceId());
			log.info("服务 Host：" + serviceInstance.getHost());
			log.debug("服务 Port：" + serviceInstance.getPort());
			log.debug("服务 Uri：" + serviceInstance.getUri().toString());
			log.debug("服务 Metadata：" + serviceInstance.getMetadata().toString());
			log.debug("******************************");
		}
		ServiceInstance serviceInstance = instances.get(0);
		
		URI uri = serviceInstance.getUri();
		return restTemplate.getForObject("http://" + serviceInstance.getServiceId() + "/user/hello", String.class);
	}
	
	/**
	 * 测试loadbalace
	 *
	 * @return
	 */
	@GetMapping("/test6")
	public Object chooseUrl() {
		ServiceInstance instance = loadBalancer.choose("service-provider");
		return instance;
	}
	
	/**
	 * 测试fegin
	 *
	 * @return
	 */
	@GetMapping("/test7")
	@HystrixCommand(fallbackMethod = "defaultCallHello")
	public Object test7() {
		return remoteService.hello();
	}

//	/**
//	 * 测试公共的feign服务
//	 *
//	 * @return
//	 */
//	@GetMapping("/test8")
//	public String test8() {
//		log.debug("begin to test feign common service");
//		return testCommonService.testCommon();
//	}
	
	@GetMapping("/test9")
	public String test9() {
		log.debug("begin to test feign common service");
		return remoteService.testCommon();
	}
}
