package com.trimark.backoffice.model;

import java.io.Serializable;

public class LoginSuccessModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jwtToken;
	
	private UserAccountModel userAccount;
	
	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public UserAccountModel getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountModel userAccount) {
		this.userAccount = userAccount;
	}	
}
