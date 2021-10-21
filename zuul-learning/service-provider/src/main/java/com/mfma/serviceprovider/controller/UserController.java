package com.mfma.serviceprovider.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author mfma
 */
@Slf4j
@RestController
public class UserController{
	
	@GetMapping("/user/hello")
	public String hello() {
		log.debug("serviceprovider1:有人请求我的接口------hello");
		return "service provider-0----method hello";
	}
	
	@GetMapping("/user/testCommon")
	public String testCommon() {
		log.debug("serviceprovider1:有人请求我的接口------testCommon");
		return "service provider-0----method testCommon";
	}
}
