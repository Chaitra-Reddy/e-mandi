package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.mindtree.EMandi.exception.service.BuyerServiceException;
import com.mindtree.EMandi.modules.buyer.dto.BuyerTransactionDto;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;
import com.mindtree.EMandi.modules.buyer.service.BuyerService;
import com.mindtree.EMandi.modules.crop.service.CropService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
class BuyerTranscationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	BuyerService service;
	
	@Test
	void getTransactionsTest() throws Exception {
		List<BuyerTransaction> transactions=new ArrayList<>();
		transactions.add(new BuyerTransaction(200,"wheat","A",50,new Date()));
		transactions.add(new BuyerTransaction(250,"wheat","B",60,new Date()));
		
		doReturn(transactions).when(service).getTransactions(1);
		
		mockMvc.perform(get("/buyerTransaction/getTransactions/{id}",1))
		.andExpect(status().isOk())
		.andExpect(header().string("Description", "Getting all previous transaction details"));
	}

	@Test
	void getTransactionsSecondTest() throws Exception {
		List<BuyerTransaction> transactions=new ArrayList<>();
		transactions.add(new BuyerTransaction(200,"wheat","A",50,new Date()));
		transactions.add(new BuyerTransaction(250,"wheat","B",60,new Date()));
		
		List<BuyerTransactionDto> transactionsDto=new ArrayList<>();
		transactionsDto.add(new BuyerTransactionDto(1,"wheat","A",200,50,new Date(),543421));
		transactionsDto.add(new BuyerTransactionDto(2,"wheat","B",250,60,new Date(),557854));
		
		doReturn(transactions).when(service).getBuyerTransaction("21",31);
		
		mockMvc.perform(get("/buyerTransaction/byId").param("clerkId", "21")
                .param("buyerId", "31") )
		.andExpect(status().isOk());
	}
}
