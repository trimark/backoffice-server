package com.trimark.api.service.request;

public class ListLotteriesGameAdminApiServiceRequest extends GameAdminApiServiceRequest {
	
	private String status;
	
	private String streams;
	
	private String games;

	public ListLotteriesGameAdminApiServiceRequest(int gameId, long drawId, String sessionId) {
		super(gameId, drawId, sessionId, "listlotteries");
	}

	@ApiServiceRequestParamConfig(name = "filter")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStreams() {
		return streams;
	}

	public void setStreams(String streams) {
		this.streams = streams;
	}

	public String getGames() {
		return games;
	}

	public void setGames(String games) {
		this.games = games;
	}	
}
