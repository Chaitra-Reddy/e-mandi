package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;
import com.mindtree.EMandi.modules.superadmin.repository.SuperAdminRepository;
import com.mindtree.EMandi.modules.superadmin.service.SuperAdminService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ContextConfiguration(classes = EMandiApplication.class)
class SuperAdminServiceTest {

	@Autowired
	private SuperAdminService sAService;

	@MockBean
	SuperAdminRepository sAdminRepo;

	@Test
	void testValidateLogin() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", "111");
		map.put("password", "sAdmin11");
		
		SuperAdmin sAdmin = new SuperAdmin();
		sAdmin.setSuperAdminId(111);
		sAdmin.setPassword("sAdmin11");
		
		doReturn(Optional.of(sAdmin)).when(sAdminRepo).findById(111);
		
		assertEquals("111", sAService.validateLogin(map));
		
	}

	@Test
	void testGetSAdmin() {
		int id = 2;
		SuperAdmin sAdmin = new SuperAdmin();
		doReturn(Optional.of(sAdmin)).when(sAdminRepo).findById(id);

		try {
			assertNotNull(sAService.getSAdmin(id));
		} catch (ServiceException e) {

		}

	}

	@Test
	void testUpdatePassword() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", "111");
		map.put("password", "sAdmin11");
		
		SuperAdmin sAdmin = new SuperAdmin();
		sAdmin.setSuperAdminId(111);
		sAdmin.setPassword("sAdmin11");
		
		doReturn(Optional.of(sAdmin)).when(sAdminRepo).findById(111);
		doReturn(sAdmin).when(sAdminRepo).save(sAdmin);
		
		assertNotNull(sAService.updatePassword(map));
	}

	@Test
	void testAddAdmin() {
		
	}

	@Test
	void testSendMail() {
		
	}

	@Test
	void testPasswordMail() {
		
	}

}
