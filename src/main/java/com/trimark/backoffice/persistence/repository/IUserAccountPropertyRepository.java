package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.UserAccount;
import com.trimark.backoffice.persistence.model.UserAccountProperty;

public interface IUserAccountPropertyRepository {
	
	List<UserAccountProperty> findAllByUserAccount(UserAccount userAccount);
	
	void create(UserAccountProperty userAccountProperty);
	
	void update(UserAccountProperty userAccountPropery);
}
