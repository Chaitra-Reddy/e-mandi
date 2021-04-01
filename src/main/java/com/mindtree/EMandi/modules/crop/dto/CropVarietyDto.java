package com.mindtree.EMandi.modules.crop.dto;

public class CropVarietyDto {

	private String cropClass;
	private double cropQualityPrice;
	private double buyerCropPrice;
	private int cropId;

	public CropVarietyDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CropVarietyDto(String cropClass, double cropQualityPrice, double buyerCropPrice, int cropId) {
		super();
		this.cropClass = cropClass;
		this.cropQualityPrice = cropQualityPrice;
		this.buyerCropPrice = buyerCropPrice;
		this.cropId = cropId;
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

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

}
