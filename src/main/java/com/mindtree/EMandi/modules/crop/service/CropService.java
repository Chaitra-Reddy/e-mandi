package com.mindtree.EMandi.modules.crop.service;

import java.util.List;

import com.mindtree.EMandi.exception.InvalidCropException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.CropServiceException;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;

public interface CropService {
	public String addCrop(Crop crop);

	public List<Crop> getAllCrops() throws ServiceException;
	
	public Crop getCropMSP(String cropName, String adminId) throws InvalidCropException;
	
	public String updateMSP(Crop crop) throws InvalidCropException;
	
	public CropVariety getCropCostForBuyer(String cropName, String cropClass, String adminId) throws CropServiceException ;

	public CropVariety updateCropCostForBuyer(String cropName, String cropClass, String cropPrice, String adminId) throws CropServiceException ;
	public List<Crop> findCropByAdminId(String adminId) throws ServiceException;
	
}
