package com.trimark.api.service.request;

public class AuthAdminLoginApiServiceRequest extends ApiServiceRequest {
	private String uid;
	
	private String password;
	
	private String domain;
	
	private String organization;
	
	public AuthAdminLoginApiServiceRequest() {
		this.setPath("http://api.trimarkgaming.com/auth.admin.web/services/auth/login.json");
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@ApiServiceRequestParamConfig(name = "pwd")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@ApiServiceRequestParamConfig(name = "org")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}	
}
