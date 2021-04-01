package com.mindtree.EMandi.modules.farmer.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Farmer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int farmerId;
	private String farmerName;
	private String bankName;
	private String accountNumber;
	private String ifsc;
	private String aadharNumber;
	private String password;
	private String mobileNumber;
	private String securityQuestion,answer;
	@OneToMany(mappedBy = "farmer")@JsonIgnore
	private Set<FarmerTransaction> transactions; 
	
	public Farmer() {
	}
	
	public Farmer(int farmerId, String farmerName, String bankName, String accountNumber, String ifsc,
			String aadharNumber, String password, String mobileNumber) {
		super();
		this.farmerId = farmerId;
		this.farmerName = farmerName;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
		this.aadharNumber = aadharNumber;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}

	public int getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(int farmerId) {
		this.farmerId = farmerId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
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

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
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

	public Set<FarmerTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<FarmerTransaction> transactions) {
		this.transactions = transactions;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Farmer [farmerId=" + farmerId + ", farmerName=" + farmerName + ", bankName=" + bankName
				+ ", accountNumber=" + accountNumber + ", ifsc=" + ifsc + ", aadharNumber=" + aadharNumber
				+ ", password=" + password + ", mobileNumber=" + mobileNumber + ", securityQuestion=" + securityQuestion
				+ ", answer=" + answer + ", transactions=" + transactions + "]";
	}

	public Farmer(int farmerId, String farmerName, String bankName, String accountNumber, String ifsc,
			String aadharNumber, String password, String mobileNumber, String securityQuestion, String answer) {
		
		this.farmerId = farmerId;
		this.farmerName = farmerName;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
		this.aadharNumber = aadharNumber;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.securityQuestion = securityQuestion;
		this.answer = answer;
		
	}
	
	
	
	
}
