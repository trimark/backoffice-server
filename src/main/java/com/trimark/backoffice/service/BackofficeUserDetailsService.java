package com.trimark.backoffice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.UserCredential;

@Service("backofficeUserDetailsService")
public class BackofficeUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IUserCredentialService userCredentialService;

	@Override
	public UserDetails loadUserByUsername(String usertoken) throws UsernameNotFoundException {
		StringTokenizer strToken = new StringTokenizer(usertoken, "/");
		String organizationName = strToken.nextToken();
		String userName = strToken.nextToken();
		Organization organization = organizationService.findByName(organizationName);
		UserCredential userCredential = userCredentialService.getUserCredentialByOrganizationAndUserName(organization, userName);
		if(userCredential == null){
			throw new UsernameNotFoundException("Username not found");
		}
		return new User(usertoken, userCredential.getPassword(), true, true, true, true, getGrantedAuthorities());
	}
	
	private List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZATIONS_CREATE"));
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZATIONS_READ"));
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ROLES_CREATE"));
		return grantedAuthorities;
	}

}
