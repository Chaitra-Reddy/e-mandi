package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.common.base.Optional;
import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.admin.repository.AdminRepository;
import com.mindtree.EMandi.modules.admin.service.AdminService;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.crop.dto.CropDto;
import com.mindtree.EMandi.modules.crop.dto.CropVarietyDto;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.repository.CropRepository;
import com.mindtree.EMandi.modules.crop.repository.CropVarietyRepository;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.repository.FarmerRepository;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ContextConfiguration(classes = EMandiApplication.class)
@Transactional
class AdminServiceTest {

	@Autowired
	AdminService aService;
	@MockBean
	AdminRepository adminRepo;
	@MockBean
	CropVarietyRepository cropVRepo;
	@MockBean
	CropRepository cropRepo;

	@Test
	@DisplayName("AdminServiceChecking1")
	void adminServiceTest() throws ServiceException {
		String str = "";
		str = aService.addAdmin(
				new Admin("33", "Password", "admin33@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111")));

		assertNotNull(str);

	}

	@Test

	@DisplayName("AdminServiceChecking2")
	void adminServiceTest2() {
		Map<String, String> map = new HashMap<>();
		Admin admin = new Admin("33", "Password", "admin33@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111"));
		map.put("userId", "33");
		map.put("password", "Password"); // String str=aService.validateLogin(map);

		Mockito.when(adminRepo.findById(map.get("userId"))).thenReturn(java.util.Optional.of(admin) );
		assertNotNull(aService.validateLogin(map));

	}

	@Test

	@DisplayName("AdminServiceChecking3")
	void adminServiceGetAllAdminsTest() throws ServiceException {

		List<Admin> admins = new ArrayList<>();

		admins.add(new Admin("33", "Password", "admin33@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111")));
		Mockito.when(adminRepo.findAll()).thenReturn(admins);

		assertNotNull(aService.getAllAdmins());

	}

	@Test

	@DisplayName("AdminServiceChecking4")
	void adminServiceGetAdminTest() throws ServiceException {

		Admin admin = new Admin("33", "Password", "admin33@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111"));
		Mockito.when(adminRepo.findById("33")).thenReturn(java.util.Optional.of(admin));

		String id = admin.getAdminId();

		assertNotNull(aService.getAdmin(id));
	}

	@Test
	@DisplayName("AdminServiceChecking5")
	void adminServiceUpdatePasswordTest() throws ServiceException {

		Map<String, String> map = new HashMap<>();
		map.put("userId", "33");
		map.put("password", "pass");
		Admin admin = new Admin("33", "Password", "admin33@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111"));
		Mockito.when(adminRepo.findById("33")).thenReturn(java.util.Optional.of(admin));

		Admin updated = aService.updatePassword(map);

		assertEquals(updated.getPassword(), "pass");

	}

	@Test
	@DisplayName("AdminServiceChecking6")
	void adminServicegetFarmersByAdminIdAndMandiPincodeTest() throws ServiceException {

		List<Farmer> farmers = new ArrayList<>();
		farmers.add(
				new Farmer(1, "robin", "SBI", "2346789956", "SBIN000987", "556709873456", "password", "9090876543"));
		String id = "22";
		int pin = 569876;

		Mockito.when(adminRepo.findAllFarmersByAdminIdAndMandiPincode(id, pin)).thenReturn(farmers);

		List<Farmer> farmersFetched = aService.getFarmersByAdminIdAndMandiPincode(id, pin);

		assertNotNull(farmersFetched);
	}

	@Test
	@DisplayName("AdminServiceChecking7")
	void adminServicegetBuyersByAdminIdAndMandiPincodeTest() throws ServiceException {

		List<Buyer> buyers = new ArrayList<>();
		buyers.add(new Buyer(22, "robin", "password", "2346789956"));
		String adminId = "22";
		int pin = 569876;

		Mockito.when(adminRepo.findAllBuyersByAdminIdAndMandiPincode(adminId, pin)).thenReturn(buyers);

		List<Buyer> buyersFetched = aService.getBuyersByAdminIdAndMandiPincode(adminId, pin);

		assertNotNull(buyersFetched);
	}

	@Test
	@DisplayName("AdminServiceChecking8")
	void adminServicepasswordMailTest() throws ServiceException {

		Admin admin = new Admin("33", "Password", "admin33@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111"));

		Mockito.when(adminRepo.findById(admin.getAdminId())).thenReturn(java.util.Optional.of(admin));

		Map<String, String> map = new HashMap<>();
		map.put("userId", "33");
		map.put("password", "Password");

		String str = aService.passwordMail(map);

		assertEquals(str, "sent mail");
	}

	@Test
	@DisplayName("AdminServiceChecking9")
	void adminServicegetAdminByStateTest() throws ServiceException {

		List<Admin> admins = new ArrayList<>();
		admins.add(new Admin("33", "Password", "admin33@gmail.com", "odisha", new SuperAdmin(111, "superAdmin@111")));
		String state = "odisha";

		Mockito.when(adminRepo.findAdminByState(state)).thenReturn((admins));

		List<Admin> fetchedAdmin = aService.getAdminByState(state);

		assertNotNull(fetchedAdmin);
	}

	@Test
	@DisplayName("AdminServiceChecking10")
	void adminServiceupdateQualityPriceTest() throws ServiceException {

		CropVarietyDto crop = new CropVarietyDto("A", 200.0, 300.0, 1);
		CropVariety cVariety = new CropVariety(1, "A", 200.0, 300.0);

		Mockito.when(cropVRepo.findCropQualityPrice(crop.getCropId(), crop.getCropClass())).thenReturn(cVariety);
		Mockito.when(cropVRepo.save(cVariety)).thenReturn(cVariety);

		String str = aService.updateQualityPrice(crop);

		assertEquals("updated", str);
	}

	@Test
	@DisplayName("AdminServiceChecking11")
	void adminServiceupdateAdminTest() throws ServiceException {

		Admin admin = new Admin("33", "Password", "admin33@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111"));

		Mockito.when(adminRepo.findById(admin.getAdminId())).thenReturn(java.util.Optional.of(admin));

		Mockito.when(adminRepo.save(admin)).thenReturn(admin);

		Mockito.when(adminRepo.existsById(admin.getAdminId())).thenReturn(true);

		Admin newAdmin = new Admin("33", "pass", "admin34@gmail.com", "Odisha", new SuperAdmin(111, "superAdmin@111"));

		Admin updatedAdmin = aService.updateAdmin(newAdmin);
		assertNotEquals(admin.getPassword(), updatedAdmin.getPassword());
	}

	@Test
	@DisplayName("AdminServiceChecking12")
	void adminServicegetCropVarietyDtoTest() throws ServiceException {

		Crop crop = new Crop(1, "rice", 50);
		CropVariety cropVar = new CropVariety(1, "A", 200.0, 300.0);
		Map<String, String> map = new HashMap<>();
		map.put("cropName", "rice");
		map.put("adminId", "22");
		map.put("cropClass", "A");
		Mockito.when(cropRepo.findMSP(map.get("cropName"), map.get("adminId"))).thenReturn(crop);

		Mockito.when(cropVRepo.findCropQualityPrice(crop.getCropId(), map.get("cropClass"))).thenReturn(cropVar);

		assertNotNull(aService.getCropVarietyDto(map));

	}

}
