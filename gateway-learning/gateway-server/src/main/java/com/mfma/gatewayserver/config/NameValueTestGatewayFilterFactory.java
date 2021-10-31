package com.mfma.gatewayserver.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author mfma
 */
@Slf4j
@Component
public class NameValueTestGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
	@Override
	public GatewayFilter apply(NameValueConfig config) {
		return (exchange, chain) -> {
			log.debug("进入了NameValueTestGatewayFilterFactory: " + config.getName() + "\t" + StringEscapeUtils.unescapeJava(config.getValue()));
			ServerHttpRequest request = exchange.getRequest().mutate().build();
			return chain.filter(exchange.mutate().request(request).build());
		};
	}
}
