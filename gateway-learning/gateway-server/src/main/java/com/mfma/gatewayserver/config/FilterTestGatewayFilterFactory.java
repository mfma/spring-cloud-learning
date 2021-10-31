package com.mfma.gatewayserver.config;

import com.alibaba.fastjson.JSONObject;
import com.mfma.gatewayserver.bo.ResponseData;
import com.mfma.gatewayserver.constant.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author mfma
 */
@Slf4j
@ConfigurationProperties("spring.cloud.gateway.filter.request-rate-limiter")
@Component("CustomRequestRateLimiter")
public class FilterTestGatewayFilterFactory extends AbstractGatewayFilterFactory<FilterTestGatewayFilterFactory.Config> {
	
	/**
	 * Key-Resolver key.
	 */
	public static final String KEY_RESOLVER_KEY = "keyResolver";
	
	private static final String EMPTY_KEY = "____EMPTY_KEY__";
	
	private final RateLimiter defaultRateLimiter;
	
	private final KeyResolver defaultKeyResolver;
	
	/**
	 * Switch to deny requests if the Key Resolver returns an empty key, defaults to true.
	 */
	private boolean denyEmptyKey = true;
	
	/**
	 * HttpStatus to return when denyEmptyKey is true, defaults to FORBIDDEN.
	 */
	private String emptyKeyStatusCode = HttpStatus.FORBIDDEN.name();
	
	public FilterTestGatewayFilterFactory(RateLimiter defaultRateLimiter, KeyResolver defaultKeyResolver) {
		super(Config.class);
		this.defaultRateLimiter = defaultRateLimiter;
		this.defaultKeyResolver = defaultKeyResolver;
	}
	
	public KeyResolver getDefaultKeyResolver() {
		return defaultKeyResolver;
	}
	
	public RateLimiter getDefaultRateLimiter() {
		return defaultRateLimiter;
	}
	
	public boolean isDenyEmptyKey() {
		return denyEmptyKey;
	}
	
	public void setDenyEmptyKey(boolean denyEmptyKey) {
		this.denyEmptyKey = denyEmptyKey;
	}
	
	public String getEmptyKeyStatusCode() {
		return emptyKeyStatusCode;
	}
	
	public void setEmptyKeyStatusCode(String emptyKeyStatusCode) {
		this.emptyKeyStatusCode = emptyKeyStatusCode;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GatewayFilter apply(Config config) {
		KeyResolver resolver = (config.keyResolver == null) ? defaultKeyResolver : config.keyResolver;
		RateLimiter<Object> limiter = (config.rateLimiter == null) ? defaultRateLimiter : config.rateLimiter;
		return (exchange, chain) -> {
			Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
			return resolver.resolve(exchange).flatMap(key -> limiter.isAllowed(route.getId(), key).flatMap(response -> {
				for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
					exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
				}
				if (response.isAllowed()) {
					return chain.filter(exchange);
				}
				
				ServerHttpResponse httpResponse = exchange.getResponse();
				httpResponse.setStatusCode(HttpStatus.OK);
				log.debug("访问已限流，请稍候再请求");
				ResponseData respObj = new ResponseData(ResultEnum.ERROR, "访问已限流，请稍候再请求");
				DataBuffer buffer = httpResponse.bufferFactory().wrap(JSONObject.toJSONBytes(respObj));
				httpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
				return httpResponse.writeWith(Mono.just(buffer));
			}));
		};
	}
	
	private <T> T getOrDefault(T configValue, T defaultValue) {
		return (configValue != null) ? configValue : defaultValue;
	}
	
	public static class Config {
		
		private KeyResolver keyResolver;
		
		private RateLimiter rateLimiter;
		
		private HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;
		
		private Boolean denyEmptyKey;
		
		private String emptyKeyStatus;
		
		public KeyResolver getKeyResolver() {
			return keyResolver;
		}
		
		public Config setKeyResolver(KeyResolver keyResolver) {
			this.keyResolver = keyResolver;
			return this;
		}
		
		public RateLimiter getRateLimiter() {
			return rateLimiter;
		}
		
		public Config setRateLimiter(RateLimiter rateLimiter) {
			this.rateLimiter = rateLimiter;
			return this;
		}
		
		public HttpStatus getStatusCode() {
			return statusCode;
		}
		
		public Config setStatusCode(HttpStatus statusCode) {
			this.statusCode = statusCode;
			return this;
		}
		
		public Boolean getDenyEmptyKey() {
			return denyEmptyKey;
		}
		
		public Config setDenyEmptyKey(Boolean denyEmptyKey) {
			this.denyEmptyKey = denyEmptyKey;
			return this;
		}
		
		public String getEmptyKeyStatus() {
			return emptyKeyStatus;
		}
		
		public Config setEmptyKeyStatus(String emptyKeyStatus) {
			this.emptyKeyStatus = emptyKeyStatus;
			return this;
		}
		
	}
}

