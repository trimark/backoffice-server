package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserAccount;

@Repository("userAccountRepository")
public class UserAccountRepository extends BaseRepository<Integer, UserAccount> implements IUserAccountRepository {
	
	@Override
	public UserAccount findByAccountId(int accountId) {
		UserAccount result = getByKey(accountId);
		if (result != null) {
			Hibernate.initialize(result.getOrganization());
			Hibernate.initialize(result.getRole());
		}
		return result;
	}
	
	@Override
	public UserAccount getUserAccountByOrganizationAndUserName(Organization organization, String userName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("organization", organization));
		criteria.add(Restrictions.eq("userName", userName));
		UserAccount result = (UserAccount) criteria.uniqueResult();
		if (result != null) {
			Hibernate.initialize(result.getOrganization());
			Hibernate.initialize(result.getRole());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAccount> findAllByOrganization(Organization organization) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("organization", organization));
		List<UserAccount> result = criteria.list();
		for (UserAccount userAccount : result) {
			Hibernate.initialize(userAccount.getRole());
		}
		return result;
	}
	
	@Override
	public void create(UserAccount userAccount) {
		persist(userAccount);
	}

	@Override
	public void update(UserAccount userAccount) {
		super.update(userAccount);
	}	
}
