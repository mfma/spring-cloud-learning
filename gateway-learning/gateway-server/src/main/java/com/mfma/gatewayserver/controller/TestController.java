package com.mfma.gatewayserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mfma
 */
@Slf4j
@RestController
public class TestController {
	
	@GetMapping("/fallback")
	public String fallback() {
		log.debug("当前在测试Hystrix的回退功能");
		return "fallback";
	}
}
