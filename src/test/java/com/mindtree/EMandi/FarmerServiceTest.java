package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.FarmerException;
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.FarmerTransactionServiceException;
import com.mindtree.EMandi.exception.service.FarmersServiceException;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.repository.CropRepository;
import com.mindtree.EMandi.modules.crop.repository.CropVarietyRepository;
import com.mindtree.EMandi.modules.farmer.dto.ExtraCreditDto;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;
import com.mindtree.EMandi.modules.farmer.repository.FarmerRepository;
import com.mindtree.EMandi.modules.farmer.repository.FarmerTransactionRepository;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ContextConfiguration(classes = EMandiApplication.class)
class FarmerServiceTest {

	@Autowired
	FarmerService fService;
	@MockBean
	FarmerRepository farmerRepo;

	@MockBean
	FarmerTransactionRepository fTransactionRepo;

	@MockBean
	CropRepository cropRepo;

	@MockBean
	CropVarietyRepository cropVRepo;

	@Mock
	FarmerTransaction farmerTransaction;

	@MockBean
	MandiRepository mandiRepo;

	@Test
	@DisplayName("FarmerServiceChecking")
	void getFarmerTest() {
		Farmer farmer = new Farmer(1, "259891957247", "55159521645", "pune", "SBI", "robin", "SBIN0001561",
				"8546121045", "v", "cityName");

		Mockito.when(farmerRepo.findById(1)).thenReturn(Optional.of(farmer));
		try {
			assertNotNull(fService.getFarmer(1));
		} catch (FarmerException e) {

		}

	}

	@Test
	@DisplayName("FarmerServiceChecking")
	void validateQATest() {
		Farmer farmer = new Farmer(1, "robin", "SBI", "259891957247", "SBIN0001561", "55159521645", "v", "8546121045",
				"cityName", "pune");
		Map<String, String> map = new HashMap<>();
		map.put("userId", "1");
		map.put("sQ", "cityName");
		map.put("answer", "pune");

		Map<String, String> map2 = new HashMap<>();
		map2.put("userId", "1");
		map2.put("sQ", "cityNae");
		map2.put("answer", "pue");
		// Mockito.when(farmerRepo.findById(1)).thenReturn();
		doReturn(Optional.of(farmer)).when(farmerRepo).findById(Integer.parseInt(map.get("userId")));
		try {

			assertEquals("yes", fService.validateQA(map));
			assertNull(fService.validateQA(map2));
		} catch (FarmerException e) {

		}

	}

	@Test
	@DisplayName("FarmerProfileUpdate")
	void updateProfileTest() {
		Farmer farmer = new Farmer(1, "robin", "SBI", "259891957247", "SBIN0001561", "55159521645", "v", "8546121045",
				"cityName", "pune");
		Farmer farmer2 = new Farmer(1, "robin", "SBI", "259891957247", "SBIN0001561", "55159521645", "v", "8546121045",
				"cityName", "pune");

		Farmer farmer3 = new Farmer(7, "robi", "SB", "259891957248", "SBIN4001561", "55359521645", "vi", "9546121045",
				"cityNae", "puri");
		doReturn(Optional.of(farmer)).when(farmerRepo).save(farmer);

		try {
			assertNull(fService.updateFarmerProfile(farmer2, farmer));
			assertNotNull(fService.updateFarmerProfile(farmer3, farmer));
		} catch (FarmersServiceException e) {
			// TODO Auto-generated catch block

		}
	}

	@Test
	@DisplayName("FarmerProfileUpdate")
	void checkForTransactionIdTest() {
		Date date = new Date();
		FarmerTransaction farmerTrans = new FarmerTransaction(99, "wheat", "C", 10, 1510, date);
		FarmerTransaction farmerTrans2 = new FarmerTransaction(91, "wheat", "A", 10, 1510, date);
		doReturn(Optional.of(farmerTrans)).when(fTransactionRepo).findById(99);
		doReturn(Optional.of(farmerTrans2)).when(fTransactionRepo).findById(91);
		try {
			assertEquals(true, fService.checkForTransactionId(99));
			assertEquals(false, fService.checkForTransactionId(9));
			assertEquals(false, fService.checkForTransactionId(91));
		} catch (FarmerTransactionServiceException e) {
		}

	}

	@Test
	@DisplayName("Farmerlogin")
	void validateLoginTest() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", "11");
		map.put("password", "12sdwe");

		Farmer f = new Farmer();
		f.setFarmerId(11);
		f.setPassword("12sdwe");

		Map<String, String> map2 = new HashMap<>();
		map2.put("userId", "1");
		map2.put("password", "12sdwe");

