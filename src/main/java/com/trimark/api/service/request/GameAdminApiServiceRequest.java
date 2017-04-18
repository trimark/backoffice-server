package com.trimark.api.service.request;

public class GameAdminApiServiceRequest extends ApiServiceRequest {
	
	private String sessionId;
	
	private String function;
	
	private int gameId;
	
	private long drawId;
	
	private String moduleName;
	
	public GameAdminApiServiceRequest(int gameId, long drawId, String sessionId, String function) {
		this.setPath("http://api.trimarkgaming.com/game.admin.web/services/game/admin.json");
		this.gameId = gameId;
		this.drawId = drawId;
		this.sessionId = sessionId;
		this.function = function;
	}
	
	@ApiServiceRequestParamConfig(name = "sessid")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@ApiServiceRequestParamConfig(name = "fn")
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	@ApiServiceRequestParamConfig(name = "gameid")
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	@ApiServiceRequestParamConfig(name = "drawid")
	public long getDrawId() {
		return drawId;
	}

	public void setDrawId(long drawId) {
		this.drawId = drawId;
	}

	@ApiServiceRequestParamConfig(name = "impl")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}	
}
