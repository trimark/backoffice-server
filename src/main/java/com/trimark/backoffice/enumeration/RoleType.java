package com.trimark.backoffice.enumeration;

public enum RoleType {
	None(0),
	Organization(1),
	User(2);
	
	private final int value;
	
	RoleType(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
