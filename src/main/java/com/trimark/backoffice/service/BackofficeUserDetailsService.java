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

import com.trimark.backoffice.enumeration.Permission;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.RoleModulePermissionPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;

@Service("backofficeUserDetailsService")
public class BackofficeUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUserAccountService userAccountService;

	@Override
	public UserDetails loadUserByUsername(String usertoken) throws UsernameNotFoundException {
		StringTokenizer strToken = new StringTokenizer(usertoken, "/");
		String organizationName = strToken.nextToken();
		String userName = strToken.nextToken();
		OrganizationPersistenceModel organization = organizationService.findByName(organizationName);
		UserAccountPersistenceModel userAccount = userAccountService.getUserAccountByOrganizationAndUserName(organization, userName);
		if(userAccount == null){
			throw new UsernameNotFoundException("Username not found");
		}
		RolePersistenceModel role = userAccount.getRole();
		if (role.getName().equals("Superuser")) {
			role = organization.getRole();
		}
		return new User(usertoken, userAccount.getPassword(), true, true, true, true, getGrantedAuthorities(roleService.findRoleModulePermissions(role)));
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(List<RoleModulePermissionPersistenceModel> roleModulePermissions) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (RoleModulePermissionPersistenceModel roleModulePermission : roleModulePermissions) {
			for (int i = 0; i < 32; i++) {
				int mask = ((1 << i) & roleModulePermission.getPermissions()); 
				if (mask > 0) {
					Permission permission = Permission.valueOf(mask);
					//System.out.println("getGrantedAuthorities >>> " + "ROLE_" + roleModulePermission.getModule() + "_" + permission);
					grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleModulePermission.getModule() + "_" + permission));
				}
			}
		}
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_GAMES_READ"));
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_LOTTERIES_READ"));
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_LOTTERIES_CREATE"));
		return grantedAuthorities;
	}

}
