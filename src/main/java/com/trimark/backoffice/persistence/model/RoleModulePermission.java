package com.trimark.backoffice.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.trimark.backoffice.persistence.enumeration.Module;

@Entity
@Table(name = "role_module_permissions")
public class RoleModulePermission extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;
	
	@Enumerated(EnumType.ORDINAL)
	private Module module;
	
	@Column
	private int permissions;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
}
