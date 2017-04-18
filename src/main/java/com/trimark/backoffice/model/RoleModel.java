package com.trimark.backoffice.model;

import java.io.Serializable;
import java.util.List;

import com.trimark.backoffice.enumeration.RoleType;

public class RoleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private String description;
	
	private RoleType type;
	
	private List<ModulePermissionsModel> modulePermissions;
	
	public RoleModel() {
		
	}
	
	public RoleModel(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

	public List<ModulePermissionsModel> getModulePermissions() {
		return modulePermissions;
	}

	public void setModulePermissions(List<ModulePermissionsModel> modulePermissions) {
		this.modulePermissions = modulePermissions;
	}	
}
