package com.mindtree.EMandi.modules.farmer.controller;

import java.util.Map;

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

import com.mindtree.EMandi.exception.FarmerException;
import com.mindtree.EMandi.exception.service.FarmersServiceException;
import com.mindtree.EMandi.modules.farmer.converter.FarmerConverter;
import com.mindtree.EMandi.modules.farmer.dto.FarmerDto;
import com.mindtree.EMandi.modules.farmer.dto.FarmerDto1;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;
import com.mindtree.EMandi.modules.farmer.service.FarmerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/farmer")
public class FarmerController {

	@Autowired
	FarmerService farmerService;

	@Autowired
	FarmerConverter converter;

	@PostMapping("/login")
	public ResponseEntity<String> sayHello(@RequestBody Map<String, String> map) {
		String id = farmerService.validateLogin(map);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Login Request being check");
		header.add("userType", "farmer");
		if(id!=null)
		return new ResponseEntity<>(id, header, HttpStatus.OK);
		else 
			return new ResponseEntity<>(null, header, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/createFarmer")
	public ResponseEntity<Integer> addPerson(@RequestBody Farmer farmer) throws FarmerException {
		try {

			Farmer farmers = farmerService.createFarmer(farmer);
			if (farmers == null) {
				HttpHeaders header1 = new HttpHeaders();
				header1.add("desc", "Aadhar Id already exist.");
				return new ResponseEntity<Integer>(null, header1, HttpStatus.BAD_REQUEST);
			}
			HttpHeaders header = new HttpHeaders();
			header.add("desc", "Farmer application");
			return new ResponseEntity<Integer>(farmers.getFarmerId(), header, HttpStatus.OK);
		} catch (FarmersServiceException e) {
			System.out.println("Data not added to database");
			throw new FarmerException("Farmer not added");
		}

	}

	@GetMapping("/validate/{id}")
	public ResponseEntity<String> validateFarmer(@PathVariable int id) {
		Farmer farmer;
		try {
			farmer = farmerService.getFarmer(id);
		} catch (FarmerException e) {
			return null;
		}
		if (farmer != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("desc", "credentials validation");
			header.add("userType", "farmer");
			return new ResponseEntity<>("" + id, header, HttpStatus.OK);
		} else
			return null;
	}

	@PutMapping("/resetPassword")
	public ResponseEntity<Farmer> resetPassword(@RequestBody Map<String, String> map) {

		Farmer farmer;
		try {
			farmer = farmerService.updatePassword(map);
		} catch (FarmerException e) {
			return null;
		}
		if (farmer != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("desc", "credentials validation");
			header.add("userType", "farmer");
			return new ResponseEntity<>(farmer, header, HttpStatus.OK);
		} else
			return null;
	}

	@PostMapping("/sqCheck")
	public ResponseEntity<String> securityCheck(@RequestBody Map<String, String> map) {
		String msg = null;
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "credentials validation");
		header.add("userType", "farmer");
		try {
			msg = farmerService.validateQA(map);
		} catch (Exception e) {
			return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
		}
		if (msg == null)
			return new ResponseEntity<>("security question didnt match", header, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}

	@GetMapping("/getfarmer/{id}")
	public ResponseEntity<FarmerDto> getFarmer(@PathVariable int id) {
		Farmer farmer = null;
		try {
			farmer = farmerService.getFarmer(id);
		} catch (FarmerException e) {
			return null;
		}

		return new ResponseEntity<FarmerDto>(FarmerConverter.entityToDto(farmer), HttpStatus.OK);

	}

	@PutMapping("/updateFarmer")
	public ResponseEntity<String> updateFarmerProfile(@RequestBody Farmer farmer) throws FarmerException {
		Farmer farmerDetails = null;
		int farmerId = farmer.getFarmerId();
		try {
			farmerDetails = farmerService.getFarmer(farmerId);
		} catch (FarmerException e) {
			return null;
		}
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Farmer application");
		String updateFarmer = farmerService.updateFarmerProfile(farmerDetails, farmer);
		return new ResponseEntity<String>(updateFarmer, header, HttpStatus.OK);
	}

	@GetMapping("/details")
	public ResponseEntity<FarmerDto1> getName(@RequestParam("farmerId") int farmerId) {
		Farmer farmer = null;
		try {
			farmer = farmerService.getFarmer(farmerId);
			return new ResponseEntity<FarmerDto1>(converter.entityToDto1(farmer), HttpStatus.OK);
		} catch (FarmerException e) {
	        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
}
