package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPropertyPersistenceModel;

@Repository("userAccountPropertyRepository")
public class UserAccountPropertyRepository extends BaseRepository<Integer, UserAccountPropertyPersistenceModel> implements IUserAccountPropertyRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAccountPropertyPersistenceModel> findAllByUserAccount(UserAccountPersistenceModel userAccount) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userAccount", userAccount));
		return (List<UserAccountPropertyPersistenceModel>) criteria.list();
	}

	@Override
	public void create(UserAccountPropertyPersistenceModel userAccountProperty) {
		persist(userAccountProperty);
	}

	@Override
	public void update(UserAccountPropertyPersistenceModel userAccountProperty) {
		super.update(userAccountProperty);
	}
	
	
}
