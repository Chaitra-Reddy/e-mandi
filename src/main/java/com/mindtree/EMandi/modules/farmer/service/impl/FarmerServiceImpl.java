package com.mindtree.EMandi.modules.farmer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mindtree.EMandi.exception.DataNotAddedException;
import com.mindtree.EMandi.exception.FarmerException;
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;

import com.mindtree.EMandi.exception.service.FarmerTransactionServiceException;

import com.mindtree.EMandi.exception.service.FarmersServiceException;
import com.mindtree.EMandi.modules.crop.repository.CropRepository;
import com.mindtree.EMandi.modules.crop.repository.CropVarietyRepository;
import com.mindtree.EMandi.modules.farmer.dto.ExtraCreditDto;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;
import com.mindtree.EMandi.modules.farmer.repository.FarmerRepository;
import com.mindtree.EMandi.modules.farmer.repository.FarmerTransactionRepository;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;


@Service
public class FarmerServiceImpl implements FarmerService {

	@Autowired
	FarmerRepository farmerRepo;

	@Autowired
	private FarmerTransactionRepository rep;


	@Autowired

	private MandiRepository mandiRepo;
	
	@Autowired
	FarmerTransactionRepository transactionRepo;
	
	@Autowired
	private CropRepository cropRepo;
	
	@Autowired
	private CropVarietyRepository cropVarietyRepo;

	@Override
	public String validateLogin(Map<String, String> map) {
		Farmer farmer = farmerRepo.findById(Integer.parseInt(map.get("userId"))).orElse(null);
		if (farmer != null)
			if (farmer.getPassword().equals(map.get("password")))
				return map.get("userId");
			else
				return null;
		return null;
	}

	@Override
	public Farmer createFarmer(Farmer farmer) throws DataNotAddedException {
		List<Farmer> farmerList = farmerRepo.findAll();
		Predicate<Farmer> farmerPredicatec = f -> f.getAadharNumber().equals(farmer.getAadharNumber());
		try {
			for (Farmer farmer2 : farmerList) {
				System.out.println(farmer2.toString());
				if (farmerPredicatec.test(farmer2)) {
					return null;
				}
			}
			return farmerRepo.save(farmer);

		} catch (Exception e) {
			throw new DataNotAddedException("Data is not added");
		}
	}

	@Override
	public Farmer getFarmer(int id) throws FarmerException {
		Farmer farmer;
		try {
			farmer = farmerRepo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new FarmerException("No data found for that id", e);
		}

		return farmer;
	}

	@Override
	public Farmer updatePassword(Map<String, String> map) throws FarmerException {
		int id = Integer.parseInt(map.get("userId"));
		Farmer farmer;
		try {
			farmer = farmerRepo.findById(id).get();
			farmer.setPassword(map.get("password"));
			farmer=farmerRepo.save(farmer);
		} catch (IllegalArgumentException e) {
			throw new FarmerException("Password couldnt be changed");
		}
		return farmer;

	}

	@Override
	public String validateQA(Map<String, String> map) throws FarmerException {
		Farmer farmer = farmerRepo.findById(Integer.parseInt(map.get("userId"))).get();
		if (farmer.getSecurityQuestion().equalsIgnoreCase(map.get("sQ"))) {
			if (farmer.getAnswer().equalsIgnoreCase(map.get("answer"))) {
				return "yes"; 
			}
		}
		return null; 
	}  

