package com.trimark.backoffice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.UserAccount;
import com.trimark.backoffice.persistence.model.UserAccountProperty;
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.service.IRoleService;
import com.trimark.backoffice.service.IUserAccountService;
import com.trimark.backoffice.web.dto.ChangePasswordDTO;
import com.trimark.backoffice.web.dto.PropertyDTO;
import com.trimark.backoffice.web.dto.UserAccountDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;
import com.trimark.backoffice.web.response.model.OrganizationModel;
import com.trimark.backoffice.web.response.model.PropertyModel;
import com.trimark.backoffice.web.response.model.RoleModel;
import com.trimark.backoffice.web.response.model.UserAccountModel;

@RestController
public class UserAccountController {
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUserAccountService userAccountService;
	
	@RequestMapping(value = "/users/findByAccountId/{accountId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getUserByAccountId(@PathVariable("accountId") int accountId) {
		UserAccount userAccount = userAccountService.getUserAccountByAccountId(accountId);
		return new ResponseEntity<SuccessBackofficeResponse<UserAccountModel>>(
				new SuccessBackofficeResponse<UserAccountModel>(getUserAccount(userAccount, userAccount.getOrganization(), userAccount.getRole())),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/listAllByOrganization/{organizationId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllUsersByOrganization(@PathVariable("organizationId") int organizationId) {
		Organization organization = organizationService.loadById(organizationId);
		List<UserAccountModel> model = new ArrayList<UserAccountModel>();
		loadUserAccount(organization, model);
		return new ResponseEntity<SuccessBackofficeResponse<List<UserAccountModel>>>(new SuccessBackofficeResponse<List<UserAccountModel>>(model), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> create(@RequestBody UserAccountDTO userAccountDTO) {
		try {
			UserAccount userAccount = new UserAccount();
			userAccount.setOrganization(organizationService.findById(userAccountDTO.getOrganization().getId()));
			userAccount.setRole(roleService.findById(userAccountDTO.getRole().getId()));
			userAccount.setUserName(userAccountDTO.getUserName());
			userAccount.setPassword(userAccountDTO.getPassword());
			
			List<UserAccountProperty> userAccountProperties = new ArrayList<UserAccountProperty>();
			for (PropertyDTO property : userAccountDTO.getAccountProperties()) {
				UserAccountProperty userAccountProperty = new UserAccountProperty();
				userAccountProperty.setUserAccount(userAccount);
				userAccountProperty.setPropertyKey(property.getName());
				userAccountProperty.setPropertyValue(property.getValue());
				userAccountProperties.add(userAccountProperty);
			}
			
			userAccountService.create(userAccount, userAccountProperties);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> update(@RequestBody UserAccountDTO userAccountDTO) {
		try {
			UserAccount userAccount = userAccountService.getUserAccountByAccountId(userAccountDTO.getAccountId());
			List<UserAccountProperty> currentUserAccountProperties = userAccountService.findAllUserAccountProperty(userAccount);
			List<UserAccountProperty> userAccountProperties = new ArrayList<UserAccountProperty>();
			
			for (PropertyDTO property : userAccountDTO.getAccountProperties()) {
				UserAccountProperty userAccountProperty = null;
				if (property.getId() == 0) {
					userAccountProperty = new UserAccountProperty();
					userAccountProperty.setUserAccount(userAccount);
					userAccountProperty.setPropertyKey(property.getName());
					userAccountProperty.setPropertyValue(property.getValue());
				}
				else {
					for (UserAccountProperty currentUserAccountProperty : currentUserAccountProperties) {
						if (currentUserAccountProperty.getId() == property.getId()) {
							userAccountProperty = currentUserAccountProperty;
							break;
						}
					}
					if (userAccountProperty == null) {
						userAccountProperty = new UserAccountProperty();
						userAccountProperty.setUserAccount(userAccount);
						userAccountProperty.setPropertyKey(property.getName());
					}
					userAccountProperty.setPropertyValue(property.getValue());
				}
				userAccountProperties.add(userAccountProperty);
			}
			
			userAccountService.updateAccountProperties(userAccountProperties);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/users/changePassword/{accountId}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> changePassword(@PathVariable("accountId") int accountId, @RequestBody ChangePasswordDTO changePasswordDTO) {
		UserAccount userAccount = userAccountService.getUserAccountByAccountId(accountId);
		userAccount.setPassword(changePasswordDTO.getNewPassword());
		userAccountService.update(userAccount);
		return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Password Changed Successfully!!!"), HttpStatus.OK);
	}
	
	private void loadUserAccount(Organization organization, List<UserAccountModel> model) {
		List<UserAccount> userAccounts = userAccountService.findAllByOrganization(organization);
		for (UserAccount userAccount : userAccounts) {
			Role role = userAccount.getRole();
			if (!role.getName().equalsIgnoreCase("superuser")) {
				model.add(getUserAccount(userAccount, organization, role));
			}
		}
		
		if (organization.getChildren() != null) {
			for (Organization child: organization.getChildren()) {
				loadUserAccount(child, model);
			}
		}
	}
	
	private UserAccountModel getUserAccount(UserAccount userAccount, Organization organization, Role role) {
		UserAccountModel userAccountModel = new UserAccountModel();
		userAccountModel.setAccountId(userAccount.getId());
		userAccountModel.setUserName(userAccount.getUserName());
		userAccountModel.setOrganization(new OrganizationModel(organization.getId(), organization.getName()));
		userAccountModel.setRole(new RoleModel(role.getId(), role.getName(), role.getDescription()));
		userAccountModel.setAccountProperties(new ArrayList<PropertyModel>());
		List<UserAccountProperty> userAccountProperties = userAccountService.findAllUserAccountProperty(userAccount);
		for (UserAccountProperty userAccountProperty : userAccountProperties) {
			userAccountModel.getAccountProperties().add(
					new PropertyModel(userAccountProperty.getId(), userAccountProperty.getPropertyKey(), userAccountProperty.getPropertyValue()));
		}
		return userAccountModel;
	}
}
