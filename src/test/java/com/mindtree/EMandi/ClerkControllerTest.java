package com.mindtree.EMandi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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
import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;
import com.mindtree.EMandi.modules.clerk.service.ClerkService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
class ClerkControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClerkService service;

	@Test
	@DisplayName("validate clerk")
	void validateClerk() throws Exception {
		Clerk clerk=new Clerk("10","nusrath","anjum15","7259258255","abc@gmail.com");
		try {
			doReturn(clerk).when(service).getClerk("10");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mockMvc.perform(get("/clerk/validate/{id}","10"))
        .andExpect(status().isOk())
        .andExpect(header().string("desc", "credentials validation"))
		.andExpect(header().string("userType", "clerk"))
		.andExpect(content().string("10"));
	}
	
	@Test
	@DisplayName("get farmers")
	void getFarmers() throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		try {
			doReturn(list).when(service).getFarmerIds("1");
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mockMvc.perform(get("/clerk/farmers?clerkId=1"))
        .andExpect(status().isOk())
		.andExpect(jsonPath("$.[0]",is(1)));
	}
	
	@Test
	@DisplayName("get buyers")
	void getBuyers() throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		try {
			doReturn(list).when(service).getBuyerIds("1");
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mockMvc.perform(get("/clerk/buyers?clerkId=1"))
        .andExpect(status().isOk())
		.andExpect(jsonPath("$.[0]",is(1)))
		.andExpect(jsonPath("$.[1]",is(2)));
	}
	
	@Test
	@DisplayName("get clerk")
	void getClerk() throws Exception {
		Clerk clerk=new Clerk("10","nusrath","anjum15","7259258255","abc@gmail.com");
		try {
			doReturn(clerk).when(service).getClerk("10");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mockMvc.perform(get("/clerk/getClerk/{id}","10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clerkId", is("10")))
        .andExpect(jsonPath("$.clerkName", is("nusrath")))
        .andExpect(jsonPath("$.password", is("anjum15")))
        .andExpect(jsonPath("$.mobileNumber", is("7259258255")))
        .andExpect(jsonPath("$.emailId",is("abc@gmail.com")));
	}

}
