package com.trimark.backoffice.web.response.model;

import java.io.Serializable;

public class OrganizationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String name;
	
	private RoleModel roleModel;
	
	public OrganizationModel() {
	}
	
	public OrganizationModel(long id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public OrganizationModel(long id, String name, RoleModel roleModel)
	{
		this(id, name);
		this.roleModel = roleModel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoleModel getRoleModel() {
		return roleModel;
	}

	public void setRoleModel(RoleModel roleModel) {
		this.roleModel = roleModel;
	}	
}
