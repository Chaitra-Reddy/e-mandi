package com.mindtree.EMandi.modules.farmer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.FarmerTransactionServiceException;
import com.mindtree.EMandi.exception.service.FarmersServiceException;
import com.mindtree.EMandi.modules.farmer.converter.FarmerConverter;
import com.mindtree.EMandi.modules.farmer.dto.ExtraCreditDto;
import com.mindtree.EMandi.modules.farmer.dto.FarmerTransactionDto;
import com.mindtree.EMandi.modules.farmer.dto.FarmerTransactionDto1;
import com.mindtree.EMandi.modules.farmer.dto.TransactionDto;
import com.mindtree.EMandi.modules.farmer.entity.FarmerTransaction;
import com.mindtree.EMandi.modules.farmer.repository.FarmerTransactionRepository;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/farmerTransaction")
public class FarmerTransactionController {

	@Autowired
	private FarmerConverter converter;
	@Autowired
	private FarmerTransactionRepository rep;
	@Autowired
	private FarmerService farmerService;
	@GetMapping("/getAllCrops/{mandiPincode}")
	public ResponseEntity<List<FarmerTransactionDto>> getAllCrops(@PathVariable (value="mandiPincode") int mandiPincode) {
		List<FarmerTransaction> farmer=null;
		try {
			farmer = farmerService.findByMandiPincode(mandiPincode);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			System.out.println("No details found");
		}
		List<FarmerTransactionDto> farmerDtos = converter.entityToDtoForListTrans(farmer);
		HttpHeaders header = new HttpHeaders();
		header.add("Description", "Getting all crops");
		return ResponseEntity.status(HttpStatus.OK).headers(header).body(farmerDtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable int id) {
		List<FarmerTransaction> transactions = new ArrayList<>();
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "get farmer transactions");
		header.add("userType", "farmer");
		try {
			transactions = farmerService.getTransactions(id);
		} catch (FarmersServiceException e) {
			return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
		}
		if(transactions.size()!=0)
		{List<TransactionDto> transactionDtos = new ArrayList<>();
		for (FarmerTransaction f : transactions) {
			transactionDtos.add(converter.transactionToDtoTrans(f));
		}
		return new ResponseEntity<>(transactionDtos, header, HttpStatus.OK);
		}
		else return new ResponseEntity<>(null, header, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/byId")
	public ResponseEntity<List<FarmerTransactionDto1>> getTransactions(@RequestParam("clerkId") String clerkId,
			@RequestParam("farmerId") int farmerId) {
		List<FarmerTransaction> transactions;
		try {
			transactions = farmerService.getTransactions(clerkId, farmerId);
			return new ResponseEntity<List<FarmerTransactionDto1>>(converter.entityToDto(transactions), HttpStatus.OK);
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}

	@PostMapping("/checkForTransactonId")
	public ResponseEntity<Boolean> checkForTransactionId(@RequestBody int transactionId) {
		boolean op = false;
		try {
			op = farmerService.checkForTransactionId(transactionId);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Checking transaction ID validation");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(op);
		} catch (FarmerTransactionServiceException e) {
			op = false;
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Checking transaction ID validation");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(op);
		}
	}

	@PutMapping("/creditExtraAmount")
	public ResponseEntity<Boolean> creditExtraAmount(@RequestBody ExtraCreditDto extraCreditDto) {
		boolean op = false;
		try {
			op = farmerService.creditExtraAmount(extraCreditDto);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Updating transaction details");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(op);
		} catch (FarmerTransactionServiceException e) {
			op = false;
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Updating transaction details");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(op);
		}
	}

}
