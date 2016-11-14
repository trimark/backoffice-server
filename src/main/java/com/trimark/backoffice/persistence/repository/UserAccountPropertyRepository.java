package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.UserAccount;
import com.trimark.backoffice.persistence.model.UserAccountProperty;

@Repository("userAccountPropertyRepository")
public class UserAccountPropertyRepository extends BaseRepository<Integer, UserAccountProperty> implements IUserAccountPropertyRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAccountProperty> findAllByUserAccount(UserAccount userAccount) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userAccount", userAccount));
		return (List<UserAccountProperty>) criteria.list();
	}

	@Override
	public void create(UserAccountProperty userAccountProperty) {
		persist(userAccountProperty);
	}

	@Override
	public void update(UserAccountProperty userAccountProperty) {
		super.update(userAccountProperty);
	}
	
	
}
