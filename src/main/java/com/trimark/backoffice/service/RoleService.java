package com.trimark.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trimark.backoffice.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.RoleModulePermissionPersistenceModel;
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
	public RolePersistenceModel findById(int id) {
		return roleRepository.findById(id);
	}
	
	@Override
	public List<RolePersistenceModel> findRolesByOwner(OrganizationPersistenceModel owner) {
		return roleRepository.findAllByOwner(owner);
	}

	@Override
	public List<RolePersistenceModel> findRolesByOwnerAndType(OrganizationPersistenceModel owner, RoleType roleType) {
		return roleRepository.findAllByOwnerAndType(owner, roleType);
	}

	@Override
	public void create(RolePersistenceModel role) {
		roleRepository.create(role);
	}
	
	@Override
	public void update(RolePersistenceModel role) {
		roleRepository.update(role);
	}

	@Override
	public List<RoleModulePermissionPersistenceModel> findRoleModulePermissions(RolePersistenceModel role) {
		return roleModulePermissionRepository.getRoleModulePermissions(role);
	}

	@Override
	public void saveRoleModulePermissions(List<RoleModulePermissionPersistenceModel> roleModulePermissions) {
		for (RoleModulePermissionPersistenceModel roleModulePermission : roleModulePermissions) {
			roleModulePermissionRepository.save(roleModulePermission);
		}
	}

	@Override
	public void deleteRoleModulePermissions(List<RoleModulePermissionPersistenceModel> roleModulePermissions) {
		for (RoleModulePermissionPersistenceModel roleModulePermission : roleModulePermissions) {
			roleModulePermissionRepository.delete(roleModulePermission);
		}
	}
	
}
