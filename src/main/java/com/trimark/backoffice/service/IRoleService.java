package com.trimark.backoffice.service;

import java.util.List;

import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;

public interface IRoleService {
	
	List<RoleModulePermission> getRoleModulePermissions(Role role);
}
