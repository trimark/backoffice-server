package com.trimark.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;
import com.trimark.backoffice.persistence.repository.IRoleModulePermissionRepository;

@Service("roleService")
@Transactional
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleModulePermissionRepository roleModulePermissionRepository;

	@Override
	public List<RoleModulePermission> getRoleModulePermissions(Role role) {
		return roleModulePermissionRepository.getRoleModulePermissions(role);
	}

}
