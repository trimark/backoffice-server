package com.trimark.backoffice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.repository.IOrganizationRepository;

@Service("organizationService")
@Transactional
public class OrganizationService implements IOrganizationService {
	
	@Autowired
	private IOrganizationRepository organizationRepository;

	@Override
	public Organization findById(int id) {
		return organizationRepository.findById(id);
	}

	@Override
	public Organization findByName(String name) {
		return organizationRepository.findByName(name);
	}

	@Override
	public List<Organization> findAll() {
		return organizationRepository.findAll();
	}

	@Override
	public List<Organization> findAllChildOrganization(int id) {
		return organizationRepository.findAllChildOrganization(id);
	}

	@Override
	public void create(Organization organization) {
		organizationRepository.create(organization);
	}
	
	@Override
	public void update(Organization organization) {
		organizationRepository.update(organization);
	}
}
