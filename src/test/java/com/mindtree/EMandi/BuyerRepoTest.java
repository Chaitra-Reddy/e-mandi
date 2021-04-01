package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;
import com.mindtree.EMandi.modules.buyer.repository.BuyerRepository;
import com.mindtree.EMandi.modules.buyer.repository.BuyerTransactionRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ActiveProfiles("cloudx")
//@ContextConfiguration(classes = EMandiApplication.class)
@Transactional
class BuyerRepoTest {
   
	@Autowired
	BuyerRepository repo;
	@Autowired
	BuyerTransactionRepository tRepo;
	@Test
	void buyerRepoSave() {
		Buyer buyer=repo.save(new Buyer(312,"Josna","josna123","8888888888","xyz","xy")); 
		assertNotNull(buyer);
	}
	@Test
	void findById() {
		Optional<Buyer> buyer=repo.findById(1);
		assertNotNull(buyer);
	}
	
	@Test
	void buyerTransactonSave() {
		BuyerTransaction tr=tRepo.save(new BuyerTransaction(12,"C","wheat",10, null));
		assertNotNull(tr);
	}
	@Test
	void getTransaction() {
		Optional<BuyerTransaction> tr=tRepo.findById(12);
		assertNotNull(tr);
	}
	
	@Test
	void getTransactionByMandi() {
		List<BuyerTransaction> tr=tRepo.getTransactions(560037, 12);
		assertNotNull(tr);
	}
	@Test
	void getTransactionByBuyer() {
		List<Integer> tr=tRepo.getBuyerIds(560037);
		assertNotNull(tr);
	}
	@Test
	void getByBuyer() {
		List<BuyerTransaction> tr=tRepo.findByBuyerId(1);
		assertNotNull(tr);
	}
	@Test
	void find() {
		Buyer buyer=repo.getOne(1);
		assertNotNull(buyer);
	}
}