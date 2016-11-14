package com.trimark.backoffice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trimark.backoffice.persistence.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.UserAccount;
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
	public Organization loadById(int id) {
		return organizationRepository.loadById(id);
	}
	
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
		Role role = new Role();
		role.setName("Superuser");
		role.setDescription("Superuser");
		role.setRoleType(RoleType.User);
		role.setOwner(organization);
		roleRepository.create(role);
		UserAccount userAccount = new UserAccount();
		userAccount.setUserName("superuser");
		userAccount.setPassword("password");
		userAccount.setOrganization(organization);
		userAccount.setRole(role);
		userAccountRepository.create(userAccount);
	}
	
	@Override
	public void update(Organization organization) {
		organizationRepository.update(organization);
	}
}
