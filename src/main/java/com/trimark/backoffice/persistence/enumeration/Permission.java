package com.trimark.backoffice.persistence.enumeration;

public enum Permission {
	CREATE(1),
	READ(2),
	UPATE(4),
	DELETE(8);
	
	private int value;
	
	Permission(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
