package com.mindtree.EMandi;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.admin.repository.AdminRepository;
import com.mindtree.EMandi.modules.admin.service.AdminService;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;
import com.mindtree.EMandi.modules.clerk.service.ClerkService;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.service.CropService;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
public class AdminControllerTest 
{
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdminService service;
	
	@MockBean
	private CropService cropService;
	
	@MockBean
	private ClerkService clerkService;
	
	@MockBean
	private AdminRepository repo;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testValidateAdmin() throws ServiceException, Exception
	{
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin = new Admin("11", "admin@12", "iqhra1998nus@gmail.com", "bihar", sAdmin);
		doReturn(admin).when(service).getAdmin("11");
		
		mockMvc.perform(get("/admin/validate/{id}","11"))
		.andExpect(status().isOk())
		.andExpect(header().string("desc", "credentials validation"))
		.andExpect(header().string("userType", "admin"))
		.andExpect(content().string(""+"11"));
	}
	
	@Test
	public void testGetAllAdmins() throws ServiceException, Exception
	{
		List<Admin> admins = new ArrayList<Admin>();
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin1 = new Admin("11", "admin@11", "admin11@gmail.com", "bihar", sAdmin);
		Admin admin2 = new Admin("22", "admin@22", "admin22@gmail.com", "maharashtra", sAdmin);
		
		admins.add(admin1);
		admins.add(admin2);
		
		doReturn(admins).when(service).getAllAdmins();
		
		mockMvc.perform(get("/admin/getAllAdmins"))
		.andExpect(status().isOk())
		.andExpect(header().string("Description", "Getting all admins success"));
	}
	
	@Test
	public void testValidateLogin() throws Exception
	{
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin = new Admin("11", "admin@12", "iqhra1998nus@gmail.com", "bihar", sAdmin);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("userId", "11");
		map.put("password", "admin@12");
		
		doReturn(admin.getAdminId()).when(service).validateLogin(map);
		
		String json = mapper.writeValueAsString(map);
		
		mockMvc.perform(post("/admin/login").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
	              .content(json).accept(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void testGetCropByAdminId() throws ServiceException,Exception
	{
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin = new Admin("11", "admin@12", "iqhra1998nus@gmail.com", "bihar", sAdmin);
		
		List<Crop> crops = new ArrayList<Crop>();
		
		Crop c1 = new Crop(1,"wheat",10);
		c1.setAdmin(admin);
		Crop c2 = new Crop(2,"rice",15);
		c2.setAdmin(admin);
		
		crops.add(c1);
		crops.add(c2);
		
		doReturn(crops).when(cropService).findCropByAdminId(admin.getAdminId());
		
		mockMvc.perform(get("/admin/getAllCrops/{adminId}","11"))
				.andExpect(status().isOk())
				.andExpect(header().string("Description", "Getting all crops"));
	}
	
	@Test
	public void testGetClerk() throws Exception
	{
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin = new Admin("11", "admin@12", "iqhra1998nus@gmail.com", "bihar", sAdmin);
		Mandi m1 = new Mandi(101010,100);
		m1.setAdmin(admin);
		Clerk c1 = new Clerk("10","nusrath","anjum15","7259258255","abc@gmail.com");
		c1.setMandi(m1);
		
		doReturn(c1).when(clerkService).getClerk(c1.getClerkId());
		
		mockMvc.perform(get("/admin/getClerkById/{clerkId}","10"))
		.andExpect(status().isOk())
		.andExpect(header().string("Description", "Getting all farmers"));
	}
}
