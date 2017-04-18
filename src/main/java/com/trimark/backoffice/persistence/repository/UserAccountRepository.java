package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;

@Repository("userAccountRepository")
public class UserAccountRepository extends BaseRepository<Integer, UserAccountPersistenceModel> implements IUserAccountRepository {
	
	@Override
	public UserAccountPersistenceModel findByAccountId(int accountId) {
		UserAccountPersistenceModel result = getByKey(accountId);
		if (result != null) {
			Hibernate.initialize(result.getOrganization());
			Hibernate.initialize(result.getRole());
		}
		return result;
	}
	
	@Override
	public UserAccountPersistenceModel getUserAccountByOrganizationAndUserName(OrganizationPersistenceModel organization, String userName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("organization", organization));
		criteria.add(Restrictions.eq("userName", userName));
		UserAccountPersistenceModel result = (UserAccountPersistenceModel) criteria.uniqueResult();
		if (result != null) {
			Hibernate.initialize(result.getOrganization());
			Hibernate.initialize(result.getRole());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAccountPersistenceModel> findAllByOrganization(OrganizationPersistenceModel organization) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("organization", organization));
		List<UserAccountPersistenceModel> result = criteria.list();
		for (UserAccountPersistenceModel userAccount : result) {
			Hibernate.initialize(userAccount.getRole());
		}
		return result;
	}
	
	@Override
	public void create(UserAccountPersistenceModel userAccount) {
		persist(userAccount);
	}

	@Override
	public void update(UserAccountPersistenceModel userAccount) {
		super.update(userAccount);
	}	
}
