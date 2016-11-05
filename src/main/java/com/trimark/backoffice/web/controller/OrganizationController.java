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
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.service.IRoleService;
import com.trimark.backoffice.web.dto.OrganizationDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.ListBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;
import com.trimark.backoffice.web.response.model.OrganizationModel;
import com.trimark.backoffice.web.response.model.RoleModel;

@RestController
public class OrganizationController {
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping(value = "/organizations/findById/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getOrganization(@PathVariable("id") int id) {
		Organization organization = organizationService.findById(id);
		Role role = organization.getRole();
		OrganizationModel model = new OrganizationModel(organization.getId(), organization.getName(), new RoleModel(role.getId(), role.getName()));
		return new ResponseEntity<SuccessBackofficeResponse<OrganizationModel>>(new SuccessBackofficeResponse<OrganizationModel>(model), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/organizations/listAll", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllOrganizations() {
		List<Organization> organizations = organizationService.findAll();
		ListBackofficeResponse<OrganizationModel> response = new ListBackofficeResponse<OrganizationModel>();
		for (Organization organization : organizations) {
			response.add(new OrganizationModel(organization.getId(), organization.getName()));
		}
		return new ResponseEntity<ListBackofficeResponse<OrganizationModel>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/organizations/listChildOrganizations/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getChildOrganizations(@PathVariable("id") int id) {
		List<Organization> organizations = organizationService.findAllChildOrganization(id);
		List<OrganizationModel> response = new ArrayList<OrganizationModel>();
		for (Organization organization : organizations) {
			response.add(createOrganizationModel(organization));
		}
		return new ResponseEntity<SuccessBackofficeResponse<List<OrganizationModel>>>(new SuccessBackofficeResponse<List<OrganizationModel>>(response), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/organizations/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> create(@RequestBody OrganizationDTO organizationDTO) {
		try {
			
			Organization organization = new Organization();
			organization.setName(organizationDTO.getName());
			organization.setParent(organizationService.findById(organizationDTO.getParent().getId()));
			organization.setRole(roleService.findById(organizationDTO.getRole().getId()));
			organizationService.create(organization);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/organizations/update", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> update(@RequestBody OrganizationDTO organizationDTO) {
		try {			
			Organization organization = organizationService.findById(organizationDTO.getId());
			organization.setRole(roleService.findById(organizationDTO.getRole().getId()));
			organizationService.update(organization);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	private OrganizationModel createOrganizationModel(Organization organization) {
		OrganizationModel parent = new OrganizationModel(organization.getParent().getId(), organization.getParent().getName());
		Role role = organization.getRole();
		OrganizationModel result = new OrganizationModel(organization.getId(), organization.getName(), new RoleModel(role.getId(), role.getName()));
		result.setParent(parent);
		result.setChildren(createChildrenOrganizationModel(organization.getChildren()));
		return result;
	}
	
	private List<OrganizationModel> createChildrenOrganizationModel(List<Organization> organizations) {
		List<OrganizationModel> result = new ArrayList<OrganizationModel>();
		for (Organization child : organizations) {
			Role role = child.getRole();
			OrganizationModel organizationModel = new OrganizationModel(child.getId(), child.getName(), new RoleModel(role.getId(), role.getName()));
			organizationModel.setChildren(this.createChildrenOrganizationModel(child.getChildren()));
			result.add(organizationModel);			
		}
		return result;
	}
}
