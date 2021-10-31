package com.mfma.gatewayserver.constant;


public enum ResultEnum {
	SUCCESS("001", "请求成功"),
	ERROR("002", "请求失败"),
	SYSTEM_ERROR("003", "系统异常"),
	BUSSINESS_ERROR("004", "业务逻辑错误"),
	VERIFY_CODE_ERROR("005", "业务参数错误"),
	PARAM_ERROR("006", "业务参数错误");
	
	private String code;
	private String msg;
	
	ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
		
	}
}


