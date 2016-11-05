package com.trimark.backoffice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trimark.backoffice.persistence.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.service.IRoleService;
import com.trimark.backoffice.web.dto.RoleDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;
import com.trimark.backoffice.web.response.model.RoleModel;

@Controller
public class RoleController {
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping(value = "/roles/listRolesByOwnerAndType/{organizationId}/{roleType}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getRoles(@PathVariable("organizationId") int organizationId, @PathVariable("roleType") String roleType) {
		Organization owner = organizationService.findById(organizationId);
		List<Role> roles = roleService.findRolesByOwnerAndType(owner, Enum.valueOf(RoleType.class, roleType));
		List<RoleModel> models = new ArrayList<RoleModel>();
		for (Role role : roles)
		{
			models.add(new RoleModel(role.getId(), role.getName()));
		}
		return new ResponseEntity<SuccessBackofficeResponse<List<RoleModel>>>(new SuccessBackofficeResponse<List<RoleModel>>(models), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/roles/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> create(@RequestBody RoleDTO roleDTO) {
		try {
			Role role = new Role();
			role.setName(roleDTO.getName());
			role.setDescription(roleDTO.getDescription());
			role.setOwner(organizationService.findById(roleDTO.getOrganization().getId()));
			role.setRoleType(Enum.valueOf(RoleType.class, roleDTO.getType()));
			roleService.saveRole(role);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
}
