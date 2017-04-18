package com.trimark.backoffice.enumeration;

public enum PlayMode {
	
	RealMode(1, "Real", "Real"), 
    FunOnlyMode(2, "Fun", "Fun");
    
    private int value;
    
    private String name;
    
    private String displayName;
    
    PlayMode(int value, String name, String displayName) {
    	this.value = value;
    	this.name = name;
    	this.displayName = displayName;
    }

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
