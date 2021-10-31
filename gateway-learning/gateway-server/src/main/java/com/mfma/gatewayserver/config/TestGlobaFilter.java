package com.mfma.gatewayserver.config;

import com.alibaba.fastjson.JSON;
import com.mfma.gatewayserver.bo.ResponseData;
import com.mfma.gatewayserver.constant.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author mfma
 */
@Slf4j
public class TestGlobaFilter implements GlobalFilter, Ordered {
	
	private List<String> blackIpList = Arrays.asList("/forbid");
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.debug("four post filter");
		String requestInfo = exchange.getRequest().getURI().getPath();
		// 在黑名单中禁用
		if (StringUtils.isNotBlank(requestInfo) && blackIpList.contains(requestInfo)) {
			ServerHttpResponse response = exchange.getResponse();
			ResponseData<String> data = new ResponseData<>(ResultEnum.ERROR,"非法请求！");
			byte[] datas = JSON.toJSONBytes(data);
			DataBuffer buffer = response.bufferFactory().wrap(datas);
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
			return response.writeWith(Mono.just(buffer));
		}
		return chain.filter(exchange);
	}
	
	@Override
	public int getOrder() {
		return 4;
	}
}
