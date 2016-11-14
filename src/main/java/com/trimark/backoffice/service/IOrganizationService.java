package com.trimark.backoffice.service;

import java.util.List;

import com.trimark.backoffice.persistence.model.Organization;

public interface IOrganizationService {
	Organization loadById(int id);
	
	Organization findById(int id);
	
	Organization findByName(String name);
	
	List<Organization> findAll();
	
	List<Organization> findAllChildOrganization(int id);
	
	void create(Organization organization);
	
	void update(Organization organization);
}
