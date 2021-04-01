package com.mindtree.EMandi.modules.buyer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;

@Repository
public interface BuyerTransactionRepository extends JpaRepository<BuyerTransaction, Integer> {
	@Query("select c from BuyerTransaction c where c.buyer.buyerId=?1")
	List<BuyerTransaction> findByBuyerId(int id);
	
	@Query("select distinct b.buyer.buyerId from BuyerTransaction b where b.mandi.mandiPincode=?1")
	List<Integer> getBuyerIds(int mandiPincode);
	
	@Query("select t from BuyerTransaction t where t.mandi.mandiPincode=?1 and t.buyer.buyerId=?2")
	List<BuyerTransaction> getTransactions(int mandiPincode,int buyerId);
}
