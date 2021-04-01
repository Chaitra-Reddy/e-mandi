package com.mindtree.EMandi.modules.mandi.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;
import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;

@Entity
public class Mandi {
	
	@Id
	private int mandiPincode;
	private double storage;
	@OneToMany(mappedBy = "mandi")@JsonIgnore
	private Set<FarmerTransaction> farmerTransactions = new HashSet<>();
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "clerkId")
	private Clerk clerk;
	@OneToMany(mappedBy = "mandi")@JsonIgnore
	private Set<BuyerTransaction> buyerTransactions = new HashSet<>();
	@ManyToOne
	@JoinColumn(name = "adminId")
	private Admin admin;
	@OneToMany(mappedBy = "mandi")@JsonIgnore
	private List<BuyerRequest> requests;
	
	public Mandi() {
	}
	
	public Mandi(int mandiPincode, double storage) {
		super();
		this.mandiPincode = mandiPincode;
		this.storage = storage;
	}
	
	public Mandi(int mandiPincode, double storage, Admin admin)
	{
		this.mandiPincode = mandiPincode;
		this.storage = storage;
		this.admin = admin;
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

	public Set<FarmerTransaction> getFarmerTransactions() {
		return farmerTransactions;
	}

	public void setFarmerTransactions(Set<FarmerTransaction> farmerTransactions) {
		this.farmerTransactions = farmerTransactions;
	}

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	public Set<BuyerTransaction> getBuyerTransactions() {
		return buyerTransactions;
	}

	public void setBuyerTransactions(Set<BuyerTransaction> buyerTransactions) {
		this.buyerTransactions = buyerTransactions;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	
	
	
}
