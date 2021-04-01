package com.mindtree.EMandi.modules.crop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import com.mindtree.EMandi.modules.crop.entity.Crop;
@Repository
public interface CropRepository extends JpaRepository<Crop, Integer> 
{
	@Query("select c from Crop c where c.cropName=?1 and c.admin.adminId=?2")
	Crop findMSP(String cropName,String adminId);

	@Query("select c from Crop c where c.admin.adminId=?1")
	List<Crop> findByAdminId(String adminId);
	
	@Query("select c.cropId from Crop c where c.admin.adminId = ?1 and c.cropName = ?2")
	Integer getCropIdByAdminIdAndCropName(String adminID, String cropName);	
	
	
	
}
