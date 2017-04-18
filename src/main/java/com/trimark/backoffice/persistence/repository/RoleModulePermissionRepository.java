package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.RoleModulePermissionPersistenceModel;

@Repository("roleModulePermissionRepository")
public class RoleModulePermissionRepository extends BaseRepository<Long, RoleModulePermissionPersistenceModel> implements IRoleModulePermissionRepository {

	@SuppressWarnings("unchecked")
	public List<RoleModulePermissionPersistenceModel> getRoleModulePermissions(RolePersistenceModel role) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("role", role));
		return (List<RoleModulePermissionPersistenceModel>) criteria.list();
	}

	@Override
	public void save(RoleModulePermissionPersistenceModel roleModulePermission) {
		persist(roleModulePermission);
	}

	@Override
	public void delete(RoleModulePermissionPersistenceModel entity) {
		super.delete(entity);
	}	
}
