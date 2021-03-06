package com.trimark.backoffice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trimark.api.service.ILoginApiService;
import com.trimark.api.service.request.AuthAdminLoginApiServiceRequest;
import com.trimark.api.service.request.GameAdminLoginApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.data.UserApiServiceResponseData;
import com.trimark.backoffice.enumeration.Permission;
import com.trimark.backoffice.model.LoginSuccessModel;
import com.trimark.backoffice.model.ModulePermissionsModel;
import com.trimark.backoffice.model.OrganizationModel;
import com.trimark.backoffice.model.PropertyModel;
import com.trimark.backoffice.model.RoleModel;
import com.trimark.backoffice.model.UserAccountModel;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.RoleModulePermissionPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPropertyPersistenceModel;
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.service.IRoleService;
import com.trimark.backoffice.service.IUserAccountService;
import com.trimark.backoffice.web.dto.ChangePasswordDTO;
import com.trimark.backoffice.web.dto.LoginDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;

@Controller
public class MyAccountController {
	
	@Autowired
	@Qualifier("backofficeUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IUserAccountService userAccountService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private ILoginApiService loginApiService;
	
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
				OrganizationPersistenceModel organization = organizationService.loadById(loginDTO.getOrganization().getId());
				UserAccountPersistenceModel userAccount = userAccountService.getUserAccountByOrganizationAndUserName(organization, loginDTO.getUserName());
				
				UserAccountModel userAccountModel = new UserAccountModel();
				userAccountModel.setAccountId(userAccount.getId());
				userAccountModel.setUserName(userAccount.getUserName());
				userAccountModel.setOrganization(new OrganizationModel(organization.getId(), organization.getName()));
				
				RolePersistenceModel role = userAccount.getRole();
				userAccountModel.setRole(new RoleModel(role.getId(), role.getName(), role.getDescription()));
				if (role.getName().equalsIgnoreCase("Superuser")) {
					role = role.getOwner().getRole();
				}
				List<RoleModulePermissionPersistenceModel> roleModulePermissions = roleService.findRoleModulePermissions(role);
				List<ModulePermissionsModel> modulePermissions = new ArrayList<ModulePermissionsModel>();
				for (RoleModulePermissionPersistenceModel roleModulePermission : roleModulePermissions) {
					List<Permission> permissions = new ArrayList<Permission>();
					for (int i = 0; i < 32; i++) {
						int mask = ((1 << i) & roleModulePermission.getPermissions()); 
						if (mask > 0) {
							permissions.add(Permission.valueOf(mask));
						}
					}
					modulePermissions.add(new ModulePermissionsModel(roleModulePermission.getModule(), permissions));
				}
				userAccountModel.getRole().setModulePermissions(modulePermissions);
				
				userAccountModel.setAccountProperties(new ArrayList<PropertyModel>());
				List<UserAccountPropertyPersistenceModel> userAccountProperties = userAccountService.findAllUserAccountProperty(userAccount);
				for (UserAccountPropertyPersistenceModel userAccountProperty : userAccountProperties) {
					userAccountModel.getAccountProperties().add(
							new PropertyModel(userAccountProperty.getId(), userAccountProperty.getPropertyKey(), userAccountProperty.getPropertyValue()));
				}
				
				userAccountModel.setOrganization(new OrganizationModel(organization.getId(), organization.getName(), 
						new RoleModel(organization.getRole().getId(), organization.getRole().getName(), organization.getRole().getDescription())));
				loadChildOrganizations(organization, userAccountModel.getOrganization());
				model.setUserAccount(userAccountModel);
				
				AuthAdminLoginApiServiceRequest authLoginApiServiceRequest = new AuthAdminLoginApiServiceRequest();
				authLoginApiServiceRequest.setUid("admin1");
				authLoginApiServiceRequest.setPassword("seven7");
				authLoginApiServiceRequest.setOrganization("Trimark");
				ApiServiceResponse<?> authLoginApiServiceResponse = loginApiService.authAdminLogin(authLoginApiServiceRequest);
				
				if (authLoginApiServiceResponse.getCode() == 0) {
					GameAdminLoginApiServiceRequest gameLoginApiServiceRequest = new GameAdminLoginApiServiceRequest();
					UserApiServiceResponseData authUserApiServiceResponseData = (UserApiServiceResponseData) authLoginApiServiceResponse.getData();
					gameLoginApiServiceRequest.setKey(authUserApiServiceResponseData.getSession());
					gameLoginApiServiceRequest.setOrganization("Trimark");
					ApiServiceResponse<?> gameLoginApiServiceResponse = loginApiService.gameAdminLogin(gameLoginApiServiceRequest);
					if (gameLoginApiServiceResponse.getCode() == 0) {
						UserApiServiceResponseData gameUserApiServiceResponseData = (UserApiServiceResponseData) gameLoginApiServiceResponse.getData();
						model.setJwtToken("organization=" + loginDTO.getOrganization().getName() + "|userName=" + loginDTO.getUserName() + 
							"|password=" + loginDTO.getPassword() + "|gameSession=" + gameUserApiServiceResponseData.getSession());
						System.out.println("ApiServiceResponse 2 >>> " + gameLoginApiServiceResponse);
						return new ResponseEntity<SuccessBackofficeResponse<LoginSuccessModel>>(new SuccessBackofficeResponse<LoginSuccessModel>(model), HttpStatus.OK);
					}
					else {
						return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(1, gameLoginApiServiceResponse.getData().toString()), HttpStatus.BAD_REQUEST);
					}
				}
				else {
					return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(1, authLoginApiServiceResponse.getData().toString()), HttpStatus.BAD_REQUEST);
				}				
			}
			else {
				return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(1, "Wrong username/password!!!"), HttpStatus.BAD_REQUEST);
			}
		}
		catch (BadCredentialsException e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Wrong username/password!!!"), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() != null)
		{
			User user = (User) authentication.getPrincipal();
			String[] userToken = user.getUsername().split("/");
			OrganizationPersistenceModel organization = organizationService.findByName(userToken[0]);
			UserAccountPersistenceModel userAccount = userAccountService.getUserAccountByOrganizationAndUserName(organization, userToken[1]);
			userAccount.setPassword(changePasswordDTO.getNewPassword());
			userAccountService.update(userAccount);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Password Changed Successfully!!!"), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(2000, "Principal does not exist"), HttpStatus.BAD_REQUEST);
		}
	}
	
	private void loadChildOrganizations(OrganizationPersistenceModel parent, OrganizationModel parentModel) {
		if (parent.getChildren() != null) {
			parentModel.setChildren(new ArrayList<OrganizationModel>());
			for (OrganizationPersistenceModel organization : parent.getChildren()) {
				RolePersistenceModel role = organization.getRole();
				OrganizationModel model = new OrganizationModel(organization.getId(), organization.getName(), new RoleModel(role.getId(), role.getName(), role.getDescription()));
				parentModel.getChildren().add(model);
				loadChildOrganizations(organization, model);
			}
		}
	}
}
