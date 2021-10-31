package com.mfma.gatewayserver.limiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class IpLimiter implements KeyResolver {

	public static String getIpAddr(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		List<String> ips = headers.get("X-Forwarded-For");
		String ip = "192.168.1.1";
		if (ips != null && ips.size() > 0) {
			ip = ips.get(0);
		}
		return ip;
	}

	@Override
	public Mono<String> resolve(ServerWebExchange exchange) {
		String hostName = exchange.getRequest().getRemoteAddress().getHostName();
		log.debug("获取到的hostname是："+hostName);
		return Mono.just(hostName);
	}
}
