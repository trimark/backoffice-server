package com.trimark.backoffice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPropertyPersistenceModel;
import com.trimark.backoffice.persistence.repository.IUserAccountPropertyRepository;
import com.trimark.backoffice.persistence.repository.IUserAccountRepository;

@Service("userAccountService")
@Transactional
public class UserAccountService implements IUserAccountService {
	
	@Autowired
	private IUserAccountRepository userAccountRepository;
	
	@Autowired
	private IUserAccountPropertyRepository userAccountPropertyRepository;
	
	@Override
	public UserAccountPersistenceModel getUserAccountByAccountId(int accountId) {
		return userAccountRepository.findByAccountId(accountId);
	}

	@Override
	public UserAccountPersistenceModel getUserAccountByOrganizationAndUserName(OrganizationPersistenceModel organization, String userName) {
		return userAccountRepository.getUserAccountByOrganizationAndUserName(organization, userName);
	}

	@Override
	public List<UserAccountPersistenceModel> findAllByOrganization(OrganizationPersistenceModel organization) {
		return userAccountRepository.findAllByOrganization(organization);
	}

	@Override
	public List<UserAccountPropertyPersistenceModel> findAllUserAccountProperty(UserAccountPersistenceModel userAccount) {
		return userAccountPropertyRepository.findAllByUserAccount(userAccount);
	}

	@Override
	public void create(UserAccountPersistenceModel userAccount, List<UserAccountPropertyPersistenceModel> userAccountProperties) {
		userAccountRepository.create(userAccount);
		for (UserAccountPropertyPersistenceModel userAccountProperty : userAccountProperties) {
			userAccountPropertyRepository.create(userAccountProperty);
		}
	}
	
	@Override
	public void update(UserAccountPersistenceModel userAccount) {
		userAccountRepository.update(userAccount);
	}

	@Override
	public void updateAccountProperties(List<UserAccountPropertyPersistenceModel> userAccountProperties) {
		for (UserAccountPropertyPersistenceModel userAccountProperty : userAccountProperties) {
			if (userAccountProperty.getId() == 0) {
				userAccountPropertyRepository.create(userAccountProperty);
			}
			else {
				userAccountPropertyRepository.update(userAccountProperty);
			}
		}
	}	
}
