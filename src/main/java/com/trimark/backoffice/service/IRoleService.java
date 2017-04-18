package com.trimark.backoffice.service;

import java.util.List;

import com.trimark.backoffice.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.RoleModulePermissionPersistenceModel;

public interface IRoleService {
	
	RolePersistenceModel findById(int id);
	
	List<RolePersistenceModel> findRolesByOwner(OrganizationPersistenceModel owner);
	
	List<RolePersistenceModel> findRolesByOwnerAndType(OrganizationPersistenceModel owner, RoleType roleType);
	
	void create(RolePersistenceModel role);
	
	void update(RolePersistenceModel role);
	
	List<RoleModulePermissionPersistenceModel> findRoleModulePermissions(RolePersistenceModel role);
	
	void saveRoleModulePermissions(List<RoleModulePermissionPersistenceModel> roleModulePermissions);
	
	void deleteRoleModulePermissions(List<RoleModulePermissionPersistenceModel> roleModulePermissions);
}
