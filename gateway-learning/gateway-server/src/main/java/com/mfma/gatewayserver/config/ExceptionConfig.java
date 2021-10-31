package com.mfma.gatewayserver.config;

import com.mfma.gatewayserver.exception.Json1ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * @author mfma
 */
@Slf4j
@Configuration
public class ExceptionConfig {
	
	private final ServerProperties serverProperties;
	private final ApplicationContext applicationContext;
	private final ResourceProperties resourceProperties;
	private final List<ViewResolver> viewResolvers;
	private final ServerCodecConfigurer serverCodecConfigurer;
	
	public ExceptionConfig(
			ServerProperties serverProperties, ResourceProperties resourceProperties, ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {
		this.serverProperties = serverProperties;
		this.applicationContext = applicationContext;
		this.resourceProperties = resourceProperties;
		this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
		this.serverCodecConfigurer = serverCodecConfigurer;
	}
	
//	@Primary
//	@Bean
//	@Order(Ordered.HIGHEST_PRECEDENCE)
//	public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
//		JsonExceptionHandler exceptionHandler = new JsonExceptionHandler(errorAttributes, this.resourceProperties, this.serverProperties.getError(), this.applicationContext);
//		exceptionHandler.setViewResolvers(this.viewResolvers);
//		exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
//		exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
//		return exceptionHandler;
//	}
	
	/**
	 * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
	 */
	@Primary
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
															 ServerCodecConfigurer serverCodecConfigurer) {
		Json1ExceptionHandler jsonExceptionHandler = new Json1ExceptionHandler();
		jsonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
		jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
		jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
		return jsonExceptionHandler;
	}

}