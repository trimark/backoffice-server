package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;

public interface IUserAccountRepository {
	
	UserAccountPersistenceModel findByAccountId(int accountId);
	
	UserAccountPersistenceModel getUserAccountByOrganizationAndUserName(OrganizationPersistenceModel organization, String userName);
	
	List<UserAccountPersistenceModel> findAllByOrganization(OrganizationPersistenceModel organization);
	
	void create(UserAccountPersistenceModel userAccount);
	
	void update(UserAccountPersistenceModel userAccount);
}
