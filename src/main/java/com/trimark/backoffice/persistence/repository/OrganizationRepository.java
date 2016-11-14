package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.Organization;

@Repository("organizationRepository")
public class OrganizationRepository extends BaseRepository<Integer, Organization> implements IOrganizationRepository {

	@Override
	public Organization loadById(int id) {
		Organization organization = this.findById(id);
		this.loadChildren(organization);
		return organization;
	}

	@Override
	public Organization findById(int id) {
		Organization result = getByKey(id);
		if (result != null)
		{
			Hibernate.initialize(result.getRole());
		}
		return result;
	}

	@Override
	public Organization findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Organization) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.ne("id", 1));
		return (List<Organization>) criteria.list();
	}

	@Override
	public List<Organization> findAllChildOrganization(int id) {
		Organization organization = this.findById(id);
		this.loadChildren(organization);
		return organization.getChildren();
	}
	
	@Override
	public void create(Organization organization) {
		persist(organization);
	}
	
	@Override
	public void update(Organization organization) {
		super.update(organization);
	}
	
	private void loadChildren(Organization parent) {
		if (parent != null) {
			Hibernate.initialize(parent.getChildren());
			if (parent.getChildren() != null) {
				for (Organization child : parent.getChildren()) {
					Hibernate.initialize(child.getRole());
					this.loadChildren(child);
				}
			}
		}
	}	
}
