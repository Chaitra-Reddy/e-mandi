package com.mindtree.EMandi.modules.clerk.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mindtree.EMandi.exception.FarmerException;
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ResourceNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.BuyerServiceException;
import com.mindtree.EMandi.modules.buyer.converter.BuyerRequestConverter;
import com.mindtree.EMandi.modules.buyer.dto.BuyerRequestDto;
import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;
import com.mindtree.EMandi.modules.buyer.repository.BuyerRequestRepository;
import com.mindtree.EMandi.modules.buyer.repository.BuyerTransactionRepository;
import com.mindtree.EMandi.modules.clerk.dto.ClerkCropDto;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;
import com.mindtree.EMandi.modules.clerk.repository.ClerkRepository;
import com.mindtree.EMandi.modules.clerk.service.ClerkService;
import com.mindtree.EMandi.modules.crop.dto.CropNameQtyDto;
import com.mindtree.EMandi.modules.crop.repository.CropRepository;
import com.mindtree.EMandi.modules.crop.repository.CropVarietyRepository;
import com.mindtree.EMandi.modules.farmer.converter.FarmerConverter;
import com.mindtree.EMandi.modules.farmer.dto.FarmerTransactionDto;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;
import com.mindtree.EMandi.modules.farmer.repository.FarmerTransactionRepository;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;
import com.mindtree.EMandi.modules.mandi.service.MandiService;
@Service
public class ClerkServiceImpl implements ClerkService {

	@Autowired
	ClerkRepository clerkRepo;

	@Autowired
	private MandiRepository mandiRepo;

	@Autowired
	private CropRepository cropRepo;

	@Autowired
	private BuyerRequestRepository requestRepo;

	@Autowired
	private FarmerConverter farmerConverter;

	@Autowired
	private FarmerTransactionRepository farmerTransactionRepo;

	@Autowired
	private MandiService mandiService;

	@Autowired
	private FarmerService farmerService;

	@Autowired
	SpringTemplateEngine tempEngine;

	@Autowired
	private BuyerRequestConverter requestConverter;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private BuyerTransactionRepository buyerTransactionRepo;

	@Autowired
	private CropVarietyRepository cVRepo;
	@Override
	public String validateLogin(Map<String, String> map) {
		Clerk clerk = clerkRepo.findById(map.get("userId")).orElse(null);
		if (clerk != null)
			if (clerk.getPassword().equals(map.get("password")))
				return map.get("userId");
			else
				return null;
		return null;
	}

	@Override
	public Clerk getClerk(String id) throws ServiceException {
		Clerk clerk;
		try {
			clerk = clerkRepo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new ServiceException("No data found for that id", e);
		}

		return clerk;
	}

	@Override
	public Clerk updatePassword(Map<String, String> map) throws ServiceException {
		String id = map.get("userId");
		Clerk clerk;
		try {
			clerk = clerkRepo.findById(id).get();
			clerk.setPassword(map.get("password"));
			clerkRepo.save(clerk);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Password couldnt be updated");
		}
		return clerk;
	}

	@Override
	public double getTotalPrice(ClerkCropDto clerkCropDto[]) {
		String adminId = mandiRepo.findAdminIdByClerkId(clerkCropDto[0].getClerkId());

		double total = 0;
		List<CropNameQtyDto> itemList = new ArrayList<CropNameQtyDto>();

		for (int i = 0; i < clerkCropDto.length; i++) {
			CropNameQtyDto tempCropItem = new CropNameQtyDto();
			tempCropItem.setCropName(clerkCropDto[i].getCropName());
			tempCropItem.setCropQty(clerkCropDto[i].getCropQty());
			itemList.add(tempCropItem);
		}

		for (CropNameQtyDto c : itemList) {
			double cost = 0;
			try {
				cost = (cropRepo.findMSP(c.getCropName(), adminId)).getCropMSP();
			} catch (Exception e) {
				cost = 0;
			}
			total += ((cost) * (c.getCropQty()));
		}
		return total;
	}

