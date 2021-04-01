package com.mindtree.EMandi;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.mindtree.EMandi.exception.InvalidCropException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.service.CropService;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
public class CropControllerTest 
{
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CropService service;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testGetAllCrops() throws ServiceException, Exception
	{
		List<Crop> crops = new ArrayList<Crop>();
		
		Crop crop1 = new Crop(1,"wheat",10);
		Crop crop2 = new Crop(2,"rice",10);
		
		crops.add(crop1);
		crops.add(crop2);
		
		doReturn(crops).when(service).getAllCrops();
		
		mockMvc.perform(get("/crop/getAllCrops"))
		.andExpect(status().isOk())
		.andExpect(header().string("Description", "Getting all crops success"));
	}
	
	@Test
	public void testAddCrop() throws Exception
	{
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin = new Admin("11", "admin@12", "iqhra1998nus@gmail.com", "bihar", sAdmin);
		
		Crop crop = new Crop(1,"wheat",10);
		crop.setAdmin(admin);
		
		doReturn("Successfully added crop").when(service).addCrop(crop);
		
		String json = mapper.writeValueAsString(crop);
		
		mockMvc.perform(post("/addCrop").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
              .content(json).accept(MediaType.APPLICATION_JSON));
		
		Assertions.assertEquals("Successfully added crop", service.addCrop(crop));
	}
	
	@Test
	void testGetCropMSP() throws InvalidCropException,Exception 
	{
		SuperAdmin sAdmin = new SuperAdmin(111,"superadmin@111");
		Admin admin = new Admin("11", "admin@12", "iqhra1998nus@gmail.com", "bihar", sAdmin);
		
		Crop crop = new Crop(1,"wheat",10);
		crop.setAdmin(admin);
		
		String adminId = "11";
		String cropName = "wheat";
		
		doReturn(crop).when(service).getCropMSP(cropName, adminId);
		
		mockMvc.perform(get("/crop/getCropMSP?cropName=wheat&adminId=11"))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.cropName", is("wheat")));
	}
}