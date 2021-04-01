package com.mindtree.EMandi.modules.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;

public interface AdminRepository extends JpaRepository<Admin, String> 
{
	@Query("SELECT f FROM Farmer f WHERE f.farmerId IN (SELECT t.farmer.farmerId FROM FarmerTransaction t WHERE t.mandi.mandiPincode IN (SELECT m FROM Mandi m WHERE m.admin.adminId = ?1 AND m.mandiPincode = ?2))")
	List<Farmer> findAllFarmersByAdminIdAndMandiPincode(String adminId, int mandiPincode);
	
	@Query("SELECT b FROM Buyer b WHERE b.buyerId IN (SELECT t.buyer.buyerId FROM BuyerTransaction t WHERE t.mandi.mandiPincode IN (SELECT m FROM Mandi m WHERE m.admin.adminId = ?1 AND m.mandiPincode = ?2))")
	List<Buyer> findAllBuyersByAdminIdAndMandiPincode(String adminId, int mandiPincode);
	
	List<Admin> findAdminByState(String state);
}
