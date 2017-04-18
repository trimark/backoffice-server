package com.trimark.backoffice.enumeration;

public enum ScratchMechanic {
	
	ScratchAll(1),
	NumOfSpots(2);
	
	private final int value;
	
	ScratchMechanic(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
