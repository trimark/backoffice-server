package com.trimark.backoffice.persistence.repository;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserCredential;

public interface IUserCredentialRepository {
	
	UserCredential getUserCredentialByOrganizationAndUserName(Organization organization, String userName);
}
