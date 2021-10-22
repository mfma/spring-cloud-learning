package com.mfma.zuulserver.config;

import com.mfma.zuulserver.filter.TestFilter;

/**
 * @author mfma
 */
//@Configuration
public class FilterConfig {
	
//	@Bean
	public TestFilter testFilter(){
		return new TestFilter();
	}
}
