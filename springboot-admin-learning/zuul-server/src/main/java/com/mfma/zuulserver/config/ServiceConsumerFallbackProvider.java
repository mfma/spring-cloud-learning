package com.mfma.zuulserver.config;

import com.alibaba.fastjson.JSON;
import com.mfma.zuulserver.bo.ResponseData;
import com.mfma.zuulserver.constant.ResultEnum;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
@Component
public class ServiceConsumerFallbackProvider implements FallbackProvider {
	
	@Override
	public String getRoute() {
		return "*";
	}
	
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new ClientHttpResponse() {
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return this.getStatusCode().value();
			}
			
			@Override
			public String getStatusText() throws IOException {
				return this.getStatusCode().getReasonPhrase();
			}
			
			@Override
			public void close() {
			
			}
			
			@Override
			public InputStream getBody() throws IOException {
				if (cause != null) {
					log.error("错误信息是：", cause.getCause().getMessage());
				}
				RequestContext ctx = RequestContext.getCurrentContext();
				ResponseData data = new ResponseData<>(ResultEnum.BUSSINESS_ERROR,"系统内部错误！！！！！！！");
				return new ByteArrayInputStream(JSON.toJSONBytes(data));
			}
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
				headers.setContentType(mt);
				return headers;
			}
		};
	}
}
