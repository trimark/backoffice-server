package com.trimark.backoffice.service;

import java.util.List;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserAccount;
import com.trimark.backoffice.persistence.model.UserAccountProperty;

public interface IUserAccountService {
	
	UserAccount getUserAccountByAccountId(int accountId);
	
	UserAccount getUserAccountByOrganizationAndUserName(Organization organization, String userName);
	
	List<UserAccount> findAllByOrganization(Organization organization);
	
	List<UserAccountProperty> findAllUserAccountProperty(UserAccount userAccount);
	
	void create(UserAccount userAccount, List<UserAccountProperty> userAccountProperties);
	
	void updateAccountProperties(List<UserAccountProperty> userAccountProperties);
}
