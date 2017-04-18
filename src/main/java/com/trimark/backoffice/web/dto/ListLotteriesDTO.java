package com.trimark.backoffice.web.dto;

import java.io.Serializable;

public class ListLotteriesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gameId;
	
	private long drawId;
	
	private String status;
	
	private String streams;
	
	private String games;

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public long getDrawId() {
		return drawId;
	}

	public void setDrawId(long drawId) {
		this.drawId = drawId;
	}

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
