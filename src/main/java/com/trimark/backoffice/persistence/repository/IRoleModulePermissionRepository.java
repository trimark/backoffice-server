package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;

public interface IRoleModulePermissionRepository {
	
	List<RoleModulePermission> getRoleModulePermissions(Role role);
	
	void save(RoleModulePermission roleModulePermission);
	
	void delete(RoleModulePermission roleModulePermission);
}
