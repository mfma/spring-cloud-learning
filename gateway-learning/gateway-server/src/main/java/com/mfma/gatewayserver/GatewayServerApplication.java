package com.mfma.gatewayserver;

import com.mfma.gatewayserver.limiter.IpLimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

/**
 * @author mfma
 */
@SpringBootApplication
public class GatewayServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}
	
	@Bean(name = "ipLimiter")
	public KeyResolver ipLimiter(){
		return new IpLimiter();
	}
	
//	@Bean
//	public KeyResolver ipKeyResolver() {
//		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
//	}
}
