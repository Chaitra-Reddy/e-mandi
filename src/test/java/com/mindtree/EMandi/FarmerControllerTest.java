package com.mindtree.EMandi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.mindtree.EMandi.exception.FarmerException;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
class FarmerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FarmerService service;

	@Test
	@DisplayName("get farmer detail")
	void getFarmer() throws Exception {
		Farmer farmer = new Farmer(1,"robin","SBI","55159521645","SBIN0001561","259891957247","v","8546121045");
		farmer.setSecurityQuestion("cityName");
		farmer.setAnswer("pune");
		try {
			doReturn(farmer).when(service).getFarmer(1);
		} catch (FarmerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mockMvc.perform(get("/farmer/getfarmer/{id}",1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.farmerName", is("robin")))
        .andExpect(jsonPath("$.aadharNumber", is("259891957247")))
        .andExpect(jsonPath("$.mobileNumber",is("8546121045")))
        .andExpect(jsonPath("$.farmerId",is("1")))
        .andExpect(jsonPath("$.bankName",is("SBI")))
        .andExpect(jsonPath("$.accountNumber",is("55159521645")))
        .andExpect(jsonPath("$.ifsc",is("SBIN0001561")))
        .andExpect(jsonPath("$.password", is("v")))
        .andExpect(jsonPath("$.securityQuestion", is("cityName")))
        .andExpect(jsonPath("$.answer",is("pune")));
	}
	
	@Test
	@DisplayName("validate farmer")
	void validateFarmer() throws Exception {
		Farmer farmer = new Farmer(1,"robin","SBI","55159521645","SBIN0001561","259891957247","v","8546121045");
		farmer.setSecurityQuestion("cityName");
		farmer.setAnswer("pune");
		try {
			doReturn(farmer).when(service).getFarmer(1);
		} catch (FarmerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mockMvc.perform(get("/farmer/validate/{id}",1))
		.andExpect(status().isOk())
		.andExpect(header().string("desc", "credentials validation"))
		.andExpect(header().string("userType", "farmer"))
		.andExpect(content().string(""+1));
	}
	
	@Test
	@DisplayName("get farmer details")
	void getDetails() throws Exception {
		Farmer farmer = new Farmer(1,"robin","SBI","55159521645","SBIN0001561","259891957247","v","8546121045");
		farmer.setSecurityQuestion("cityName");
		farmer.setAnswer("pune");
		try {
			doReturn(farmer).when(service).getFarmer(1);
		} catch (FarmerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mockMvc.perform(get("/farmer/details?farmerId=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.farmerName", is("robin")))
        .andExpect(jsonPath("$.aadharNumber", is("259891957247")))
        .andExpect(jsonPath("$.mobileNumber",is("8546121045")))
        .andExpect(jsonPath("$.bankName",is("SBI")))
        .andExpect(jsonPath("$.accountNumber",is("55159521645")))
        .andExpect(jsonPath("$.ifsc",is("SBIN0001561")));
	}

}
