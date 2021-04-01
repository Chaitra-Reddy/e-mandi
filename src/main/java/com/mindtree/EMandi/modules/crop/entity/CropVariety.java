package com.mindtree.EMandi.modules.crop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CropVariety {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cropVarietyId;
	private String cropClass;
	private double cropQualityPrice;
	private double buyerCropPrice;
	@ManyToOne
	@JoinColumn(name = "cropId")
	private Crop crop;
	
	public CropVariety() {
	}
	
	public CropVariety(String cropClass, double cropQualityPrice, double buyerCropPrice) {
		super();
		this.cropClass = cropClass;
		this.cropQualityPrice = cropQualityPrice;
		this.buyerCropPrice = buyerCropPrice;
	}
	
	

	public CropVariety(int cropVarietyId, String cropClass, double cropQualityPrice, double buyerCropPrice) {
		super();
		this.cropVarietyId = cropVarietyId;
		this.cropClass = cropClass;
		this.cropQualityPrice = cropQualityPrice;
		this.buyerCropPrice = buyerCropPrice;
	}

	public String getCropClass() {
		return cropClass;
	}

	public void setCropClass(String cropClass) {
		this.cropClass = cropClass;
	}

	public double getCropQualityPrice() {
		return cropQualityPrice;
	}

	public void setCropQualityPrice(double cropQualityPrice) {
		this.cropQualityPrice = cropQualityPrice;
	}

	public double getBuyerCropPrice() {
		return buyerCropPrice;
	}

	public void setBuyerCropPrice(double buyerCropPrice) {
		this.buyerCropPrice = buyerCropPrice;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}
	
	
	
}
