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

import com.trimark.backoffice.model.OrganizationModel;
import com.trimark.backoffice.model.PropertyModel;
import com.trimark.backoffice.model.RoleModel;
import com.trimark.backoffice.model.UserAccountModel;
import com.trimark.backoffice.persistence.model.OrganizationPersistenceModel;
import com.trimark.backoffice.persistence.model.RolePersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPersistenceModel;
import com.trimark.backoffice.persistence.model.UserAccountPropertyPersistenceModel;
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.service.IRoleService;
import com.trimark.backoffice.service.IUserAccountService;
import com.trimark.backoffice.web.dto.ChangePasswordDTO;
import com.trimark.backoffice.web.dto.PropertyDTO;
import com.trimark.backoffice.web.dto.UserAccountDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;

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
		UserAccountPersistenceModel userAccount = userAccountService.getUserAccountByAccountId(accountId);
		return new ResponseEntity<SuccessBackofficeResponse<UserAccountModel>>(
				new SuccessBackofficeResponse<UserAccountModel>(getUserAccount(userAccount, userAccount.getOrganization(), userAccount.getRole())),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/listAllByOrganization/{organizationId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllUsersByOrganization(@PathVariable("organizationId") int organizationId) {
		OrganizationPersistenceModel organization = organizationService.loadById(organizationId);
		List<UserAccountModel> model = new ArrayList<UserAccountModel>();
		loadUserAccount(organization, model);
		return new ResponseEntity<SuccessBackofficeResponse<List<UserAccountModel>>>(new SuccessBackofficeResponse<List<UserAccountModel>>(model), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> create(@RequestBody UserAccountDTO userAccountDTO) {
		try {
			UserAccountPersistenceModel userAccount = new UserAccountPersistenceModel();
			userAccount.setOrganization(organizationService.findById(userAccountDTO.getOrganization().getId()));
			userAccount.setRole(roleService.findById(userAccountDTO.getRole().getId()));
			userAccount.setUserName(userAccountDTO.getUserName());
			userAccount.setPassword(userAccountDTO.getPassword());
			
			List<UserAccountPropertyPersistenceModel> userAccountProperties = new ArrayList<UserAccountPropertyPersistenceModel>();
			for (PropertyDTO property : userAccountDTO.getAccountProperties()) {
				UserAccountPropertyPersistenceModel userAccountProperty = new UserAccountPropertyPersistenceModel();
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
			UserAccountPersistenceModel userAccount = userAccountService.getUserAccountByAccountId(userAccountDTO.getAccountId());
			List<UserAccountPropertyPersistenceModel> currentUserAccountProperties = userAccountService.findAllUserAccountProperty(userAccount);
			List<UserAccountPropertyPersistenceModel> userAccountProperties = new ArrayList<UserAccountPropertyPersistenceModel>();
			
			for (PropertyDTO property : userAccountDTO.getAccountProperties()) {
				UserAccountPropertyPersistenceModel userAccountProperty = null;
				if (property.getId() == 0) {
					userAccountProperty = new UserAccountPropertyPersistenceModel();
					userAccountProperty.setUserAccount(userAccount);
					userAccountProperty.setPropertyKey(property.getName());
					userAccountProperty.setPropertyValue(property.getValue());
				}
				else {
					for (UserAccountPropertyPersistenceModel currentUserAccountProperty : currentUserAccountProperties) {
						if (currentUserAccountProperty.getId() == property.getId()) {
							userAccountProperty = currentUserAccountProperty;
							break;
						}
					}
					if (userAccountProperty == null) {
						userAccountProperty = new UserAccountPropertyPersistenceModel();
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
		UserAccountPersistenceModel userAccount = userAccountService.getUserAccountByAccountId(accountId);
		userAccount.setPassword(changePasswordDTO.getNewPassword());
		userAccountService.update(userAccount);
		return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Password Changed Successfully!!!"), HttpStatus.OK);
	}
	
	private void loadUserAccount(OrganizationPersistenceModel organization, List<UserAccountModel> model) {
		List<UserAccountPersistenceModel> userAccounts = userAccountService.findAllByOrganization(organization);
		for (UserAccountPersistenceModel userAccount : userAccounts) {
			RolePersistenceModel role = userAccount.getRole();
			if (!role.getName().equalsIgnoreCase("superuser")) {
				model.add(getUserAccount(userAccount, organization, role));
			}
		}
		
		if (organization.getChildren() != null) {
			for (OrganizationPersistenceModel child: organization.getChildren()) {
				loadUserAccount(child, model);
			}
		}
	}
	
	private UserAccountModel getUserAccount(UserAccountPersistenceModel userAccount, OrganizationPersistenceModel organization, RolePersistenceModel role) {
		UserAccountModel userAccountModel = new UserAccountModel();
		userAccountModel.setAccountId(userAccount.getId());
		userAccountModel.setUserName(userAccount.getUserName());
		userAccountModel.setOrganization(new OrganizationModel(organization.getId(), organization.getName()));
		userAccountModel.setRole(new RoleModel(role.getId(), role.getName(), role.getDescription()));
		userAccountModel.setAccountProperties(new ArrayList<PropertyModel>());
		List<UserAccountPropertyPersistenceModel> userAccountProperties = userAccountService.findAllUserAccountProperty(userAccount);
		for (UserAccountPropertyPersistenceModel userAccountProperty : userAccountProperties) {
			userAccountModel.getAccountProperties().add(
					new PropertyModel(userAccountProperty.getId(), userAccountProperty.getPropertyKey(), userAccountProperty.getPropertyValue()));
		}
		return userAccountModel;
	}
}
