package com.mfma.gatewayserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * @author mfma
 */
@Slf4j
@Component
public class PredicateTestRoutePredicateFactory extends AbstractRoutePredicateFactory<PredicateTestRoutePredicateFactory.Config> {

	public PredicateTestRoutePredicateFactory() {
		super(Config.class);
	}

	@Override
	public Predicate<ServerWebExchange> apply(Config config) {
		return exchange -> {
			log.debug("进入了PredicateTestRoutePredicateFactory\\t" + config.getName());
			if (config.getName().equals("mfma")) {
				return true;
			}
			return false;
		};
	}

	public static class Config {
		private String name;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
