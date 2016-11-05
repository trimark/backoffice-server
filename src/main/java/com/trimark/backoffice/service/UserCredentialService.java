package com.trimark.backoffice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserCredential;
import com.trimark.backoffice.persistence.repository.IUserCredentialRepository;

@Service("userCredentialService")
@Transactional
public class UserCredentialService implements IUserCredentialService {
	
	@Autowired
	private IUserCredentialRepository userCredentialRepository;

	@Override
	public UserCredential getUserCredentialByOrganizationAndUserName(Organization organization, String userName) {
		return userCredentialRepository.getUserCredentialByOrganizationAndUserName(organization, userName);
	}

}
