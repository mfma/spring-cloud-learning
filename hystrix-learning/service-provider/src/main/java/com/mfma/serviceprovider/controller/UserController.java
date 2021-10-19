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
		log.debug("=========有人请求我==========");
		return "hello,this is service provider-0";
	}
	
	@GetMapping("/user/testCommon")
	public String testCommon() {
		return "hello, this is test feign common service 0";
	}
}
