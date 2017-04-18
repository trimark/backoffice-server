package com.trimark.api.service.request;

public class GameAdminLoginApiServiceRequest extends ApiServiceRequest {
	private String organization;
	
	private String key;
	
	public GameAdminLoginApiServiceRequest() {
		this.setPath("http://api.trimarkgaming.com/game.admin.web/services/game/login.json");
	}
	
	@ApiServiceRequestParamConfig(name = "org")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
