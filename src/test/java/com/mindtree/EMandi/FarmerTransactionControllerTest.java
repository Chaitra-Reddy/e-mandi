package com.mindtree.EMandi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.service.CropService;
import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
class FarmerTransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FarmerService service;

	@Test
	void getAllCropstest() throws Exception {

		List<FarmerTransaction> farmer = new ArrayList<>();

		farmer.add(new FarmerTransaction("wheat", "A", 200, 50));
		farmer.add(new FarmerTransaction("wheat", "B", 200, 50));

		doReturn(farmer).when(service).findByMandiPincode(10);

		mockMvc.perform(get("/farmerTransaction/getAllCrops/{mandiPincode}", 10)).andExpect(status().isOk())
				.andExpect(header().string("Description", "Getting all crops"));

	}

	@Test
	void getTransactiontest() throws Exception {

		List<FarmerTransaction> farmerTrans = new ArrayList<>();

		farmerTrans.add(new FarmerTransaction("wheat", "A", 200, 50));
		farmerTrans.add(new FarmerTransaction("wheat", "B", 200, 50));

		doReturn(farmerTrans).when(service).getTransactions(10);

		mockMvc.perform(get("/farmerTransaction//{id}", 10))
				.andExpect(status().isOk())
				.andExpect(header().string("desc", "get farmer transactions"))
				.andExpect(header().string("userType", "farmer"));

	}
	
	@Test
	@DisplayName("farmer transactions by Id")
	void getFarmerTransactions() throws Exception {
		List<FarmerTransaction> farmerTrans = new ArrayList<>();

		FarmerTransaction farmer = new FarmerTransaction("wheat","C",200,50);
		farmer.setTransactionId(1);
		farmerTrans.add(farmer);
		
		try {
			doReturn(farmerTrans).when(service).getTransactions("1",1);
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mockMvc.perform(get("/farmerTransaction/byId?clerkId=1&farmerId=1"))
		.andExpect(status().isOk());
		
	}

}

