package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;

public interface IRoleRepository {
	
	RolePersistenceModel findById(int id);
	
	List<RolePersistenceModel> findAllByOwner(OrganizationPersistenceModel organization);
	
	List<RolePersistenceModel> findAllByOwnerAndType(OrganizationPersistenceModel organization, RoleType roleType);
	
	void create(RolePersistenceModel role);
	
	void update(RolePersistenceModel role);
}
