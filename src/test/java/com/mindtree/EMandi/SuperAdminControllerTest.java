package com.mindtree.EMandi;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;
import com.mindtree.EMandi.modules.superadmin.service.SuperAdminService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
class SuperAdminControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SuperAdminService sAService;
	
	
	

	@Test
	void testSayHello() {
	}

	@Test
	@DisplayName("validate super admin")
	void testValidateSAdmin() {
		SuperAdmin sAdmin = new SuperAdmin();
		
		try {
			doReturn(sAdmin).when(sAService).getSAdmin(1);
		} catch (ServiceException e) {
			
		}
		
		try {
			mockMvc.perform(get("/sAdmin/validate/{id}",1))
			.andExpect(status().isOk())
			.andExpect(header().string("desc", "credentials validation"))
			.andExpect(header().string("userType", "SuperAdmin"))
			.andExpect(content().string(""+1));
		} catch (Exception e) {
			
		}
		
	}

	@Test
	void testResetPassword() {
		
	}

	@Test
	void testAddAdmin() {
		
	}

	@Test
	void testMailPassword() {
		
	}

}
