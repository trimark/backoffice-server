package com.trimark.backoffice.persistence.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "organizations")
public class OrganizationPersistenceModel extends AbstractPersistable<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private OrganizationPersistenceModel parent;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private RolePersistenceModel role;
	
	@OneToMany(mappedBy = "parent")
	private List<OrganizationPersistenceModel> children;
	
	@Column(unique = true)
	private String name;
	
	public OrganizationPersistenceModel getParent() {
		return parent;
	}

	public void setParent(OrganizationPersistenceModel parent) {
		this.parent = parent;
	}
	
	public RolePersistenceModel getRole() {
		return role;
	}

	public void setRole(RolePersistenceModel role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrganizationPersistenceModel> getChildren() {
		return children;
	}

	public void setChildren(List<OrganizationPersistenceModel> children) {
		this.children = children;
	}	
}
