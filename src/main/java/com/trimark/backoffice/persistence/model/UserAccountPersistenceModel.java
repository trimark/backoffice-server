package com.trimark.backoffice.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "user_credentials")
public class UserAccountPersistenceModel implements Persistable<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue @Column(name = "account_id") private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private OrganizationPersistenceModel organization;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private RolePersistenceModel role;
	
	@Column(name = "user_name")
	private String userName;
	
	private String password;

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	public OrganizationPersistenceModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationPersistenceModel organization) {
		this.organization = organization;
	}

	public RolePersistenceModel getRole() {
		return role;
	}

	public void setRole(RolePersistenceModel role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isNew() {
		return null == getId();
	}

}
