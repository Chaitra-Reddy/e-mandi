package com.mindtree.EMandi.modules.buyer.dto;

import java.util.Date;

public class BuyerTransactionDto {
	private int transactionId;
	private String cropName;
	private String cropClass;
	private double quantity;
	private double amount;
	private Date date;
	private int mandiPincode;
	public BuyerTransactionDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BuyerTransactionDto(int transactionId, String cropName, String cropClass, double quantity, double amount,
			Date date, int mandiPincode) {
		super();
		this.transactionId = transactionId;
		this.cropName = cropName;
		this.cropClass = cropClass;
		this.quantity = quantity;
		this.amount = amount;
		this.date = date;
		this.mandiPincode = mandiPincode;
	}
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getMandiPincode() {
		return mandiPincode;
	}
	public void setMandiPincode(int mandiPincode) {
		this.mandiPincode = mandiPincode;
	}
	
}
