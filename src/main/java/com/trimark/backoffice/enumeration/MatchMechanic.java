package com.trimark.backoffice.enumeration;

public enum MatchMechanic {
	QtyMatch(1),
	SpecificMatch(2);
	
	private final int value;
	
	MatchMechanic(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
