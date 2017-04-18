package com.trimark.backoffice.enumeration;

public enum WinMechanic {
	SingleSymMatch(1),
	VariedSymMatch(2),
	AmountMatch(3);
	
	private final int value;
	
	WinMechanic(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
