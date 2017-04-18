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

import com.trimark.backoffice.enumeration.RoleType;

@Entity
@Table(name = "roles")
public class RolePersistenceModel extends AbstractPersistable<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization")
	private OrganizationPersistenceModel owner;
	
	@Column(unique = true)
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "role_type")
	private RoleType roleType;
	
	@Column
	private String description;

	public OrganizationPersistenceModel getOwner() {
		return owner;
	}

	public void setOwner(OrganizationPersistenceModel owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
