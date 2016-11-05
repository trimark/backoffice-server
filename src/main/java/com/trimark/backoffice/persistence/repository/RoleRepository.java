package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;

@Repository("roleRepository")
public class RoleRepository extends BaseRepository<Integer, Role> implements IRoleRepository {
	
	@Override
	public Role findById(int id) {
		Role result = getByKey(id);
		if (result != null)
		{
			Hibernate.initialize(result.getOwner());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAllByOwnerAndType(Organization organization, RoleType roleType) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.and(Restrictions.eq("owner", organization), Restrictions.eq("roleType", roleType)));
		return (List<Role>) criteria.list();
	}

	@Override
	public void save(Role role) {
		persist(role);
	}
}
