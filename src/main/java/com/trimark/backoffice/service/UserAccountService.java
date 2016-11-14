package com.trimark.backoffice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserAccount;
import com.trimark.backoffice.persistence.model.UserAccountProperty;
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
	public UserAccount getUserAccountByAccountId(int accountId) {
		return userAccountRepository.findByAccountId(accountId);
	}

	@Override
	public UserAccount getUserAccountByOrganizationAndUserName(Organization organization, String userName) {
		return userAccountRepository.getUserAccountByOrganizationAndUserName(organization, userName);
	}

	@Override
	public List<UserAccount> findAllByOrganization(Organization organization) {
		return userAccountRepository.findAllByOrganization(organization);
	}

	@Override
	public List<UserAccountProperty> findAllUserAccountProperty(UserAccount userAccount) {
		return userAccountPropertyRepository.findAllByUserAccount(userAccount);
	}

	@Override
	public void create(UserAccount userAccount, List<UserAccountProperty> userAccountProperties) {
		userAccountRepository.create(userAccount);
		for (UserAccountProperty userAccountProperty : userAccountProperties) {
			userAccountPropertyRepository.create(userAccountProperty);
		}
	}

	@Override
	public void updateAccountProperties(List<UserAccountProperty> userAccountProperties) {
		for (UserAccountProperty userAccountProperty : userAccountProperties) {
			if (userAccountProperty.getId() == 0) {
				userAccountPropertyRepository.create(userAccountProperty);
			}
			else {
				userAccountPropertyRepository.update(userAccountProperty);
			}
		}
	}	
}
