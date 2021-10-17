package com.mfma.serviceconsumer.service;

import com.mfma.serviceconsumer.config.FeginConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

//@Service
//@FeignClient(value="service-provider",configuration = FeginConfiguration.class)
public interface RemoteService {
	
//	@GetMapping("/user/hello")
	String hello();
}
