package com.trimark.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trimark.backoffice.persistence.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;
import com.trimark.backoffice.persistence.repository.IRoleModulePermissionRepository;
import com.trimark.backoffice.persistence.repository.IRoleRepository;

@Service("roleService")
@Transactional
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private IRoleModulePermissionRepository roleModulePermissionRepository;

	@Override
	public Role findById(int id) {
		return roleRepository.findById(id);
	}

	@Override
	public List<Role> findRolesByOwnerAndType(Organization owner, RoleType roleType) {
		return roleRepository.findAllByOwnerAndType(owner, roleType);
	}

	@Override
	public void saveRole(Role role) {
		roleRepository.save(role);
	}
	
	@Override
	public List<RoleModulePermission> findRoleModulePermissions(Role role) {
		return roleModulePermissionRepository.getRoleModulePermissions(role);
	}
}
