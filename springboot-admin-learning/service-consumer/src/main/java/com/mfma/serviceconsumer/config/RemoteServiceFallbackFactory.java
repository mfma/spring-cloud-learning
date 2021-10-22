package com.mfma.serviceconsumer.config;

import com.mfma.serviceconsumer.service.RemoteService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mfma
 */
@Slf4j
@Component
public class RemoteServiceFallbackFactory implements FallbackFactory<RemoteService> {
	@Override
	public RemoteService create(Throwable throwable) {
		log.error("UserRemoteClient回退：", throwable);
		return new RemoteService() {
			@Override
			public String hello() {
				return "hello-UserRemoteClientFallback-factory";
			}
			
			@Override
			public String testCommon() {
				return "testCommon-UserRemoteClientFallback-factory";
			}
		};
	}
}
