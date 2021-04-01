package com.mindtree.EMandi.modules.farmer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindtree.EMandi.modules.farmer.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Integer>{
	
	@Query("select f from Farmer f where f.farmerId=?1")
	Farmer getFarmer(int farmerId);
}
