package com.trimark.backoffice.persistence.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Permission {
	NONE(0),
	READ(1),
	UPDATE(2),
	CREATE(4),
	DELETE(8),
	FULL(16),
	ACTIVATEINACTIVATE(32),
	SETPERMISSIONS(64),
	EDITROLES(128),
	EDITPROFILE(256),
	CHANGEPASSWORD(512);
	
	private static Map<Integer, Permission> map = new HashMap<Integer, Permission>();
	private int value;
	
	Permission(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	static {
        for (Permission permission : Permission.values()) {
            map.put(permission.value, permission);
        }
    }

    public static Permission valueOf(int value) {
        return map.get(value);
    }
}
