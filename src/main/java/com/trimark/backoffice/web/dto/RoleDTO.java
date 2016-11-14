package com.trimark.backoffice.web.dto;

import java.io.Serializable;
import java.util.List;

import com.trimark.backoffice.web.response.model.OrganizationModel;

public class RoleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private String type;
	
	private String description;
	
	private OrganizationModel organization;
	
	private List<AclEntryDTO> aclEntries;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public List<AclEntryDTO> getAclEntries() {
		return aclEntries;
	}

	public void setAclEntries(List<AclEntryDTO> aclEntries) {
		this.aclEntries = aclEntries;
	}	
}
