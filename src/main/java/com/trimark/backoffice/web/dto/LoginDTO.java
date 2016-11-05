package com.trimark.backoffice.web.dto;

import java.io.Serializable;

import com.trimark.backoffice.web.response.model.OrganizationModel;

public class LoginDTO implements Serializable {
	
	private String userName;
	
	private String password;
	
	private OrganizationModel organization;

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

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}
}
