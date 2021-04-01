package com.mindtree.EMandi.modules.admin.dto;

import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

public class AdminDto {
	private String adminId;
	private String password, emailId, state;
	private SuperAdmin sAdmin;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public SuperAdmin getsAdmin() {
		return sAdmin;
	}

	public void setsAdmin(SuperAdmin sAdmin) {
		this.sAdmin = sAdmin;
	}
}