	@Override
	public boolean buyCrops(ClerkCropDto clerkCropDto[]) throws ServiceException {
		boolean op = false;
		double totalAmount = 0;
		double totalStorage = 0;
		try {
			int mandiPincode = clerkRepo.findMandiPincodeByClerkId(clerkCropDto[0].getClerkId());
			String adminId = mandiRepo.findAdminIdByClerkId(clerkCropDto[0].getClerkId());

			List<CropNameQtyDto> itemList = new ArrayList<CropNameQtyDto>();

			for (int i = 0; i < clerkCropDto.length; i++) {
				CropNameQtyDto tempCropItem = new CropNameQtyDto();
				tempCropItem.setCropName(clerkCropDto[i].getCropName());
				tempCropItem.setCropQty(clerkCropDto[i].getCropQty());
				itemList.add(tempCropItem);
			}

			for (CropNameQtyDto c : itemList) {
				double cost = 0;
				try {
					cost = (cropRepo.findMSP(c.getCropName(), adminId)).getCropMSP();
				} catch (Exception e) {
					cost = 0;
				}
				totalAmount += ((cost) * (c.getCropQty()));
				if (cost != 0) {
					totalStorage += c.getCropQty();
				}

				FarmerTransactionDto farmerTransDto = new FarmerTransactionDto();
				farmerTransDto.setAmount((cost) * (c.getCropQty()));
				farmerTransDto.setCropClass("C");
				farmerTransDto.setCropName(c.getCropName());
				farmerTransDto.setFarmerId(clerkCropDto[0].getFarmerId());
				farmerTransDto.setMandiPincode(mandiPincode);
				farmerTransDto.setQuantity(c.getCropQty());

				FarmerTransaction farmerTrans = new FarmerTransaction();
				farmerTrans = farmerConverter.dtoToEntityTrans(farmerTransDto);
				if (farmerTrans.getAmount() != 0) {
					farmerTransactionRepo.save(farmerTrans);
					op = true;
				}
			}

			// subtract total storage required from mandi storage
			mandiService.updateMandiStorage(mandiPincode, totalStorage);

			return op;
		} catch (Exception e) {
			throw new ServiceException("Something went wrong while buying crops.", e);
		}
	}

	@Override
	public double getStorageByClerkId(String clerkId) throws ServiceException {
		try {
			int mandiPincode = clerkRepo.findMandiPincodeByClerkId(clerkId);
			double storage = mandiRepo.findById(mandiPincode).get().getStorage();
			return storage;
		} catch (Exception e) {
			throw new ServiceException("Something went wrong while getting the storage.", e);
		}
	}

	@Override
	public double getSingleCropPrice(ClerkCropDto clerkCropDto) throws ServiceException {
		double cost = 0;
		try {
			String adminId = mandiRepo.findAdminIdByClerkId(clerkCropDto.getClerkId());
			if (adminId == null) {
				throw new ResourceNotFoundException();
			}
			cost = (cropRepo.findMSP(clerkCropDto.getCropName(), adminId)).getCropMSP();
			if (cost == 0) {
				throw new ResourceNotFoundException();
			}
		} catch (ResourceNotFoundException e) {
			throw new ServiceException("Admin ID or cost not found.", e);
		} catch (Exception e) {
			throw new ServiceException("Something went wrong while getting the single crop price.", e);
		}
		return cost;
	}

