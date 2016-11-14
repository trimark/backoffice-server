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

import com.trimark.backoffice.persistence.enumeration.Module;
import com.trimark.backoffice.persistence.enumeration.Permission;
import com.trimark.backoffice.persistence.enumeration.RoleType;
import com.trimark.backoffice.persistence.model.Organization;
import com.trimark.backoffice.persistence.model.Role;
import com.trimark.backoffice.persistence.model.RoleModulePermission;
import com.trimark.backoffice.service.IOrganizationService;
import com.trimark.backoffice.service.IRoleService;
import com.trimark.backoffice.web.dto.AclEntryDTO;
import com.trimark.backoffice.web.dto.RoleDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;
import com.trimark.backoffice.web.response.model.ModulePermissionsModel;
import com.trimark.backoffice.web.response.model.RoleModel;

@Controller
public class RoleController {
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping(value = "/roles/findById/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getRole(@PathVariable("id") int id) {
		Role role = roleService.findById(id);
		RoleModel roleModel = new RoleModel(role.getId(), role.getName(), role.getDescription());
		roleModel.setType(role.getRoleType());
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
		roleModel.setModulePermissions(modulePermissions);
		return new ResponseEntity<SuccessBackofficeResponse<RoleModel>>(new SuccessBackofficeResponse<RoleModel>(roleModel), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/roles/listRolesByOwner/{organizationId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getRoles(@PathVariable("organizationId") int organizationId) {
		Organization owner = organizationService.findById(organizationId);
		List<Role> roles = roleService.findRolesByOwner(owner);
		List<RoleModel> models = new ArrayList<RoleModel>();
		for (Role role : roles)
		{
			if (!role.getName().equalsIgnoreCase("superuser"))
			{
				RoleModel roleModel = new RoleModel(role.getId(), role.getName(), role.getDescription());
				roleModel.setType(role.getRoleType());
				models.add(roleModel);
			}
		}
		return new ResponseEntity<SuccessBackofficeResponse<List<RoleModel>>>(new SuccessBackofficeResponse<List<RoleModel>>(models), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/roles/listRolesByOwnerAndType/{organizationId}/{roleType}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getRolesByType(@PathVariable("organizationId") int organizationId, @PathVariable("roleType") String roleType) {
		Organization owner = organizationService.findById(organizationId);
		List<Role> roles = roleService.findRolesByOwnerAndType(owner, Enum.valueOf(RoleType.class, roleType));
		List<RoleModel> models = new ArrayList<RoleModel>();
		for (Role role : roles)
		{
			if (!role.getName().equalsIgnoreCase("superuser"))
			{
				RoleModel roleModel = new RoleModel(role.getId(), role.getName(), role.getDescription());
				roleModel.setType(role.getRoleType());
				models.add(roleModel);
			}
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
			roleService.create(role);
			List<AclEntryDTO> aclEntries = roleDTO.getAclEntries();
			List<RoleModulePermission> roleModulePermissions = new ArrayList<RoleModulePermission>();
			for (AclEntryDTO aclEntry : aclEntries) {
				int permissions = 0;
				for (String permission : aclEntry.getPermissions()) {
					permissions = permissions + Enum.valueOf(Permission.class, permission).getValue();
				}
				RoleModulePermission roleModulePermission = new RoleModulePermission();
				roleModulePermission.setRole(role);
				roleModulePermission.setModule(Enum.valueOf(Module.class, aclEntry.getModule()));
				roleModulePermission.setPermissions(permissions);
				roleModulePermissions.add(roleModulePermission);
			}
			roleService.saveRoleModulePermissions(roleModulePermissions);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/roles/update", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> update(@RequestBody RoleDTO roleDTO) {
		try {
			Role role = roleService.findById(roleDTO.getId());
			role.setDescription(roleDTO.getDescription());
			roleService.update(role);
			roleService.deleteRoleModulePermissions(roleService.findRoleModulePermissions(role));
			List<AclEntryDTO> aclEntries = roleDTO.getAclEntries();
			List<RoleModulePermission> roleModulePermissions = new ArrayList<RoleModulePermission>();
			for (AclEntryDTO aclEntry : aclEntries) {
				int permissions = 0;
				for (String permission : aclEntry.getPermissions()) {
					permissions = permissions + Enum.valueOf(Permission.class, permission).getValue();
				}
				RoleModulePermission roleModulePermission = new RoleModulePermission();
				roleModulePermission.setRole(role);
				roleModulePermission.setModule(Enum.valueOf(Module.class, aclEntry.getModule()));
				roleModulePermission.setPermissions(permissions);
				roleModulePermissions.add(roleModulePermission);
			}
			roleService.saveRoleModulePermissions(roleModulePermissions);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
}
