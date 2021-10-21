package com.mfma.zuulserver.controller;

import com.mfma.zuulserver.bo.ResponseData;
import com.mfma.zuulserver.constant.ResultEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ErrorHandlerController implements ErrorController {
	
	@Autowired
	private ErrorAttributes errorAttributes;
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public ResponseData<String> error(HttpServletRequest request) {
		Map<String, Object> errorAttributes = getErrorAttributes(request);
		String message = (String) errorAttributes.get("message");
		String trace = (String) errorAttributes.get("trace");
		if (StringUtils.isNotBlank(trace)) {
			message += String.format("and trace %s", trace);
		}
		return new ResponseData<>(ResultEnum.BUSSINESS_ERROR,"系统内部错误");
	}
	
	private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		return errorAttributes.getErrorAttributes(new ServletWebRequest(request), true);
	}
}
