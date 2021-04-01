package com.mindtree.EMandi.modules.buyer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mindtree.EMandi.modules.mandi.entity.Mandi;

@Entity
public class BuyerRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int requestId;
	@ManyToOne
	@JoinColumn(name="buyerId")
	private Buyer buyer;
	private double quantity;
	@ManyToOne
	@JoinColumn(name="mandiPincode")
	private Mandi mandi;
	private String cropName;
	private String cropClass;
	
	public BuyerRequest() {
		
	}
	
	public BuyerRequest(int requestId, Buyer buyer, double quantity, Mandi mandi, String cropName, String cropClass) {
		super();
		this.requestId = requestId;
		this.buyer = buyer;
		this.quantity = quantity;
		this.mandi = mandi;
		this.cropName = cropName;
		this.cropClass = cropClass;
	}
	
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public Mandi getMandi() {
		return mandi;
	}
	public void setMandi(Mandi mandi) {
		this.mandi = mandi;
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
	
}
