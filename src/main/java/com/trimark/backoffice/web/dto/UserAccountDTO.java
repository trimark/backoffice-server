package com.trimark.backoffice.web.dto;

import java.io.Serializable;
import java.util.List;

public class UserAccountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int accountId;
	
	private String userName;
	
	private String password;
	
	private OrganizationDTO organization;
	
	private RoleDTO role;
	
	private List<PropertyDTO> accountProperties;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public List<PropertyDTO> getAccountProperties() {
		return accountProperties;
	}

	public void setAccountProperties(List<PropertyDTO> accountProperties) {
		this.accountProperties = accountProperties;
	}	
}
