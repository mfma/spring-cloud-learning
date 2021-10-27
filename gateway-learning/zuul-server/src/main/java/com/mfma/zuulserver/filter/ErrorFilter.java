package com.mfma.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author mfma
 */
@Slf4j
public class ErrorFilter extends ZuulFilter {
	
	@Override
	public String filterType() {
		return "error";
	}
	
	@Override
	public int filterOrder() {
		return 100;
	}
	
	@Override
	public boolean shouldFilter() {
		return true;
	}
	
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Throwable throwable = ctx.getThrowable();
		log.error("Filter Error : {}", throwable.getCause().getMessage());
		return null;
	}
}
