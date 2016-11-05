package com.trimark.backoffice.persistence.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserCredential;

@Repository("userCredentialRepository")
public class UserCredentialRepository extends BaseRepository<Long, UserCredential> implements IUserCredentialRepository {
	
	@Override
	public UserCredential getUserCredentialByOrganizationAndUserName(Organization organization, String userName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("organization", organization));
		criteria.add(Restrictions.eq("userName", userName));
		return (UserCredential) criteria.uniqueResult();
	}

}
