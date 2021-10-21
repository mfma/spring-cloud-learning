package com.mfma.serviceconsumer.feign;

import com.mfma.serviceconsumer.service.RemoteService;
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
