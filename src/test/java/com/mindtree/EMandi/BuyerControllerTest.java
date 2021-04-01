package com.mindtree.EMandi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.modules.buyer.dto.BuyerDto;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
import com.mindtree.EMandi.modules.buyer.service.BuyerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
class BuyerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BuyerService service;

	@Test
	@DisplayName("buyer/get/1")
	void getBuyer() throws Exception {
		
		Buyer buyer = new Buyer();
		buyer.setBuyerId(1);
		buyer.setBuyerName("nusrath");
		buyer.setPassword("anjum15");
		buyer.setPhoneNumber("7259258255");
		doReturn(buyer).when(service).getBuyer(1);
		
		mockMvc.perform(get("/buyer/get/{id}",1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.buyerId", is(1)))
        .andExpect(jsonPath("$.buyerName", is("nusrath")))
        .andExpect(jsonPath("$.password", is("anjum15")))
        .andExpect(jsonPath("$.phoneNumber", is("7259258255")));
	}  
	
	@Test
	@DisplayName("validate")
	void validateUser() throws Exception {
		
		Buyer buyer = new Buyer(1,"nusrath","anjum15","7259258255");
		doReturn(buyer).when(service).getBuyer(1);
		
		mockMvc.perform(get("/buyer/validate/{id}",1))
		.andExpect(status().isOk())
		.andExpect(header().string("desc", "credentials validation"))
		.andExpect(header().string("userType", "buyer"))
		.andExpect(content().string(""+1));
		
	}
	
	@Test
	@DisplayName("get buyer details")
	void getDetails() throws Exception {
		Buyer buyer = new Buyer(1,"nusrath","anjum15","7259258255");
		doReturn(buyer).when(service).getBuyer(1);
		
		mockMvc.perform(get("/buyer/details?buyerId=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.buyerId", is(1)))
        .andExpect(jsonPath("$.buyerName", is("nusrath")))
        .andExpect(jsonPath("$.password", is("anjum15")))
        .andExpect(jsonPath("$.phoneNumber", is("7259258255")));
	}
	
	@Test
	@DisplayName("get buyer details")
	void testLogin() throws Exception {
		Buyer buyer = new Buyer(1,"nusrath","anjum15","7259258255");
		doReturn(buyer).when(service).getBuyer(1);
		
		mockMvc.perform(get("/buyer/details?buyerId=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.buyerId", is(1)))
        .andExpect(jsonPath("$.buyerName", is("nusrath")))
        .andExpect(jsonPath("$.password", is("anjum15")))
        .andExpect(jsonPath("$.phoneNumber", is("7259258255")));
	}

}
