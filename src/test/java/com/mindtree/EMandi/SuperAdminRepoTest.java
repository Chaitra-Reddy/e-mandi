package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;
import com.mindtree.EMandi.modules.superadmin.repository.SuperAdminRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ActiveProfiles("cloudx")
//@ContextConfiguration(classes = EMandiApplication.class)
class SuperAdminRepoTest {

	@Autowired
	SuperAdminRepository repo;
	/*@Test
	void superAdminGet() {
		
		SuperAdmin sAdmin=repo.getOne(11);
		
		assertNotNull(sAdmin);
		
	}*/

}
