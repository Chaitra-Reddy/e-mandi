package com.mindtree.EMandi;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
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
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.mandi.service.MandiService;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
public class MandiControllerTest 
{
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MandiService service;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testAddMandi() throws Exception
	{
		Mandi mandi = new Mandi(101010,100);
		
		doReturn(mandi).when(service).addMandi(mandi);
		
		String json = mapper.writeValueAsString(mandi);
		
		mockMvc.perform(post("/mandi/add").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
              .content(json).accept(MediaType.APPLICATION_JSON));
		
		Assertions.assertEquals(mandi, service.addMandi(mandi));
	}
	
	@Test
	public void testGetMandiByAdminId() throws Exception
	{
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin = new Admin("11", "admin@12", "iqhra1998nus@gmail.com", "bihar", sAdmin);
		
		Set<Mandi> mandis = new HashSet<Mandi>();
		Mandi m1 = new Mandi(101010,100);
		m1.setAdmin(admin);
		Mandi m2 = new Mandi(202020,200);
		m1.setAdmin(admin);
		
		mandis.add(m1);
		mandis.add(m2);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("adminId", "11");
		
		doReturn(mandis).when(service).getMandiByAdminId("11");
		
		mockMvc.perform(post("/mandi/getMandiByAdminId").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
	              .content("11").accept(MediaType.APPLICATION_JSON));
	}
}
