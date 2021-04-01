package com.mindtree.EMandi.modules.buyer.service;


import java.util.List;
import java.util.Map;

import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.BuyerServiceException;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;

public interface BuyerService {
	
void updateBuyer(Buyer buyer);
	
	Buyer getBuyer(int id);
	
	Buyer saveBuyer(Buyer buyer);
	
	public String validateLogin( Map<String, String> map);
	
	public Buyer updatePassword(Map<String, String> map);
	
	public String validateQA(Map<String, String> map);

	public List<BuyerTransaction> getTransactions(int id) throws BuyerServiceException;
	
	public List<BuyerTransaction> getBuyerTransaction(String clerkId,int buyerId) throws IdNotFoundException;
	
	public BuyerRequest saveBuyerRequest(BuyerRequest buyer) throws ServiceException;

}
