package com.mindtree.EMandi.modules.buyer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;
@Repository
public interface BuyerRequestRepository extends JpaRepository<BuyerRequest,Integer>{

	@Query("select r from BuyerRequest r where r.mandi.mandiPincode=?1")
	List<BuyerRequest> getReuquestListByMandiPincode(int mandiPincode);
}
