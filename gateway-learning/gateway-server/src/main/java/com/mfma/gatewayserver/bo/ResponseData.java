package com.mfma.gatewayserver.bo;


import com.mfma.gatewayserver.constant.ResultEnum;

import java.io.Serializable;


public class ResponseData<T> implements Serializable {
	private static final long serialVersionUID = -225863381103524707L;
	private String code;
	
	private String msg;
	
	private T data;
	
	public ResponseData(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public ResponseData(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ResponseData(ResultEnum resultEnums) {
		this.code = resultEnums.getCode();
		this.msg = resultEnums.getMsg();
	}
	
	public ResponseData(ResultEnum resultEnums, T data) {
		this.code = resultEnums.getCode();
		this.msg = resultEnums.getMsg();
		this.data = data;
	}
	
	public ResponseData() {
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
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	
}


