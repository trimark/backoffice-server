package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.RoleModulePermissionPersistenceModel;

public interface IRoleModulePermissionRepository {
	
	List<RoleModulePermissionPersistenceModel> getRoleModulePermissions(RolePersistenceModel role);
	
	void save(RoleModulePermissionPersistenceModel roleModulePermission);
	
	void delete(RoleModulePermissionPersistenceModel roleModulePermission);
}
