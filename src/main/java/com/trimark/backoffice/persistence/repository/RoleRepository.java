package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;

@Repository("roleRepository")
public class RoleRepository extends BaseRepository<Integer, RolePersistenceModel> implements IRoleRepository {
	
	@Override
	public RolePersistenceModel findById(int id) {
		RolePersistenceModel result = getByKey(id);
		if (result != null)
		{
			Hibernate.initialize(result.getOwner());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RolePersistenceModel> findAllByOwner(OrganizationPersistenceModel organization) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("owner", organization));
		return (List<RolePersistenceModel>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RolePersistenceModel> findAllByOwnerAndType(OrganizationPersistenceModel organization, RoleType roleType) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.and(Restrictions.eq("owner", organization), Restrictions.eq("roleType", roleType)));
		return (List<RolePersistenceModel>) criteria.list();
	}

	@Override
	public void create(RolePersistenceModel role) {
		persist(role);
	}

	@Override
	public void update(RolePersistenceModel entity) {
		super.update(entity);
	}	
}
