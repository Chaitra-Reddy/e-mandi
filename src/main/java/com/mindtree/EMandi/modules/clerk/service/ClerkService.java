package com.mindtree.EMandi.modules.clerk.service;

import java.util.List;
import java.util.Map;

import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.buyer.dto.BuyerRequestDto;
import com.mindtree.EMandi.modules.clerk.dto.ClerkCropDto;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;

public interface ClerkService {

	public String validateLogin(Map<String, String> map);

	public Clerk getClerk(String id) throws ServiceException;

	public Clerk updatePassword(Map<String, String> map) throws ServiceException;
	
	public double getTotalPrice(ClerkCropDto clerkCropDto[]);
	
	public boolean buyCrops(ClerkCropDto clerkCropDto[]) throws ServiceException;
	
	public double getStorageByClerkId(String clerkId) throws ServiceException;
	
	public double getSingleCropPrice(ClerkCropDto clerkCropDto) throws ServiceException;
	
	public boolean validateFarmerId(int farmerId) throws ServiceException;
	
	public String passwordMail(Map<String, String> map) throws ServiceException;
	
	public List<Integer> getFarmerIds(String clerkId) throws IdNotFoundException;
	
	public List<Clerk> getAllClerks(List<String> mandi) throws ServiceException;
	
	Clerk updateClerk(Clerk cler) throws ServiceException;
	
	public List<Integer> getBuyerIds(String clerkId) throws IdNotFoundException;
	
	public boolean updateClerkProfile(Clerk clerk) throws ServiceException;
	
	public List<BuyerRequestDto> getRequestList(String clerkId) throws ServiceException;
	
	public String requestAccept(String requestId) throws ServiceException;
	
	public String requestReject(String requestId) throws ServiceException;
}
