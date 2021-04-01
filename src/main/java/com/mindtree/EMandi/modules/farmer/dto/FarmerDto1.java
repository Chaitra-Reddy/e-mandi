package com.mindtree.EMandi.modules.farmer.dto;

public class FarmerDto1 {
	
	private String farmerName;
	private String aadharNumber;
	private String mobileNumber;
    private String bankName;
	private String accountNumber;
	private String ifsc;
	
	public FarmerDto1() {
	}
	
	public FarmerDto1(String farmerName, String aadharNumber, String mobileNumber, String bankName,
			String accountNumber, String ifsc) {
		super();
		this.farmerName = farmerName;
		this.aadharNumber = aadharNumber;
		this.mobileNumber = mobileNumber;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	
	
}
