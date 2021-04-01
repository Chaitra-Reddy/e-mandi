package com.mindtree.EMandi.modules.buyer.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Buyer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int buyerId;
	private String buyerName;
	private String password;
	private String phoneNumber;
	private String securityQuestion;
	private String answer;
	@OneToMany(mappedBy = "buyer")@JsonIgnore
	private Set<BuyerTransaction> buyerTransactions;
	@OneToMany(mappedBy = "buyer")@JsonIgnore
	private List<BuyerRequest> requests;
	
	public Buyer() {
	}
	
	public Buyer(int buyerId, String buyerName, String password, String phoneNumber) {
		super();
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public Buyer(int buyerId, String buyerName, String password, String phoneNumber, String securityQuestion,
			String answer) {
		super();
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.securityQuestion = securityQuestion;
		this.answer = answer;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<BuyerTransaction> getBuyerTransactions() {
		return buyerTransactions;
	}

	public void setBuyerTransactions(Set<BuyerTransaction> buyerTransactions) {
		this.buyerTransactions = buyerTransactions;
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
	
}
