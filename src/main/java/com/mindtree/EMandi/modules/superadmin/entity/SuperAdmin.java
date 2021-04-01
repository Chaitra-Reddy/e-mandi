package com.mindtree.EMandi.modules.superadmin.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindtree.EMandi.modules.admin.entity.Admin;

@Entity
public class SuperAdmin {
	
	@Id
	private int superAdminId;
	private String password;
	private String emailId;
	@OneToMany(mappedBy = "sAdmin")
	@JsonIgnore
	private List<Admin> admins;
	
	
	public SuperAdmin() {
	}
	
	public SuperAdmin(int superAdminId, String password) {
		super();
		this.superAdminId = superAdminId;
		this.password = password;
	}

	public int getSuperAdminId() {
		return superAdminId;
	}
	
	public void setSuperAdminId(int superAdminId) {
		this.superAdminId = superAdminId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	
}
