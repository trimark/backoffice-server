package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;

@Repository("roleModulePermissionRepository")
public class RoleModulePermissionRepository extends BaseRepository<Long, RoleModulePermission> implements IRoleModulePermissionRepository {

	@SuppressWarnings("unchecked")
	public List<RoleModulePermission> getRoleModulePermissions(Role role) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("role", role));
		return (List<RoleModulePermission>) criteria.list();
	}

}
