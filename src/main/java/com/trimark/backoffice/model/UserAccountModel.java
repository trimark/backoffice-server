package com.trimark.backoffice.model;

import java.io.Serializable;
import java.util.List;

public class UserAccountModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long accountId;
	
	private String userName;
	
	private OrganizationModel organization;
	
	private RoleModel role;
	
	private List<PropertyModel> accountProperties;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public List<PropertyModel> getAccountProperties() {
		return accountProperties;
	}

	public void setAccountProperties(List<PropertyModel> accountProperties) {
		this.accountProperties = accountProperties;
	}	
}
