package com.mindtree.EMandi.modules.admin.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

@Entity
public class Admin {

	@Id
	private String adminId;
	private String password;
	private String emailId;
	private String state;
	@ManyToOne
	@JoinColumn(name = "superAdminId")
	private SuperAdmin sAdmin;
	@OneToMany(mappedBy = "admin")
	@JsonIgnore
	private List<Mandi> mandis;
	@OneToMany(mappedBy = "admin")
	@JsonIgnore
	private List<Crop> crops;

	public Admin() {
	}

	public Admin(String adminId, String password, String emailId, String state, SuperAdmin sAdmin) {
		super();
		this.adminId = adminId;
		this.password = password;
		this.emailId = emailId;
		this.state = state;
		this.sAdmin = sAdmin;
	}

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

	public SuperAdmin getAdmin() {
		return sAdmin;
	}

	public void setAdmin(SuperAdmin admin) {
		this.sAdmin = admin;
	}

	public SuperAdmin getsAdmin() {
		return sAdmin;
	}

	public void setsAdmin(SuperAdmin sAdmin) {
		this.sAdmin = sAdmin;
	}

	public List<Mandi> getMandis() {
		return mandis;
	}

	public void setMandis(List<Mandi> mandis) {
		this.mandis = mandis;
	}

	public List<Crop> getCrops() {
		return crops;
	}

	public void setCrops(List<Crop> crops) {
		this.crops = crops;
	}

}
