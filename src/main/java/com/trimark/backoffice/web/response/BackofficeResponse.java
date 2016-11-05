package com.trimark.backoffice.web.response;

public abstract class BackofficeResponse<T> {
	
	private int code;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	abstract T getResponse();
}
