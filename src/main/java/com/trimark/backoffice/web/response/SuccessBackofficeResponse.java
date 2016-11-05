package com.trimark.backoffice.web.response;

import java.io.Serializable;

public class SuccessBackofficeResponse<T> extends BackofficeResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T data;
	
	public SuccessBackofficeResponse(T data) {
		setCode(0);
		this.data = data;
	}

	@Override
	public T getData() {
		return data;
	}
}
