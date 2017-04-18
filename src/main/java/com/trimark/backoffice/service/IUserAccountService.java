package com.trimark.backoffice.service;

import java.util.List;

import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPropertyPersistenceModel;

public interface IUserAccountService {
	
	UserAccountPersistenceModel getUserAccountByAccountId(int accountId);
	
	UserAccountPersistenceModel getUserAccountByOrganizationAndUserName(OrganizationPersistenceModel organization, String userName);
	
	List<UserAccountPersistenceModel> findAllByOrganization(OrganizationPersistenceModel organization);
	
	List<UserAccountPropertyPersistenceModel> findAllUserAccountProperty(UserAccountPersistenceModel userAccount);
	
	void create(UserAccountPersistenceModel userAccount, List<UserAccountPropertyPersistenceModel> userAccountProperties);
	
	void update(UserAccountPersistenceModel userAccount);
	
	void updateAccountProperties(List<UserAccountPropertyPersistenceModel> userAccountProperties);
}
