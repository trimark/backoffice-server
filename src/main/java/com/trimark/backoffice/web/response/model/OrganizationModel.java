package com.trimark.backoffice.web.response.model;

import java.io.Serializable;
import java.util.List;

public class OrganizationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private OrganizationModel parent;
	
	private RoleModel role;
	
	private List<OrganizationModel> children;
	
	public OrganizationModel() {
	}
	
	public OrganizationModel(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public OrganizationModel(int id, String name, RoleModel role)
	{
		this(id, name);
		this.role = role;
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
	
	public OrganizationModel getParent() {
		return parent;
	}

	public void setParent(OrganizationModel parent) {
		this.parent = parent;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRol(RoleModel role) {
		this.role = role;
	}

	public List<OrganizationModel> getChildren() {
		return children;
	}

	public void setChildren(List<OrganizationModel> children) {
		this.children = children;
	}	
}
