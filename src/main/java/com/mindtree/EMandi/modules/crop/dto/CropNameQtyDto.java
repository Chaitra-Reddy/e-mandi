package com.mindtree.EMandi.modules.crop.dto;

import org.springframework.stereotype.Component;

@Component
public class CropNameQtyDto {
	private String cropName;
	private double cropQty;

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public double getCropQty() {
		return cropQty;
	}

	public void setCropQty(double cropQty) {
		this.cropQty = cropQty;
	}
}
