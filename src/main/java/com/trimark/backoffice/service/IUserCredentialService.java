package com.trimark.backoffice.service;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserCredential;

public interface IUserCredentialService {
	
	UserCredential getUserCredentialByOrganizationAndUserName(Organization organization, String userName); 
}
