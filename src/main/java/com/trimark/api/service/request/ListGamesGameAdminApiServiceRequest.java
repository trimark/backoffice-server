package com.trimark.api.service.request;

public class ListGamesGameAdminApiServiceRequest extends ListGamesGameApiServiceRequest {
	
	private String sessionId;
	
	public ListGamesGameAdminApiServiceRequest() {
		this.setPath("http://api.trimarkgaming.com/game.admin.web/services/game/games.json");
	}
	
	@ApiServiceRequestParamConfig(name = "sessid")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
