package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.Organization;

public interface IOrganizationRepository {
	
	Organization findById(int id);
	
	Organization findByName(String name);
	
	List<Organization> findAll();
	
	List<Organization> findAllChildOrganization(int id);
}