	@Override
	public boolean validateFarmerId(int farmerId) throws ServiceException {
		Farmer farmer = new Farmer();
		boolean op = false;
		try {
			farmer = farmerService.getFarmer(farmerId);
			if (farmer != null) {
				op = true;
			}
		} catch (FarmerException e) {
			e.printStackTrace();
			throw new ServiceException("Farmer not found.", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Something went wrong while validating farmer.", e);
		}
		return op;
	}

	@Override
	public String passwordMail(Map<String, String> map) throws ServiceException {
		MimeMessage message = sender.createMimeMessage();
		try {
			Clerk clerk = clerkRepo.findById(map.get("userId")).get();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("password", clerk.getPassword());

			Context context = new Context();
			context.setVariables(model);
			String htmlPage = tempEngine.process("passwordTemp", context);
			helper.setTo(clerk.getEmailId());
			helper.setText(htmlPage, true);
			helper.setSubject("Password for logging into the system");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		sender.send(message);
		return "sent mail";
	}

	@Override
	public List<Integer> getFarmerIds(String clerkId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		int mandiPincode = mandiRepo.getMandiPincode(clerkId);
		if (farmerTransactionRepo.getFarmerIds(mandiPincode).isEmpty()) {
			throw new IdNotFoundException("Id not found");
		}
		return farmerTransactionRepo.getFarmerIds(mandiPincode);

	}

	@Override
	public List<Clerk> getAllClerks(List<String> mandi) throws ServiceException {
		// TODO Auto-generated method stub
		List<Clerk> clerk = null;
		try {
			clerk = clerkRepo.findAllById(mandi);
			if (clerk.isEmpty()) {
				System.out.println("No data Found");
				throw new ResourceNotFoundException();
			}
		} catch (ResourceNotFoundException e) {
			System.out.println("No data available");
		} catch (Exception e) {
			System.out.println("No data Found");
			throw new ServiceException("Some exception occured while grabbing data from DB.", e);
		}
		return clerk;
	}

	@Override
	public Clerk updateClerk(Clerk cler) throws ServiceException {
		// TODO Auto-generated method stub
		Clerk clerk1 = null;
		clerk1 = clerkRepo.save(cler);
		return clerk1;
	}

	@Override
	public List<Integer> getBuyerIds(String clerkId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		int mandiPincode = mandiRepo.getMandiPincode(clerkId);
		if (buyerTransactionRepo.getBuyerIds(mandiPincode).isEmpty()) {
			throw new IdNotFoundException("Id not found");
		}
		

		return buyerTransactionRepo.getBuyerIds(mandiPincode);
	}

	@Override
	public boolean updateClerkProfile(Clerk clerk) throws ServiceException {
		try 
		{
			Clerk clerk1 = clerkRepo.findById(clerk.getClerkId()).orElse(null);
			if(clerk1 == null)
			{
				throw new ResourceNotFoundException();
			}
			clerkRepo.save(clerk);
			return true;
		}
		catch (ResourceNotFoundException e) 
		{
			throw new ServiceException("Error! Clerk not found.", e);
		}
		catch (Exception e) {
			throw new ServiceException("Something went wrong while updating clerk.", e);
		}
	}

	@Override
	public List<BuyerRequestDto> getRequestList(String clerkId) throws ServiceException {
		List<BuyerRequest> requests;
		try {
			requests = requestRepo
					.getReuquestListByMandiPincode(clerkRepo.findById(clerkId).get().getMandi().getMandiPincode());
		} catch (Exception e) {
			throw new ServiceException("No requests are present ");
		}
		if (requests.size() != 0) {
			List<BuyerRequestDto> requestsDto = requestConverter.entityToDtoForList(requests);
			return requestsDto;
		} else
			throw new ServiceException("No requests are present ");
	}

	@Override
	public String requestAccept(String requestId) throws ServiceException {
		BuyerTransaction transaction = new BuyerTransaction();
		Date date=new Date();
		try {
			BuyerRequest bReq = requestRepo.findById(Integer.parseInt(requestId)).get();
			int cropId=cropRepo.getCropIdByAdminIdAndCropName(bReq.getMandi().getAdmin().getAdminId(), bReq.getCropName());
			transaction.setMandi(bReq.getMandi());
			transaction.setCropName(bReq.getCropName());
			transaction.setCropClass(bReq.getCropClass());
			transaction.setQuantity(bReq.getQuantity());
			transaction.setBuyer(bReq.getBuyer());
			transaction.setDate(date);
			transaction.setAmount((bReq.getQuantity()) * (cVRepo.getBuyerCropPrice(cropId, bReq.getCropClass()).getBuyerCropPrice()));
			buyerTransactionRepo.save(transaction);
			requestRepo.delete(bReq);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("couldnt accept the request, due to some internal issues");
		}
		return null;
	}

	@Override
	public String requestReject(String requestId) throws ServiceException {
		try {
			BuyerRequest bReq = requestRepo.findById(Integer.parseInt(requestId)).get();
			requestRepo.delete(bReq);

		} catch (IllegalArgumentException e) {
			throw new ServiceException("Couldnt find, any request with that id");
		}

		return "rejected request";
	}
}
