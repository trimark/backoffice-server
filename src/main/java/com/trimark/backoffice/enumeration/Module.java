package com.trimark.backoffice.enumeration;

public enum Module {
	NONE(0),
	GAMERESULTS(1),
	ROYALTIES(2),
	GAMEHISTORY(3),
	GAMEDEFINITIONS(4),
	MYGAMES(5),
	MYACCOUNTS(6),
	ORGANIZATIONS(7),
	ROLES(8),
	USERS(9),
	GAMES(10);
	
	private final int value;
	
	Module(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
