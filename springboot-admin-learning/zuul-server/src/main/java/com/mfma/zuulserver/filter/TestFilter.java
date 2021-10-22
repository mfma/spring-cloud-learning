package com.mfma.zuulserver.filter;

import com.alibaba.fastjson.JSON;
import com.mfma.zuulserver.bo.ResponseData;
import com.mfma.zuulserver.constant.ResultEnum;
import com.mfma.zuulserver.util.IpUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class TestFilter extends ZuulFilter {
	// IP黑名单列表
	private List<String> blackIpList = Arrays.asList("/service-consumer/forbid");
	
	
	public TestFilter() {
		super();
	}
	@Override
	public boolean shouldFilter() {
		return true;
	}
	@Override
	public String filterType() {
		return "pre";
	}
	@Override
	public int filterOrder() {
		return 1;
	}
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String requestInfo = ctx.getRequest().getServletPath();
		// 在黑名单中禁用
		if (StringUtils.isNotBlank(requestInfo) && blackIpList.contains(requestInfo)) {
			ctx.setSendZuulResponse(false);
			ResponseData<String> data =  new ResponseData<>(ResultEnum.ERROR,"非法请求1");
			ctx.setResponseBody(JSON.toJSONString(data));
			ctx.getResponse().setContentType("application/json; charset=utf-8");
			return null;
		}
		return null;
	}
}