		Map<String, String> map3 = new HashMap<>();
		map3.put("userId", "11");
		map3.put("password", "12sdw");

		doReturn(Optional.of(f)).when(farmerRepo).findById(11);
		// doReturn(Optional.of(f)).when(farmerRepo).findById(1);

		assertEquals("11", fService.validateLogin(map));

		assertNull(fService.validateLogin(map2));

		assertNull(fService.validateLogin(map3));

	}

	@Test
	@DisplayName("update Password")
	void updatePasswordTest() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", "11");
		map.put("password", "12sdwe");

		Farmer f = new Farmer();
		f.setFarmerId(11);
		f.setPassword("12sdwe");

		doReturn(Optional.of(f)).when(farmerRepo).findById(11);

		doReturn(f).when(farmerRepo).save(f);

		try {
			assertNotNull(fService.updatePassword(map));
		} catch (FarmerException e) {

		}

	}

	@Test
	@DisplayName("create farmer")
	void createFarmerTest() {
		Farmer f = new Farmer();
		f.setAadharNumber("63246342374");

		Farmer f1 = new Farmer();
		f1.setAadharNumber("63246342307");

		List<Farmer> farmerList = new ArrayList<>();
		farmerList.add(f);
		// Mockito.when(farmerRepo.findAll()).thenReturn(Optional.of(farmerList));

		doReturn(farmerList).when(farmerRepo).findAll();
		doReturn(f1).when(farmerRepo).save(f1);
		try {
			assertNull(fService.createFarmer(f));
			assertNotNull(fService.createFarmer(f1));
		} catch (FarmersServiceException e) {

		}

	}

	@Test
	@DisplayName("get Farmer By mandi pincode")
	void getFarmerByMandiPincodeTest() {

		Farmer f1 = new Farmer();
		f1.setAadharNumber("63246342307");

		List<Farmer> farmerList = new ArrayList<>();

		farmerList.add(f1);

		doReturn(farmerList).when(fTransactionRepo).findByMandiPincode(1);

		try {
			assertNotNull(fService.findByMandiPincode(1));
		} catch (ServiceException e) {
		}

	}

	@Test
	@DisplayName("credit extra amount")
	void creditExtraAmount() {
		Mandi m = new Mandi();
		m.setMandiPincode(12223);
		ExtraCreditDto extraDto = new ExtraCreditDto();
		extraDto.setTransactionId(12);
		extraDto.setCropClass("A");
		FarmerTransaction farmerTransaction = new FarmerTransaction();
		farmerTransaction.setTransactionId(12);
		farmerTransaction.setQuantity(20);
		farmerTransaction.setAmount(2364);
		farmerTransaction.setCropName("wheat");
		farmerTransaction.setMandi(m);

		doReturn(Optional.of(farmerTransaction)).when(fTransactionRepo).findById(12);

		doReturn(1).when(cropRepo).getCropIdByAdminIdAndCropName("11", "wheat");
		doReturn("11").when(mandiRepo).getAdminIdByMandiPincode(12223);

		CropVariety cv = new CropVariety();
		cv.setCropClass("A");
		cv.setCropQualityPrice(25);

		doReturn(cv).when(cropVRepo).findCropQualityPrice(1, "A");

		doReturn(farmerTransaction).when(fTransactionRepo).save(farmerTransaction);

		try {
			assertEquals(true, fService.creditExtraAmount(extraDto));
		} catch (FarmerTransactionServiceException e) {

		}

	}

	@Test
	@DisplayName("get farmer transactions")
	void getTransactionsTest() {
		List<FarmerTransaction> transactions = new ArrayList<>();
		doReturn(transactions).when(fTransactionRepo).findByFarmerId(1);

		try {
			assertNotNull(fService.getTransactions(1));
		} catch (FarmersServiceException e) {

		}

	}

	@Test
	@DisplayName("get farmer transactions")
	void getTransactionTest() {
		String clerkId = "12";
		int farmerId = 1;
		int mandiPincode = 12322;
		List<FarmerTransaction> transactions = new ArrayList<>();
		FarmerTransaction transaction = new FarmerTransaction();
		transaction.setTransactionId(2);
		transactions.add(transaction);
		doReturn(mandiPincode).when(mandiRepo).getMandiPincode(clerkId);
		doReturn(transactions).when(fTransactionRepo).getTransactions(farmerId, mandiPincode);

		try {
			assertNotNull(fService.getTransactions(clerkId, farmerId));
		} catch (IdNotFoundException e) {
		}
	}

}
