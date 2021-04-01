package com.mindtree.EMandi.modules.farmer.dto;

import org.springframework.stereotype.Component;

@Component
public class FarmerTransactionDto {
	private String cropName;
	private String cropClass;
	private double quantity;
	private double amount;
	private int farmerId;
	private int mandiPincode;

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getCropClass() {
		return cropClass;
	}

	public void setCropClass(String cropClass) {
		this.cropClass = cropClass;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(int farmerId) {
		this.farmerId = farmerId;
	}

	public int getMandiPincode() {
		return mandiPincode;
	}

	public void setMandiPincode(int mandiPincode) {
		this.mandiPincode = mandiPincode;
	}
}
