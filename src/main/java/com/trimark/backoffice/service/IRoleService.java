package com.trimark.backoffice.service;

import java.util.List;

import com.trimark.backoffice.persistence.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;

public interface IRoleService {
	
	Role findById(int id);
	
	List<Role> findRolesByOwnerAndType(Organization owner, RoleType roleType);
	
	void saveRole(Role role);
	
	List<RoleModulePermission> findRoleModulePermissions(Role role);
}
