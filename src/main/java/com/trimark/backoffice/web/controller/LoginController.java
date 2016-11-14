package com.trimark.backoffice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trimark.backoffice.persistence.enumeration.Permission;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;
import com.trimark.backoffice.persistence.model.UserAccount;
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.service.IRoleService;
import com.trimark.backoffice.service.IUserAccountService;
import com.trimark.backoffice.web.dto.LoginDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;
import com.trimark.backoffice.web.response.model.LoginSuccessModel;
import com.trimark.backoffice.web.response.model.ModulePermissionsModel;
import com.trimark.backoffice.web.response.model.OrganizationModel;
import com.trimark.backoffice.web.response.model.RoleModel;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("backofficeUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IUserAccountService userAccountService;
	
	@Autowired
	private IRoleService roleService;
	
	private DaoAuthenticationProvider authenticationProvider;
	
	@Autowired
	public void setUp() {
		authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
	};
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> login(@RequestBody LoginDTO loginDTO) {
		try {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					loginDTO.getOrganization().getName() + "/" + loginDTO.getUserName(), loginDTO.getPassword());
			if (authenticationProvider.authenticate(authRequest).isAuthenticated())
			{
				LoginSuccessModel model = new LoginSuccessModel();
				model.setJwtToken("organization=" + loginDTO.getOrganization().getName() + 
						"|userName=" + loginDTO.getUserName() + "|password=" + loginDTO.getPassword());
				Organization organization = organizationService.loadById(loginDTO.getOrganization().getId());
				UserAccount userAccount = userAccountService.getUserAccountByOrganizationAndUserName(organization, loginDTO.getUserName()); 
				Role role = userAccount.getRole();
				model.setRole(new RoleModel(role.getId(), role.getName(), role.getDescription()));
				if (role.getName().equalsIgnoreCase("Superuser")) {
					role = role.getOwner().getRole();
				}
				List<RoleModulePermission> roleModulePermissions = roleService.findRoleModulePermissions(role);
				List<ModulePermissionsModel> modulePermissions = new ArrayList<ModulePermissionsModel>();
				for (RoleModulePermission roleModulePermission : roleModulePermissions) {
					List<Permission> permissions = new ArrayList<Permission>();
					for (int i = 0; i < 32; i++) {
						int mask = ((1 << i) & roleModulePermission.getPermissions()); 
						if (mask > 0) {
							permissions.add(Permission.valueOf(mask));
						}
					}
					modulePermissions.add(new ModulePermissionsModel(roleModulePermission.getModule(), permissions));
				}
				model.getRole().setModulePermissions(modulePermissions);
				model.setOrganization(new OrganizationModel(organization.getId(), organization.getName(), 
						new RoleModel(organization.getRole().getId(), organization.getRole().getName(), organization.getRole().getDescription())));
				loadChildren(organization, model.getOrganization());
				return new ResponseEntity<SuccessBackofficeResponse<LoginSuccessModel>>(new SuccessBackofficeResponse<LoginSuccessModel>(model), HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(1, "Login Failed"), HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	private void loadChildren(Organization parent, OrganizationModel parentModel) {
		if (parent.getChildren() != null) {
			parentModel.setChildren(new ArrayList<OrganizationModel>());
			for (Organization organization : parent.getChildren()) {
				Role role = organization.getRole();
				OrganizationModel model = new OrganizationModel(organization.getId(), organization.getName(), new RoleModel(role.getId(), role.getName(), role.getDescription()));
				parentModel.getChildren().add(model);
				loadChildren(organization, model);
			}
		}
	}
}
