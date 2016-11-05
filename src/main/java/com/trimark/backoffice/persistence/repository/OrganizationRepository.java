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
	public Organization findById(int id) {
		Organization result = getByKey(id);
		if (result != null)
		{
			this.load(result);
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
	
	private void load(Organization organization) {
		Hibernate.initialize(organization.getParent());
		Hibernate.initialize(organization.getRole());
		Hibernate.initialize(organization.getChildren());
	}
	
	private void loadChildren(Organization parent) {
		if (parent.getChildren() != null)
		{
			for (Organization child : parent.getChildren())
			{
				this.load(child);
				this.loadChildren(child);
			}
		}		
	}
	
	
}