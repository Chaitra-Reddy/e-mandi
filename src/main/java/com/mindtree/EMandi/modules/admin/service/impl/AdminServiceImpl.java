package com.mindtree.EMandi.modules.admin.service.impl;

import java.nio.charset.StandardCharsets;
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

import com.mindtree.EMandi.exception.InvalidIdException;
import com.mindtree.EMandi.exception.ResourceNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.admin.repository.AdminRepository;
import com.mindtree.EMandi.modules.admin.service.AdminService;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.crop.converter.CropVarietyConverter;
import com.mindtree.EMandi.modules.crop.dto.CropVarietyDto;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.repository.CropRepository;
import com.mindtree.EMandi.modules.crop.repository.CropVarietyRepository;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.superadmin.service.SuperAdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepo;
	@Autowired
	SpringTemplateEngine tempEngine;
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private CropVarietyRepository cropVRepo;
	@Autowired
	private CropRepository cropRepo;
	@Autowired
	private CropVarietyConverter cropVarietyConverter;
	@Autowired
	private SuperAdminService sAdminService;

	@Override
	public String validateLogin(Map<String, String> map) {
		Admin admin = adminRepo.findById(map.get("userId")).orElse(null);
		if (admin != null)
			if (admin.getPassword().equals(map.get("password")))
				return map.get("userId");
			else
				return null;
		return null;
	}

	@Override
	public String addAdmin(Admin admin) throws ServiceException {
		try {
			adminRepo.save(admin);
		} catch (Exception e) {
			throw new ServiceException("Some exception occured while adding data to DB.", e);
		}
		return "Successfully added admin";
	}

	@Override
	public List<Admin> getAllAdmins() throws ServiceException {
		List<Admin> admins = null;
		try {
			admins = adminRepo.findAll();
			if (admins.isEmpty()) {
				throw new ResourceNotFoundException();
			}
		} catch (ResourceNotFoundException e) {
			throw new ServiceException("No data found.", e);
		} catch (Exception e) {
			throw new ServiceException("Some exception occured while grabbing data from DB.", e);
		}
		return admins;
	}

	@Override
	public Admin getAdmin(String id) throws ServiceException {
		Admin admin;
		try {
			admin = adminRepo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new ServiceException("No data found for that id", e);
		}

		return admin;
	}

	@Override
	public Admin updatePassword(Map<String, String> map) throws ServiceException {
		String id = map.get("userId");
		Admin admin;
		try {
			admin = adminRepo.findById(id).get();
			admin.setPassword(map.get("password"));
			adminRepo.save(admin);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("passwords couldnt be updated");
		}
		return admin;

	}

	@Override
	public List<Farmer> getFarmersByAdminIdAndMandiPincode(String adminId, int mandiPincode) throws ServiceException {
		List<Farmer> farmers = null;
		try {
			farmers = adminRepo.findAllFarmersByAdminIdAndMandiPincode(adminId, mandiPincode);
			if (farmers.isEmpty()) {
				throw new ResourceNotFoundException();
			}
		} catch (ResourceNotFoundException e) {
			throw new ServiceException("No data found.", e);
		} catch (Exception e) {
			throw new ServiceException("Some exception occured while grabbing data from DB.", e);
		}
		return farmers;
	}

	@Override
	public List<Buyer> getBuyersByAdminIdAndMandiPincode(String adminId, int mandiPincode) throws ServiceException {
		List<Buyer> buyers = null;
		try {
			buyers = adminRepo.findAllBuyersByAdminIdAndMandiPincode(adminId, mandiPincode);
			if (buyers.isEmpty()) {
				throw new ResourceNotFoundException();
			}
		} catch (ResourceNotFoundException e) {
			throw new ServiceException("No data found.", e);
		} catch (Exception e) {
			throw new ServiceException("Some exception occured while grabbing data from DB.", e);
		}
		return buyers;
	}

	@Override
	public String passwordMail(Map<String, String> map) throws ServiceException {
		MimeMessage message = sender.createMimeMessage();
		try {
			Admin admin = adminRepo.findById(map.get("userId")).get();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("password", admin.getPassword());

			Context context = new Context();
			context.setVariables(model);
			String htmlPage = tempEngine.process("passwordTemp", context);
			helper.setTo(admin.getEmailId());
			helper.setText(htmlPage, true);
			helper.setSubject("Password for logging into the system");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		sender.send(message);
		return "sent mail";
	}

	@Override
	public List<Admin> getAdminByState(String state) throws ServiceException {
		List<Admin> admin=null;
		try {
			admin=adminRepo.findAdminByState(state);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("No data found for that id", e);
		}

		return admin;
	}

	@Override
	public CropVarietyDto getCropVarietyDto(Map<String, String> map) throws ServiceException {
		Crop crop = cropRepo.findMSP(map.get("cropName"), map.get("adminId"));
		CropVariety cVariety = cropVRepo.findCropQualityPrice(crop.getCropId(), map.get("cropClass"));
		CropVarietyDto cVarietyDto = cropVarietyConverter.entityToDto(cVariety);
		return cVarietyDto;
	}

	@Override
	public String updateQualityPrice(CropVarietyDto cropVarietyDto) throws ServiceException {
		System.out.println(cropVarietyDto.getCropId());
		CropVariety cVariety = cropVRepo.findCropQualityPrice(cropVarietyDto.getCropId(),
				cropVarietyDto.getCropClass());
		cVariety.setCropQualityPrice(cropVarietyDto.getCropQualityPrice());
		try {
			cropVRepo.save(cVariety);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Null Entity found");
		}
		return "updated";
	}

	@Override
	public Admin updateAdmin(Admin admin) throws InvalidIdException {
		// TODO Auto-generated method stub
		if(adminRepo.existsById(admin.getAdminId())) {
			admin.setState(adminRepo.findById(admin.getAdminId()).get().getState());
			admin.setsAdmin(adminRepo.findById(admin.getAdminId()).get().getsAdmin());
			adminRepo.save(admin);
			try {
				sAdminService.sendMail(admin);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			throw new InvalidIdException("Invalid Admin Id");
		}
		return admin;
	}

}
