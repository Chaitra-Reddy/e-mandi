package com.mindtree.EMandi.modules.farmer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;
@Repository
public interface FarmerTransactionRepository extends JpaRepository<FarmerTransaction, Integer>
{
	@Query("select c from FarmerTransaction c where c.mandi.mandiPincode=?1")
	List<FarmerTransaction> findByMandiPincode(int mandiCode);
	

	@Query("select distinct f.farmer.farmerId from FarmerTransaction f where f.mandi.mandiPincode=?1")
	List<Integer> getFarmerIds(int mandiPincode);
	

	
	@Query("select c from FarmerTransaction c where c.farmer.farmerId=?1")
	List<FarmerTransaction> findByFarmerId(int id);
	
	@Query("select f from FarmerTransaction f where f.farmer.farmerId=?1 and f.mandi.mandiPincode=?2")
	List<FarmerTransaction> getTransactions(int farmerId, int mandiPincode);

}
