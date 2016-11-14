package com.trimark.backoffice.web.response.model;

import java.io.Serializable;
import java.util.List;

import com.trimark.backoffice.persistence.enumeration.Module;
import com.trimark.backoffice.persistence.enumeration.Permission;

public class ModulePermissionsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Module module;
	
	private List<Permission> permissions;
	
	public ModulePermissionsModel(Module module, List<Permission> permissions) {
		this.module = module;
		this.permissions = permissions;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
