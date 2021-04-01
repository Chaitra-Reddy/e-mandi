package com.mindtree.EMandi.modules.buyer.service.impl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mindtree.EMandi.exception.DatabaseConnectionFailureException;
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.BuyerServiceException;
import com.mindtree.EMandi.exception.service.DataNotFoundException;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;
import com.mindtree.EMandi.modules.buyer.repository.BuyerRepository;
import com.mindtree.EMandi.modules.buyer.repository.BuyerRequestRepository;
import com.mindtree.EMandi.modules.buyer.repository.BuyerTransactionRepository;
import com.mindtree.EMandi.modules.buyer.service.BuyerService;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;

@Service
//@Profile("cloudx")
public class BuyerServiceImpl implements BuyerService{
	
	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private BuyerTransactionRepository buyerTransactionRepo;
	
	@Autowired
	private MandiRepository mandiRepo;
	@Autowired
	private BuyerRequestRepository requestRep;
	@Override
	public void updateBuyer(Buyer buyer) {
		// TODO Auto-generated method stub
		buyer.setAnswer(buyerRepository.findById(buyer.getBuyerId()).get().getAnswer());
		buyer.setSecurityQuestion(buyerRepository.findById(buyer.getBuyerId()).get().getSecurityQuestion());
		buyerRepository.save(buyer);
	}

	@Override
	public Buyer getBuyer(int id) throws IllegalArgumentException, NoSuchElementException{
		Buyer buyer;
		try {
			 buyer=buyerRepository.findById(id).get();
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Id is null");
		}catch(NoSuchElementException a) {
			throw new NoSuchElementException("No such element Present");
		}
		return buyer;
	}

	@Override
	public Buyer saveBuyer(Buyer buyer) {
		// TODO Auto-generated method stub
		
		return buyerRepository.save(buyer);
	}
	@Override
	public String validateLogin(Map<String, String> map) {
		Buyer buyer = buyerRepository.findById((Integer.parseInt(map.get("userId")))).orElse(null);
		if (buyer != null)
			if (buyer.getPassword().equals(map.get("password")))
				return map.get("userId");
			else
				return null;
		return null;
	}

	@Override
	public Buyer updatePassword(Map<String, String> map) {
		int id=Integer.parseInt(map.get("userId"));
		Buyer buyer=buyerRepository.findById(id).get();
		buyer.setPassword(map.get("password"));
		buyerRepository.save(buyer);
		return buyer;
	}

	@Override
	public String validateQA(Map<String, String> map) {
		Buyer buyer=buyerRepository.findById(Integer.parseInt(map.get("userId"))).get();
		if(buyer.getSecurityQuestion().equalsIgnoreCase(map.get("sQ"))) {
			if(buyer.getAnswer().equalsIgnoreCase(map.get("answer"))) {
				System.out.println(buyer.getAnswer()+"&"+ map.get("answer"));
				return "yes";
			}
		}
		return null;
	}

	@Override
	public List<BuyerTransaction> getTransactions(int id) throws BuyerServiceException {
		try {
			return buyerTransactionRepo.findByBuyerId(id);
		} catch (Exception e) {
			throw new DataNotFoundException("Data not founded.");
		}
		
	}

	@Override
	public List<BuyerTransaction> getBuyerTransaction(String clerkId, int buyerId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		int mandiPincode = mandiRepo.getMandiPincode(clerkId);
		
		if(buyerTransactionRepo.getTransactions(mandiPincode, buyerId).isEmpty()) {
			throw new IdNotFoundException("Id Not Found");
		}
		
		return buyerTransactionRepo.getTransactions(mandiPincode, buyerId);
	}

	
	@Override
	public BuyerRequest saveBuyerRequest(BuyerRequest buyer) throws ServiceException{
		// TODO Auto-generated method stub
		BuyerRequest buyerReq=null;
		try {
			buyerReq=requestRep.save(buyer);
		}catch(Exception e) {
			throw new DatabaseConnectionFailureException("Failed to connect");
		}
		return buyerReq;
	}

}
