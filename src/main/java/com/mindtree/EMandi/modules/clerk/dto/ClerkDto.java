package com.mindtree.EMandi.modules.clerk.dto;

public class ClerkDto {
	private String clerkId;
	private String clerkName;
	private String password;
	private String mobileNumber;
	private String emailId;
	
	public ClerkDto() {}

	public ClerkDto(String clerkId, String clerkName, String password, String mobileNumber, String emailId) {
		super();
		this.clerkId = clerkId;
		this.clerkName = clerkName;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
	}

	public String getClerkId() {
		return clerkId;
	}

	public void setClerkId(String clerkId) {
		this.clerkId = clerkId;
	}

	public String getClerkName() {
		return clerkName;
	}

	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
