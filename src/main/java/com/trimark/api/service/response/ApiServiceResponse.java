package com.trimark.api.service.response;

public class ApiServiceResponse<T> {
	private int code = 0;
	
	private T data;
	
	private String message;
	
	private String type;
		
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
}