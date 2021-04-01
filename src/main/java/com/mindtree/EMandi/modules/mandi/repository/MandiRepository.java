package com.mindtree.EMandi.modules.mandi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindtree.EMandi.modules.mandi.entity.Mandi;
@Repository
public interface MandiRepository extends JpaRepository<Mandi,Integer>{

	@Query("select c.clerk.clerkId from Mandi c where c.admin.adminId=?1")
	List<String> findByAdminId(String adminId);
	
	@Query("select c.admin.adminId from Mandi c where c.clerk.clerkId=?1")
	String findAdminIdByClerkId(String clerkId);
	
	@Query("select m from Mandi m where m.admin.adminId=?1")
	List<Mandi> findMandiByAdminId(String adminId);
	
	@Query("select m.mandiPincode from Mandi m where m.clerk.clerkId=?1")
	int getMandiPincode(String clerkId);
	
	@Query("select m.admin.adminId from Mandi m where m.mandiPincode=?1")
	String getAdminIdByMandiPincode(int mandiPincode);
}
