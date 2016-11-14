package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;

public interface IRoleRepository {
	
	Role findById(int id);
	
	List<Role> findAllByOwner(Organization organization);
	
	List<Role> findAllByOwnerAndType(Organization organization, RoleType roleType);
	
	void create(Role role);
	
	void update(Role role);
}
