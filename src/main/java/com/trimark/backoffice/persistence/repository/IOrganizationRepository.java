package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;

public interface IOrganizationRepository {
	
	OrganizationPersistenceModel loadById(int id);
	
	OrganizationPersistenceModel findById(int id);
	
	OrganizationPersistenceModel findByName(String name);
	
	List<OrganizationPersistenceModel> findAll();
	
	List<OrganizationPersistenceModel> findAllChildOrganization(int id);
	
	void create(OrganizationPersistenceModel organization);
	
	void update(OrganizationPersistenceModel organization);
}
