package com.trimark.api.service.request;

public class ApiServiceRequest {
	private String language;
	
	private String path;

	@ApiServiceRequestParamConfig(name = "lang")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}	
}
