package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPropertyPersistenceModel;

public interface IUserAccountPropertyRepository {
	
	List<UserAccountPropertyPersistenceModel> findAllByUserAccount(UserAccountPersistenceModel userAccount);
	
	void create(UserAccountPropertyPersistenceModel userAccountProperty);
	
	void update(UserAccountPropertyPersistenceModel userAccountPropery);
}
