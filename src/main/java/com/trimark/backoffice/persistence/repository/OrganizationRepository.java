package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;

@Repository("organizationRepository")
public class OrganizationRepository extends BaseRepository<Integer, OrganizationPersistenceModel> implements IOrganizationRepository {

	@Override
	public OrganizationPersistenceModel loadById(int id) {
		OrganizationPersistenceModel organization = this.findById(id);
		this.loadChildren(organization);
		return organization;
	}

	@Override
	public OrganizationPersistenceModel findById(int id) {
		OrganizationPersistenceModel result = getByKey(id);
		if (result != null)
		{
			Hibernate.initialize(result.getRole());
		}
		return result;
	}

	@Override
	public OrganizationPersistenceModel findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (OrganizationPersistenceModel) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationPersistenceModel> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.ne("id", 1));
		return (List<OrganizationPersistenceModel>) criteria.list();
	}

	@Override
	public List<OrganizationPersistenceModel> findAllChildOrganization(int id) {
		OrganizationPersistenceModel organization = this.findById(id);
		this.loadChildren(organization);
		return organization.getChildren();
	}
	
	@Override
	public void create(OrganizationPersistenceModel organization) {
		persist(organization);
	}
	
	@Override
	public void update(OrganizationPersistenceModel organization) {
		super.update(organization);
	}
	
	private void loadChildren(OrganizationPersistenceModel parent) {
		if (parent != null) {
			Hibernate.initialize(parent.getChildren());
			if (parent.getChildren() != null) {
				for (OrganizationPersistenceModel child : parent.getChildren()) {
					Hibernate.initialize(child.getRole());
					this.loadChildren(child);
				}
			}
		}
	}	
}
