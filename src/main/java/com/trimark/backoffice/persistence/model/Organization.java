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
public class Organization extends AbstractPersistable<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Organization parent;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;
	
	@OneToMany(mappedBy = "parent")
	private List<Organization> children;
	
	@Column(unique = true)
	private String name;
	
	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}	
}
