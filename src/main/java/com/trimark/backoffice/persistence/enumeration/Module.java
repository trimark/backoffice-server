package com.trimark.backoffice.persistence.enumeration;

public enum Module {
	NONE(0),
	ORGANIZATION(1);
	
	private final int value;
	
	Module(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
