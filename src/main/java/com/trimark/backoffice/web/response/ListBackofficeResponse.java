package com.trimark.backoffice.web.response;

import java.util.ArrayList;
import java.util.List;

public class ListBackofficeResponse<T> extends BackofficeResponse<List<T>> {
	
	private List<T> model = new ArrayList<T>();
	
	public ListBackofficeResponse() {
		setCode(0);
	}

	@Override
	public List<T> getData() {
		return model;
	}
	
	public void add(T entity) {
		model.add(entity);
	}
}
