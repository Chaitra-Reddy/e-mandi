package com.mindtree.EMandi.modules.mandi.dto;

import com.mindtree.EMandi.modules.clerk.entity.Clerk;

public class MandiDto {
	
	private int mandiPincode;
	private double storage;
	private Clerk clerk;
	private String adminId;
	
	public MandiDto() {
		
	}
	
	public MandiDto(int mandiPincode, double storage, Clerk clerk, String adminId) {
		super();
		this.mandiPincode = mandiPincode;
		this.storage = storage;
		this.clerk = clerk;
		this.adminId = adminId;
	}

	public int getMandiPincode() {
		return mandiPincode;
	}

	public void setMandiPincode(int mandiPincode) {
		this.mandiPincode = mandiPincode;
	}

	public double getStorage() {
		return storage;
	}

	public void setStorage(double storage) {
		this.storage = storage;
	}

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	

}
