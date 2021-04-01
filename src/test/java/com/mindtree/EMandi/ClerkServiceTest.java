package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.buyer.converter.BuyerRequestConverter;
import com.mindtree.EMandi.modules.buyer.dto.BuyerRequestDto;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;
import com.mindtree.EMandi.modules.buyer.repository.BuyerRequestRepository;
import com.mindtree.EMandi.modules.buyer.repository.BuyerTransactionRepository;
import com.mindtree.EMandi.modules.clerk.dto.ClerkCropDto;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;
import com.mindtree.EMandi.modules.clerk.repository.ClerkRepository;
import com.mindtree.EMandi.modules.clerk.service.ClerkService;
import com.mindtree.EMandi.modules.crop.dto.CropNameQtyDto;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.repository.CropRepository;
import com.mindtree.EMandi.modules.crop.repository.CropVarietyRepository;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.repository.FarmerTransactionRepository;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ContextConfiguration(classes = EMandiApplication.class)
class ClerkServiceTest {

	@Autowired
	ClerkService clerkService;

	@MockBean
	FarmerService farmerService;

	@MockBean
	ClerkRepository clerkRepo;

	@MockBean
	MandiRepository mandiRepo;

	@MockBean
	CropRepository cropRepo;

	@MockBean
	CropVarietyRepository cVRepo;

	@MockBean
	FarmerTransactionRepository farmerTransactionRepo;

	@MockBean
	BuyerRequestRepository requestRepo;

	@MockBean
	BuyerRequestConverter bRConverter;

	@MockBean
	BuyerTransactionRepository buyerTransactionRepo;

	@Test
	@DisplayName("ClerkServiceChecking1")
	void getClerktest() {

		Clerk clerk = new Clerk("11", "Aashish", "aashu123", "9779425378", "sinhaaashish98@gmail.com");

		Mockito.when(clerkRepo.findById("11")).thenReturn(Optional.of(clerk));

		try {
			assertNotNull(clerkService.getClerk("11"));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block

		}
	}

	@Test
	@DisplayName("ClerkServiceChecking2")
	void getTotalPricetest() {
		Crop c = new Crop();
		c.setCropMSP(200);

		Admin admin = new Admin();
		admin.setAdminId("101");
		ClerkCropDto[] cropDto = new ClerkCropDto[2];

		cropDto[0] = new ClerkCropDto("11", 1, "wheat", 15);
		cropDto[1] = new ClerkCropDto("12", 2, "rice", 16);

		CropNameQtyDto cname = new CropNameQtyDto();
		cname.setCropName("wheat");

		Mockito.when(mandiRepo.findAdminIdByClerkId("11")).thenReturn("101");

		Mockito.when(cropRepo.findMSP("wheat", "101")).thenReturn(c);

		assertNotNull(clerkService.getTotalPrice(cropDto));
	}

	@Test
	@DisplayName("ClerkServiceChecking3")
	void buyCropstest() {
		Mandi m = new Mandi();
		m.setMandiPincode(800006);

		Admin admin = new Admin();
		admin.setAdminId("101");

		CropNameQtyDto cname = new CropNameQtyDto();
		cname.setCropName("wheat");

		Crop c = new Crop();
		c.setCropMSP(200);

		ClerkCropDto[] cropDto = new ClerkCropDto[2];

		cropDto[0] = new ClerkCropDto("11", 1, "wheat", 15);
		cropDto[1] = new ClerkCropDto("12", 2, "rice", 16);

		Mockito.when(clerkRepo.findMandiPincodeByClerkId("11")).thenReturn(800006);
		Mockito.when(mandiRepo.findAdminIdByClerkId("11")).thenReturn("101");
		Mockito.when(cropRepo.findMSP("wheat", "101")).thenReturn(c);

		try {
			assertNotNull(clerkService.buyCrops(cropDto));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block

		}
	}

	@Test
	@DisplayName("ClerkServiceChecking4")
	void getStorageByClerkIdtest() throws ServiceException {

		Clerk clerk = new Clerk();
		clerk.setClerkId("100");

		Mandi mandi = new Mandi();
		mandi.setMandiPincode(800006);
		mandi.setStorage(800);

		Mockito.when(clerkRepo.findMandiPincodeByClerkId("100")).thenReturn(800006);
		Mockito.when(mandiRepo.findById(800006)).thenReturn(Optional.of(mandi));

		assertNotNull(clerkService.getStorageByClerkId("100"));

	}

	@Test
	@DisplayName("ClerkServiceChecking5")
	void getSingleCropPricetest() throws ServiceException {

		ClerkCropDto clerkDto = new ClerkCropDto();
		clerkDto.setClerkId("100");
		clerkDto.setCropName("wheat");

		Crop c = new Crop();
		c.setCropMSP(200);

		Admin admin = new Admin();
		admin.setAdminId("101");

		Mockito.when(mandiRepo.findAdminIdByClerkId("100")).thenReturn("101");
		Mockito.when(cropRepo.findMSP("wheat", "101")).thenReturn(c);

		assertNotNull(clerkService.getSingleCropPrice(clerkDto));
	}

	@Test
	@DisplayName("ClerkServiceChecking6")
	void getFarmerIdstest() throws ServiceException, IdNotFoundException {
		String clerkId = "100";
		Mandi mandi = new Mandi();
		mandi.setMandiPincode(800006);

		List<Integer> l = new ArrayList<>();
		l.add(100);
		l.add(101);

		Mockito.when(mandiRepo.getMandiPincode("100")).thenReturn(800006);
		Mockito.when(farmerTransactionRepo.getFarmerIds(800006)).thenReturn(l);

		assertNotNull(clerkService.getFarmerIds("100"));
	}

