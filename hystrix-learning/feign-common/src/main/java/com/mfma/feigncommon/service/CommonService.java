package com.mfma.feigncommon.service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mfma
 */
public interface CommonService {
	
	@GetMapping("/user/testCommon")
	String testCommon();
}