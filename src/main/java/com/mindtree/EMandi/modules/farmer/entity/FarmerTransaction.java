package com.mindtree.EMandi.modules.farmer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mindtree.EMandi.modules.mandi.entity.Mandi;

@Entity
public class FarmerTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int transactionId;
	private String cropName;
	private String cropClass;
	private double quantity;
	private double amount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date();
	@ManyToOne
	@JoinColumn(name = "farmerId")
	private Farmer farmer;
	@ManyToOne
	@JoinColumn(name = "mandiPincode")
	private Mandi mandi;
	
	public FarmerTransaction() {
	}
	
	public FarmerTransaction(String cropName, String cropClass, double quantity, double amount) {
		super();
		this.cropName = cropName;
		this.cropClass = cropClass;
		this.quantity = quantity;
		this.amount = amount;
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

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public Mandi getMandi() {
		return mandi;
	}

	public void setMandi(Mandi mandi) {
		this.mandi = mandi;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((cropClass == null) ? 0 : cropClass.hashCode());
		result = prime * result + ((cropName == null) ? 0 : cropName.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((farmer == null) ? 0 : farmer.hashCode());
		result = prime * result + ((mandi == null) ? 0 : mandi.hashCode());
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + transactionId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FarmerTransaction other = (FarmerTransaction) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (cropClass == null) {
			if (other.cropClass != null)
				return false;
		} else if (!cropClass.equals(other.cropClass))
			return false;
		if (cropName == null) {
			if (other.cropName != null)
				return false;
		} else if (!cropName.equals(other.cropName))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (farmer == null) {
			if (other.farmer != null)
				return false;
		} else if (!farmer.equals(other.farmer))
			return false;
		if (mandi == null) {
			if (other.mandi != null)
				return false;
		} else if (!mandi.equals(other.mandi))
			return false;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		if (transactionId != other.transactionId)
			return false;
		return true;
	}

	public FarmerTransaction(int transactionId, String cropName, String cropClass, double quantity, double amount,
			Date date) {
		super();
		this.transactionId = transactionId;
		this.cropName = cropName;
		this.cropClass = cropClass;
		this.quantity = quantity;
		this.amount = amount;
		this.date = date;
	}

		
}
