package com.mfma.serviceconsumer1.feign;

import com.mfma.serviceconsumer1.service.RemoteService;
import org.springframework.stereotype.Component;

@Component
public class RemoteServiceFallback implements RemoteService {
	@Override
	public String hello() {
		return "hello-UserRemoteClientFallback";
	}
	
	@Override
	public String testCommon() {
		return "testCommon-UserRemoteClientFallback";
	}
}
