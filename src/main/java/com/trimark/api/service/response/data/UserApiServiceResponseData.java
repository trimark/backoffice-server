package com.trimark.api.service.response.data;

import com.trimark.api.service.response.ApiServiceResponseParamConfig;

public class UserApiServiceResponseData {
	private long loginId;
	
	private String session;
	
	private String organization;

	public long getLoginId() {
		return loginId;
	}

	@ApiServiceResponseParamConfig(name = "id")
	public void setLoginId(long loginId) {
		this.loginId = loginId;
	}

	public String getSession() {
		return session;
	}

	@ApiServiceResponseParamConfig(name = "sessid")
	public void setSession(String session) {
		this.session = session;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}	
}
