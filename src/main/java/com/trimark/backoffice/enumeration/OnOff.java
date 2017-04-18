package com.trimark.backoffice.enumeration;

public enum OnOff {
	
	On(1),
	Off(2);
	
	private final int value;
	
	OnOff(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
