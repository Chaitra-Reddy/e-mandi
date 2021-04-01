package com.mindtree.EMandi.modules.crop.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindtree.EMandi.modules.admin.entity.Admin;

@Entity
public class Crop{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cropId;
	private String cropName;
	private double cropMSP;
	@ManyToOne
	@JoinColumn(name = "adminId")
	private Admin admin;
	@OneToMany(mappedBy = "crop")@JsonIgnore
	private List<CropVariety> varieties;
	
	public Crop() {
	}
	
	public Crop(int cropId, String cropName, double cropMSP) {
		super();
		this.cropId = cropId;
		this.cropName = cropName;
		this.cropMSP = cropMSP;
	}

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<CropVariety> getVarieties() {
		return varieties;
	}

	public void setVarieties(List<CropVariety> varieties) {
		this.varieties = varieties;
	}

	
	
}
