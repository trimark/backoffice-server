package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserAccount;

public interface IUserAccountRepository {
	
	UserAccount findByAccountId(int accountId);
	
	UserAccount getUserAccountByOrganizationAndUserName(Organization organization, String userName);
	
	List<UserAccount> findAllByOrganization(Organization organization);
	
	void create(UserAccount userAccount);
}
