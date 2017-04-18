package com.trimark.backoffice.model;

import java.io.Serializable;

public class PropertyModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private String value;
	
	public PropertyModel() {
		this(0, null, null);
	}
	
	public PropertyModel(String name, String value) {
		this(0, name, value);
	}
	
	public PropertyModel(int id,String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
}