	@Test
	@DisplayName("ClerkServiceChecking7")
	void requestRejecttest() throws ServiceException {

		BuyerRequest buyerReq = new BuyerRequest(100, null, 120, null, null, null);

		Mockito.when(requestRepo.findById(100)).thenReturn(Optional.of(buyerReq));

		assertNotNull(clerkService.requestReject("100"));

	}

	@Test
	@DisplayName("ClerkServiceChecking8")
	void updateClerkProfiletest() throws ServiceException {

		Clerk clerk = new Clerk("100", "Aashish", "aashu1234", "9779425378", "sinhaaashish98@gmail.com");
		Mockito.when(clerkRepo.findById("100")).thenReturn(Optional.of(clerk));
		Mockito.when(clerkRepo.save(clerk)).thenReturn(clerk);
		assertEquals(true, clerkService.updateClerkProfile(clerk));

	}

	@Test
	@DisplayName("ClerkServiceChecking9")
	void getBuyerIdstest() throws IdNotFoundException {
		String clerkId = "100";

		List<Integer> l = new ArrayList<>();
		l.add(100);
		l.add(101);

		Mandi mandi = new Mandi();
		mandi.setMandiPincode(800006);

		Mockito.when(mandiRepo.getMandiPincode("100")).thenReturn(800006);
		Mockito.when(buyerTransactionRepo.getBuyerIds(800006)).thenReturn(l);

		assertNotNull(clerkService.getBuyerIds("100"));

	}

	@Test
	@DisplayName("ClerkServiceChecking10")
	void updateClerktest() throws ServiceException {
		Clerk clerk = new Clerk("100", "Aashish", "aashu1234", "9779425378", "sinhaaashish98@gmail.com");

		Mockito.when(clerkRepo.save(clerk)).thenReturn(clerk);

		assertNotNull(clerkService.updateClerk(clerk));

	}

	@Test
	@DisplayName("get request Lists")
	void getRequestListtest() throws ServiceException {
		Mandi mandi = new Mandi();
		mandi.setMandiPincode(800006);

		List<BuyerRequest> requests = new ArrayList<>();
		BuyerRequest b1 = new BuyerRequest(1, null, 0, mandi, null, null);
		BuyerRequest b2 = new BuyerRequest(2, null, 0, mandi, null, null);
		requests.add(b1);
		requests.add(b2);
		Clerk c = new Clerk();
		c.setClerkId("100");
		c.setMandi(mandi);
		doReturn(Optional.of(c)).when(clerkRepo).findById("100");
		Mockito.when(requestRepo.getReuquestListByMandiPincode(800006)).thenReturn(requests);

		BuyerRequestDto brDto = new BuyerRequestDto();
		brDto.setCropClass("A");
		List<BuyerRequestDto> requestsDto = new ArrayList<>();
		requestsDto.add(brDto);

		doReturn(requestsDto).when(bRConverter).entityToDtoForList(requests);

		assertNotNull(clerkService.getRequestList("100"));

	}

	@Test
	@DisplayName("ClerkServiceChecking11")
	void validateLoginTest() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", "11");
		map.put("password", "12sdwe");

		Clerk c = new Clerk();
		c.setClerkId("11");
		c.setPassword("12sdwe");

		Map<String, String> map2 = new HashMap<>();
		map2.put("userId", "1");
		map2.put("password", "12sdwe");

		Map<String, String> map3 = new HashMap<>();
		map3.put("userId", "11");
		map3.put("password", "12sdw");

		doReturn(Optional.of(c)).when(clerkRepo).findById("11");

		assertEquals("11", clerkService.validateLogin(map));

		assertNull(clerkService.validateLogin(map2));

		assertNull(clerkService.validateLogin(map3));

	}

	@Test
	@DisplayName("Update Password")
	void updatePasswordTest() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", "11");
		map.put("password", "gfhg");

		Clerk c = new Clerk();

		doReturn(Optional.of(c)).when(clerkRepo).findById("11");
		doReturn(c).when(clerkRepo).save(c);

		try {
			assertNotNull(clerkService.updatePassword(map));
		} catch (ServiceException e) {

		}

	}

	@Test
	@DisplayName("Update Password")
	void ValidateFarmerTestId() {
		Farmer f = new Farmer();
		Farmer f2 = null;
		int id = 1;
		try {
			doReturn(f).when(farmerService).getFarmer(id);
			doReturn(f2).when(farmerService).getFarmer(2);
			assertEquals(true, clerkService.validateFarmerId(id));
			assertEquals(false, clerkService.validateFarmerId(2));
		} catch (Exception e) {

		}

	}

	@Test
	@DisplayName("Request accept")
	void acceptRequestTest() {
		String requestId = "1";
		BuyerRequest bReq = new BuyerRequest();
		bReq.setRequestId(12236);
		bReq.setCropName("Wheat");
		bReq.setCropClass("A");
		bReq.setQuantity(12);
		Mandi m = new Mandi();
		Admin a = new Admin();
		a.setAdminId("11");
		m.setAdmin(a);
		bReq.setMandi(m);
		Buyer b = new Buyer();
		bReq.setBuyer(b);
		CropVariety cV = new CropVariety();
		cV.setBuyerCropPrice(40);
		doReturn(Optional.of(bReq)).when(requestRepo).findById(1);
		doReturn(1).when(cropRepo).getCropIdByAdminIdAndCropName("11", "Wheat");
		doReturn(cV).when(cVRepo).getBuyerCropPrice(1, "A");
		BuyerTransaction bTransaction = new BuyerTransaction();
		doReturn(bTransaction).when(buyerTransactionRepo).save(bTransaction);
		doNothing().when(requestRepo).delete(bReq);
		try {
			assertNull(clerkService.requestAccept(requestId));
		} catch (ServiceException e) {

		}

	}

}
