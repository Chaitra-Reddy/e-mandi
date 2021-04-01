package com.mindtree.EMandi.modules.buyer.dto;



public class BuyerRequestDto {
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private double quantity;
	private String cropName;
	private String cropClass;
	
	private int mandiPincode;
	
	private int buyerId;

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
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

	public int getMandiPincode() {
		return mandiPincode;
	}

	public void setMandiPincode(int mandiPincode) {
		this.mandiPincode = mandiPincode;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	
}
