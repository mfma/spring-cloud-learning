package com.mfma.gatewayserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @author mfma
 */
@Slf4j
@Configuration
public class FilterConfig {
	
	@Bean
	@Order(-1)
	public GlobalFilter a() {
		return (exchange, chain) -> {
			log.debug("first pre filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.debug("third post filter");
			}));
		};
	}
	@Bean
	@Order(0)
	public GlobalFilter b() {
		return (exchange, chain) -> {
			log.debug("second pre filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.debug("second post filter");
			}));
		};
	}
	
	@Bean
	@Order(1)
	public GlobalFilter c() {
		return (exchange, chain) -> {
			log.debug("third pre filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.debug("first post filter");
			}));
		};
	}
	
	@Bean
	@Order(4)
	public GlobalFilter d() {
		log.debug("four pre filter");
		return new TestGlobaFilter();
	}
}
