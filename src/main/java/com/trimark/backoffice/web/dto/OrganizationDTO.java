package com.trimark.backoffice.web.dto;

import java.io.Serializable;

import com.trimark.backoffice.model.OrganizationModel;
import com.trimark.backoffice.model.RoleModel;

public class OrganizationDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	
	private OrganizationModel parent;
	
	private RoleModel role;

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

	public OrganizationModel getParent() {
		return parent;
	}

	public void setParent(OrganizationModel parent) {
		this.parent = parent;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}	
}
