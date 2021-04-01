package com.mindtree.EMandi.modules.clerk.controller;

import java.util.List;
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

import com.mindtree.EMandi.exception.IdNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.buyer.dto.BuyerRequestDto;
import com.mindtree.EMandi.modules.clerk.converter.ClerkConverter;
import com.mindtree.EMandi.modules.clerk.dto.ClerkCropDto;
import com.mindtree.EMandi.modules.clerk.dto.ClerkDto;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;
import com.mindtree.EMandi.modules.clerk.service.ClerkService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clerk")
public class ClerkController {

	@Autowired
	ClerkService clerkService;

	@Autowired
	private ClerkConverter clerkConv;

	@PostMapping("/login")
	public ResponseEntity<String> sayHello(@RequestBody Map<String, String> map) {
		String id = clerkService.validateLogin(map);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Login Request being check");
		header.add("userType", "clerk");
		if(id!=null)
		return new ResponseEntity<>(id, header, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, header, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/validate/{id}")
	public ResponseEntity<String> validateClerk(@PathVariable String id) {
		Clerk clerk;
		try {
			clerk = clerkService.getClerk(id);
		} catch (ServiceException e) {
			return null;
		}
		if (clerk != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("desc", "credentials validation");
			header.add("userType", "clerk");
			return new ResponseEntity<>(id, header, HttpStatus.OK);
		} else
			return null;
	}

	@PutMapping("/resetPassword")
	public ResponseEntity<Clerk> resetPassword(@RequestBody Map<String, String> map) {

		Clerk clerk;
		try {
			clerk = clerkService.updatePassword(map);
		} catch (ServiceException e) {
			return null;
		}
		if (clerk != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("desc", "credentials validation");
			header.add("userType", "clerk");
			return new ResponseEntity<>(clerk, header, HttpStatus.OK);
		} else
			return null;
	}

	@PostMapping("/getTotalPrice")
	public ResponseEntity<Double> getTotalPrice(@RequestBody ClerkCropDto clerkCropDto[]) {
		double total = 0;
		try {
			total = clerkService.getTotalPrice(clerkCropDto);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting total price success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(total);
		} catch (Exception e) {
			total = 0;
			e.printStackTrace();
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting total price failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(header).body(total);
		}
	}

	@PostMapping("/buyCrops")
	public ResponseEntity<Boolean> buyCrops(@RequestBody ClerkCropDto clerkCropDto[]) {
		boolean op = false;
		try {
			op = clerkService.buyCrops(clerkCropDto);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Buying crops success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(op);
		} catch (ServiceException e) {
			op = false;
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Buying crops failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(header).body(op);
		}
	}

	@PostMapping("/getStorage")
	public ResponseEntity<Double> getStorageByClerkId(@RequestBody String clerkId) {
		double storage = 0;
		try {
			storage = clerkService.getStorageByClerkId(clerkId);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting mandi storage success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(storage);
		} catch (ServiceException e) {
			e.printStackTrace();
			storage = -1;
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting mandi storage failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(header).body(storage);
		}
	}

	@PostMapping("/getSingleCropPrice")
	public ResponseEntity<Double> getSingleCropPrice(@RequestBody ClerkCropDto clerkCropDto) {
		double cost = 0;
		try {
			cost = clerkService.getSingleCropPrice(clerkCropDto);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting single crop price success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(cost);
		} catch (ServiceException e) {
			e.printStackTrace();
			cost = 0;
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting single crop price failed");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(cost);
		}
	}

	@PostMapping("/validateFarmer")
	public ResponseEntity<Boolean> validateFarmerId(@RequestBody int farmerId) {
		boolean op = false;
		try {
			op = clerkService.validateFarmerId(farmerId);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Validating farmer ID success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(op);
		} catch (ServiceException e) {
			op = false;
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Validating farmer ID success");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(op);
		}
	}

	@PostMapping("/passwordMail")
	public ResponseEntity<String> mailPassword(@RequestBody Map<String, String> map) {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "sending password in mail");
		header.add("userType", "clerk");
		String msg;
		try {
			msg = clerkService.passwordMail(map);
		} catch (ServiceException e) {
			return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(msg, header, HttpStatus.OK);

	}

	@GetMapping("/farmers")
	public ResponseEntity<List<Integer>> getFarmerIds(@RequestParam("clerkId") String clerkId) {
		try {
			return new ResponseEntity<List<Integer>>(clerkService.getFarmerIds(clerkId), HttpStatus.OK);
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buyers")
	public ResponseEntity<List<Integer>> getBuyerIds(@RequestParam("clerkId") String clerkId) {
		try {
			return new ResponseEntity<List<Integer>>(clerkService.getBuyerIds(clerkId), HttpStatus.OK);
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateClerk")
	public ResponseEntity<Boolean> updateClerk(@RequestBody ClerkDto clerkDto) {
		Clerk clerk = clerkConv.dtoToEntity(clerkDto);
		boolean op = false;
		try {
			op = clerkService.updateClerkProfile(clerk);
		} catch (ServiceException e) {
			e.printStackTrace();
			op = false;
		}
		if (op == true) {
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Updating clerk details success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(op);
		} else {
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Updating clerk details failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(header).body(op);
		}
	}

	@GetMapping("/getClerk/{id}")
	public ResponseEntity<Clerk> getClerkById(@PathVariable String id) {
		Clerk clerk = new Clerk();
		try {
			clerk = clerkService.getClerk(id);
		} catch (ServiceException e) {
			e.printStackTrace();
			clerk = null;
		}
		if (clerk != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting clerk details success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(clerk);
		} else {
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting clerk details failed");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(clerk);
		}
	}

	@GetMapping("/getRequestList/{clerkId}")
	public ResponseEntity<List<BuyerRequestDto>> getRequestList(@PathVariable String clerkId) {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "credentials validation");
		header.add("userType", "clerk");
		List<BuyerRequestDto> requestDto;
		try {
			requestDto = clerkService.getRequestList(clerkId);
		} catch (ServiceException e) {
			header.add("msg", "no request present");
			return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
		}
		header.add("msg", "returned sucessfully");
		return new ResponseEntity<>(requestDto, header, HttpStatus.OK);

	}

	@PostMapping("/acceptRequest")
	public ResponseEntity<String> acceptReq(@RequestBody Map<String, String> map) {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", " Accept Buyer Request");
		header.add("userType", "clerk");
		String msg = null;
		try {
			msg = clerkService.requestAccept(map.get("reqId"));
		} catch (ServiceException e) {
			header.add("msg", "couldnt accept the request");
			return new ResponseEntity<>(msg, header, HttpStatus.BAD_REQUEST);
		}
		header.add("msg", msg);
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}

	@PostMapping("/rejectRequest")
	public ResponseEntity<String> rejectRequest(@RequestBody Map<String, String> map) {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", " Reject Buyer Request");
		header.add("userType", "clerk");
		String msg = null;
		try {
			msg = clerkService.requestReject(map.get("reqId"));
		} catch (ServiceException e) {
			header.add("msg", "Error while rejecting request");
			return new ResponseEntity<>(msg, header, HttpStatus.BAD_REQUEST);
		}
		header.add("msg", msg);
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
}
