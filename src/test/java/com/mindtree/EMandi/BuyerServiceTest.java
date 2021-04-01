package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.test.web.servlet.MockMvc;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.BuyerServiceException;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;
import com.mindtree.EMandi.modules.buyer.repository.BuyerRepository;
import com.mindtree.EMandi.modules.buyer.repository.BuyerRequestRepository;
import com.mindtree.EMandi.modules.buyer.repository.BuyerTransactionRepository;
import com.mindtree.EMandi.modules.buyer.service.BuyerService;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
@Transactional
class BuyerServiceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	BuyerService bService;
	
	@MockBean
	BuyerRepository buyerRepo;
	
	@MockBean
	BuyerTransactionRepository buyerTransactionRepo;
	
	@MockBean
	private BuyerRequestRepository requestRep;
	
	@MockBean
	private MandiRepository mandiRepo;
	
	@Test
	@DisplayName("BuyerServiceChecking1")
	void BuyerServiceGetBuyertest() {
		
		Buyer buyer=new Buyer(1,"Abhilas","password","887823231","Who is your father","dad");
		//Buyer newBuyer=new Buyer(1,"Abhilas","password","887823231","Who is your mother","mom");
		int id=1;
		Mockito.when(buyerRepo.findById(1)).thenReturn(java.util.Optional.of(buyer) );
		
		//Mockito.when(buyerRepo.save(newBuyer)).thenReturn(newBuyer);
		
		//Buyer fetchedBuyer=bService.getBuyer(id);
		
		assertNotNull(bService.getBuyer(id));
		
	}
	
	@Test
	@DisplayName("BuyerServiceChecking2")
	void BuyerServiceSaveBuyertest() {
		
		Buyer buyer=new Buyer(1,"Abhilas","password","887823231","Who is your father","dad");
	
		Mockito.when(buyerRepo.save(buyer)).thenReturn(buyer) ;
		

		assertNotNull(bService.saveBuyer(buyer));
		
	}
	
	@Test
	@DisplayName("BuyerServiceChecking3")
	void BuyerServiceValidateLogintest() {
		
		Map<String,String> map=new HashMap<>();
		map.put("userId", "1");
		map.put("password", "password");
		Buyer buyer=new Buyer(1,"Abhilas","password","887823231","Who is your father","dad");
	
		Mockito.when(buyerRepo.findById(1)).thenReturn(java.util.Optional.of(buyer) );

		
		assertNotNull(bService.validateLogin(map));
		
	}

	@Test
	@DisplayName("BuyerServiceChecking4")
	void BuyerServiceUpdatePasswordtest() {
		
		Map<String,String> map=new HashMap<>();
		map.put("userId", "1");
		map.put("password", "pass");
		Buyer buyer=new Buyer(1,"Abhilas","password","887823231","Who is your father","dad");
	
		Mockito.when(buyerRepo.findById(1)).thenReturn(java.util.Optional.of(buyer) );

		Buyer updatedBuyer=bService.updatePassword(map);
		assertNotNull(bService.updatePassword(map));
		assertEquals(updatedBuyer.getPassword(), "pass");
		
	}
	
	@Test
	@DisplayName("BuyerServiceChecking5")
	void BuyerServiceValidateQAtest() {
		
		Map<String,String> map=new HashMap<>();
		map.put("userId", "1");
		map.put("sQ", "Who is your father");
		map.put("answer", "dad");
		Buyer buyer=new Buyer(1,"Abhilas","password","887823231","Who is your father","dad");
	
		Mockito.when(buyerRepo.findById(1)).thenReturn(java.util.Optional.of(buyer) );

		String result=bService.validateQA(map);
		assertEquals(result, "yes");
		
	}
	
	@Test
	@DisplayName("BuyerServiceChecking6")
	void BuyerServiceGetTransactionstest() throws BuyerServiceException {
		
		List<BuyerTransaction> tranc=new ArrayList<>();
		tranc.add( new BuyerTransaction(4000.0,"wheat","A",200.0,new Date()));
		Buyer buyer=new Buyer(1,"Abhilas","password","887823231","Who is your father","dad");
	
		Mockito.when(buyerTransactionRepo.findByBuyerId(1)).thenReturn(tranc);

		
		assertNotNull(bService.getTransactions(1));
		
	}
	
	@Test
	@DisplayName("BuyerServiceChecking7")
	void BuyerServiceSaveBuyerRequesttest() throws BuyerServiceException, ServiceException {
		
		Buyer buyer=new Buyer(1,"Abhilas","password","887823231","Who is your father","dad");
		BuyerRequest buyerReq=new BuyerRequest(1,buyer,300.0,new Mandi(234567,500.0),"wheat","A");
		
	
		Mockito.when(requestRep.save(buyerReq)).thenReturn(buyerReq);

		BuyerRequest savedBuyerReq=bService.saveBuyerRequest(buyerReq);
		
		assertNotNull(bService.saveBuyerRequest(buyerReq));
		assertEquals(savedBuyerReq.getCropName(), "wheat");
		
	}
	

	
	@Test
	@DisplayName("BuyerServiceChecking7")
	void BuyerServiceGetBuyerTransactiontest() throws BuyerServiceException, ServiceException, IdNotFoundException {
		
		Mandi mandi=new Mandi(324567,1000);
		
		
		
		List<BuyerTransaction> tranc=new ArrayList<>();
		tranc.add( new BuyerTransaction(4000.0,"wheat","A",200.0,new Date()));
	
		Mockito.when(mandiRepo.getMandiPincode("1")).thenReturn(mandi.getMandiPincode());
		
		Mockito.when(buyerTransactionRepo.getTransactions(mandi.getMandiPincode(),22)).thenReturn(tranc);

		
		List<BuyerTransaction> fetchedTranc=bService.getBuyerTransaction("1",22);
		
		
		assertNotNull(fetchedTranc);
		
		
	}
}
