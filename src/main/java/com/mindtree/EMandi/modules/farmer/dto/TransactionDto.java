package com.mindtree.EMandi.modules.farmer.dto;

import java.util.Date;

public class TransactionDto {
	private int transactionId;
	private String cropName;
	private String cropClass;
	private double quantity;
	private double amount;
	private Date date;
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private int mandiPincode;
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

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

	public int getMandiPincode() {
		return mandiPincode;
	}

	public void setMandiPincode(int mandiPincode) {
		this.mandiPincode = mandiPincode;
	}
}
