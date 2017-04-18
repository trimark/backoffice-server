package com.trimark.backoffice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trimark.backoffice.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;
import com.trimark.backoffice.persistence.repository.IOrganizationRepository;
import com.trimark.backoffice.persistence.repository.IRoleRepository;
import com.trimark.backoffice.persistence.repository.IUserAccountRepository;

@Service("organizationService")
@Transactional
public class OrganizationService implements IOrganizationService {
	
	@Autowired
	private IOrganizationRepository organizationRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private IUserAccountRepository userAccountRepository;

	@Override
	public OrganizationPersistenceModel loadById(int id) {
		return organizationRepository.loadById(id);
	}
	
	@Override
	public OrganizationPersistenceModel findById(int id) {
		return organizationRepository.findById(id);
	}

	@Override
	public OrganizationPersistenceModel findByName(String name) {
		return organizationRepository.findByName(name);
	}

	@Override
	public List<OrganizationPersistenceModel> findAll() {
		return organizationRepository.findAll();
	}

	@Override
	public List<OrganizationPersistenceModel> findAllChildOrganization(int id) {
		return organizationRepository.findAllChildOrganization(id);
	}

	@Override
	public void create(OrganizationPersistenceModel organization) {
		organizationRepository.create(organization);
		RolePersistenceModel role = new RolePersistenceModel();
		role.setName("Superuser");
		role.setDescription("Superuser");
		role.setRoleType(RoleType.User);
		role.setOwner(organization);
		roleRepository.create(role);
		UserAccountPersistenceModel userAccount = new UserAccountPersistenceModel();
		userAccount.setUserName("superuser");
		userAccount.setPassword("password");
		userAccount.setOrganization(organization);
		userAccount.setRole(role);
		userAccountRepository.create(userAccount);
	}
	
	@Override
	public void update(OrganizationPersistenceModel organization) {
		organizationRepository.update(organization);
	}
}
