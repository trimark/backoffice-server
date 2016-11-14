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
	public List<Role> findRolesByOwner(Organization owner) {
		return roleRepository.findAllByOwner(owner);
	}

	@Override
	public List<Role> findRolesByOwnerAndType(Organization owner, RoleType roleType) {
		return roleRepository.findAllByOwnerAndType(owner, roleType);
	}

	@Override
	public void create(Role role) {
		roleRepository.create(role);
	}
	
	@Override
	public void update(Role role) {
		roleRepository.update(role);
	}

	@Override
	public List<RoleModulePermission> findRoleModulePermissions(Role role) {
		return roleModulePermissionRepository.getRoleModulePermissions(role);
	}

	@Override
	public void saveRoleModulePermissions(List<RoleModulePermission> roleModulePermissions) {
		for (RoleModulePermission roleModulePermission : roleModulePermissions) {
			roleModulePermissionRepository.save(roleModulePermission);
		}
	}

	@Override
	public void deleteRoleModulePermissions(List<RoleModulePermission> roleModulePermissions) {
		for (RoleModulePermission roleModulePermission : roleModulePermissions) {
			roleModulePermissionRepository.delete(roleModulePermission);
		}
	}
	
}
