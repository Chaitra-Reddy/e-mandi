package com.mindtree.EMandi.modules.farmer.service;

import java.util.List;
import java.util.Map;

import com.mindtree.EMandi.exception.FarmerException;
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.FarmerTransactionServiceException;
import com.mindtree.EMandi.exception.service.FarmersServiceException;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;
import com.mindtree.EMandi.modules.farmer.dto.ExtraCreditDto;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;



public interface FarmerService {

	public String validateLogin(Map<String, String> map);

	public Farmer createFarmer(Farmer farmer) throws FarmersServiceException;

	public Farmer getFarmer(int id) throws FarmerException;

	public Farmer updatePassword(Map<String, String> map) throws FarmerException;

	public String validateQA(Map<String, String> map) throws FarmerException;

	public String updateFarmerProfile(Farmer farmerDetails, Farmer farmer) throws FarmersServiceException;

	
	public List<FarmerTransaction> findByMandiPincode(int mandiPincode) throws ServiceException;


	public List<FarmerTransaction> getTransactions(String clerkId, int farmerId) throws IdNotFoundException;

	public boolean checkForTransactionId(int transactionId) throws FarmerTransactionServiceException;

	public boolean creditExtraAmount(ExtraCreditDto extraCreditDto) throws FarmerTransactionServiceException;
	
	
	public List<FarmerTransaction> getTransactions(int id) throws FarmersServiceException;
}
