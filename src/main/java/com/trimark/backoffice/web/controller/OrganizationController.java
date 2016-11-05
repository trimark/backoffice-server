package com.trimark.backoffice.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ListBackofficeResponse;
import com.trimark.backoffice.web.response.model.OrganizationModel;
import com.trimark.backoffice.web.response.model.RoleModel;

@RestController
public class OrganizationController {
	
	@Autowired
	private IOrganizationService organizationService;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/organization/getAllOrganizations", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllOrganizations() {
		List<Organization> organizations = organizationService.findAll();
		ListBackofficeResponse<OrganizationModel> response = new ListBackofficeResponse<OrganizationModel>();
		for (Organization organization : organizations) {
			response.add(new OrganizationModel(organization.getId(), organization.getName()));
		}
		return new ResponseEntity<ListBackofficeResponse<OrganizationModel>>(response, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/organization/getChildOrganizations/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getChildOrganizations(@PathVariable("id") int id) {
		List<Organization> organizations = organizationService.findAllChildOrganization(id);
		ListBackofficeResponse<OrganizationModel> response = new ListBackofficeResponse<OrganizationModel>();
		for (Organization organization : organizations) {
			Role role = organization.getRole();
			response.add(new OrganizationModel(organization.getId(), organization.getName(), new RoleModel(role.getId(), role.getName())));
		}
		return new ResponseEntity<ListBackofficeResponse<OrganizationModel>>(response, HttpStatus.OK);
	}
}