	@Override
	public String updateFarmerProfile(Farmer farmerDetails, Farmer farmer) throws FarmersServiceException {
		
		int count = 0;
		if (!farmerDetails.getAccountNumber().equals(farmer.getAccountNumber())) {
			farmerDetails.setAccountNumber(farmer.getAccountNumber());
		} else {
			count++;
		}
		if (!farmerDetails.getAnswer().equals(farmer.getAnswer())) {
			farmerDetails.setAnswer(farmer.getAnswer());
		} else {
			count++;
		}
		if (!farmerDetails.getBankName().equals(farmer.getBankName())) {
			farmerDetails.setBankName(farmer.getBankName());
		} else {
			count++;
		}
		if (!farmerDetails.getIfsc().equals(farmer.getIfsc())) {
			farmerDetails.setIfsc(farmer.getIfsc());
		} else {
			count++;
		}
		if (!farmerDetails.getFarmerName().equals(farmer.getFarmerName())) {
			farmerDetails.setFarmerName(farmer.getFarmerName());
		} else {
			count++;
		}
		if (!farmerDetails.getMobileNumber().equals(farmer.getMobileNumber())) {
			farmerDetails.setMobileNumber(farmer.getMobileNumber());
		} else {
			count++;
		}
		if (!farmerDetails.getPassword().equals(farmer.getPassword())) {
			farmerDetails.setPassword(farmer.getPassword());
		} else {
			count++;
		}
		if (count == 7) {
			return null;
		} else {
			farmerRepo.save(farmerDetails);
		}
		return "Updated SuccessFully";
	}

	@Override
	public List<FarmerTransaction> findByMandiPincode(int mandiPincode) throws ServiceException {
		// TODO Auto-generated method stub
		List<FarmerTransaction> farmer=null;
		farmer=rep.findByMandiPincode(mandiPincode);
		return farmer;
	}

	public List<FarmerTransaction> getTransactions(String clerkId, int farmerId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		int mandiPincode = mandiRepo.getMandiPincode(clerkId);
		
		if(transactionRepo.getTransactions(farmerId, mandiPincode).isEmpty()) {
			throw new IdNotFoundException("Id Not Found");
		}
		
		return transactionRepo.getTransactions(farmerId, mandiPincode);
	}


	
	@Override
	public boolean checkForTransactionId(int transactionId) throws FarmerTransactionServiceException 
	{
		FarmerTransaction farmerTrans = new FarmerTransaction();
		try
		{
			farmerTrans = transactionRepo.findById(transactionId).orElse(null);
		}
		catch(Exception e)
		{
			throw new FarmerTransactionServiceException("Something went wrong while grabbing data.",e);
		}
		if(farmerTrans == null)
		{
			return false;
		}
		else
		{
			String cropClass = farmerTrans.getCropClass();
			if(cropClass.equals("C"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public boolean creditExtraAmount(ExtraCreditDto extraCreditDto) throws FarmerTransactionServiceException 
	{
		int transactionId = extraCreditDto.getTransactionId();
		String cropClass = extraCreditDto.getCropClass();
		FarmerTransaction farmerTransaction = new FarmerTransaction();
		try
		{
			farmerTransaction = transactionRepo.findById(transactionId).orElse(null);
		}
		catch(Exception e)
		{
			throw new FarmerTransactionServiceException("Something went wrong while grabbing data.",e);
		}
		if(farmerTransaction != null)
		{
			try
			{
				int mandiPincode = farmerTransaction.getMandi().getMandiPincode();
				double cropQty = farmerTransaction.getQuantity();
				double presentAmount = farmerTransaction.getAmount();  
				String adminId = mandiRepo.getAdminIdByMandiPincode(mandiPincode);
				String cropName = farmerTransaction.getCropName();
				int cropId = cropRepo.getCropIdByAdminIdAndCropName(adminId, cropName);
				double cropQualityPrice = cropVarietyRepo.findCropQualityPrice(cropId, cropClass).getCropQualityPrice();
				
				double extraAmount = cropQty * cropQualityPrice;
				double newAmount = presentAmount + extraAmount;
				
				farmerTransaction.setAmount(newAmount);
				farmerTransaction.setCropClass(cropClass);
			
				transactionRepo.save(farmerTransaction);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new FarmerTransactionServiceException("Something went wrong while updating data.",e);
			}
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public List<FarmerTransaction> getTransactions(int id) throws FarmersServiceException {
		List<FarmerTransaction> transactions = new ArrayList<>();
		try {
			transactions = transactionRepo.findByFarmerId(id);
		} catch (Exception e) {
			throw new FarmersServiceException(e.getMessage());
		}
		return transactions;
	}

}
