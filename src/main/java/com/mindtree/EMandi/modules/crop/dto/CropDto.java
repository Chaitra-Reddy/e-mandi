package com.mindtree.EMandi.modules.crop.dto;

public class CropDto {
	private String cropName;
	private double cropMSP;
	private String adminId;

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public double getCropMSP() {
		return cropMSP;
	}

	public void setCropMSP(double cropMSP) {
		this.cropMSP = cropMSP;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
}
