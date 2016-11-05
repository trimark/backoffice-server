package com.trimark.backoffice.web.response;

import java.io.Serializable;

public class ErrorBackofficeResponse extends BackofficeResponse<String> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message; 

	public ErrorBackofficeResponse(int code, String message) {
		this.setCode(code);
		this.message = message;
	}
	
	@Override
	String getData() {
		return message;
	}	
}
