package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.admin.repository.AdminRepository;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ActiveProfiles("cloudx")
//@ContextConfiguration(classes = EMandiApplication.class)
@Transactional
class AdminRepoTest {

	@Autowired
	AdminRepository repo;
	
	@Test
	void adminRepoSave() {
		
		Admin admin=repo.save(new Admin("33","Password","admin33@gmail.com","Odisha",new SuperAdmin(111,"superAdmin@111")));
		assertNotNull(admin);
	}
	
	@Test
	void adminRepoGet() {
		
		Admin admin=repo.getOne("33");
		assertNotNull(admin);
	}
	
	@Test
	void adminRepoGetByState() {
		
		String state="karnataka";
		List<Admin> admins=repo.findAdminByState(state);
		assertNotNull(admins);
		
	}
	
	@Test
	void findAllBuyersByAdminIdAndMandiPincode() {
		
		String state="karnataka";
		List<Buyer> buyers=repo.findAllBuyersByAdminIdAndMandiPincode("22", 560032);
		assertNotNull(buyers);
		
	}
}
