package com.mindtree.EMandi.modules.crop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindtree.EMandi.modules.crop.entity.CropVariety;

public interface CropVarietyRepository extends JpaRepository<CropVariety, Integer> {
	@Query("select c  from CropVariety c where c.crop.cropId=?1 and c.cropClass=?2")
	CropVariety getBuyerCropPrice(int cropId, String cropClass);
	
	@Query("select c from CropVariety c where c.crop.cropId=?1 and c.cropClass=?2")
	CropVariety findCropQualityPrice(int cropId, String cropClass);
	
	
}
