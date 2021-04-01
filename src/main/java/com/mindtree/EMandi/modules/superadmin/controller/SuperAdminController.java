package com.mindtree.EMandi.modules.superadmin.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;
import com.mindtree.EMandi.modules.superadmin.service.SuperAdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/sAdmin")
public class SuperAdminController {

	@Autowired
	SuperAdminService sAdminService;

	@PostMapping("/login")
	public ResponseEntity<String> sayHello(@RequestBody Map<String, String> map) {
		String id = sAdminService.validateLogin(map);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Login Request being check");
		header.add("userType", "sAdmin");
		if(id!=null)
		return new ResponseEntity<>(id, header, HttpStatus.OK);
		else 
			return new ResponseEntity<>(null, header, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/validate/{id}")
	public ResponseEntity<String> validateSAdmin(@PathVariable String id) {
		SuperAdmin sAdmin;
		try {
			sAdmin = sAdminService.getSAdmin(Integer.parseInt(id));
		} catch (ServiceException e) {
			return null;
		}
		if (sAdmin != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("desc", "credentials validation");
			header.add("userType", "SuperAdmin");
			return new ResponseEntity<>(id, header, HttpStatus.OK);
		} else
			return null;
	}

	@PutMapping("/resetPassword")
	public ResponseEntity<SuperAdmin> resetPassword(@RequestBody Map<String, String> map) {
		SuperAdmin sAdmin = sAdminService.updatePassword(map);
		if (sAdmin != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("desc", "credentials validation");
			header.add("userType", "SuperAdmin");
			return new ResponseEntity<>(sAdmin, header, HttpStatus.OK);
		} else
			return null;
	}

	@PostMapping("/addAdmin")
	public ResponseEntity<String> addAdmin(@RequestBody Map<String, String> map) {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "Adding admin");
		header.add("userType", "SuperAdmin");
		String msg;
		try {
			msg = sAdminService.addAdmin(map);
		} catch (ServiceException e) {
			return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(msg, header, HttpStatus.OK);

	}
	
	@PostMapping("/passwordMail")
	public ResponseEntity<String> mailPassword(@RequestBody Map<String, String> map){
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "sending password in mail");
		header.add("userType", "SuperAdmin");
		String msg;
		try {
			msg = sAdminService.passwordMail(map);
		} catch (ServiceException e) {
			return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(msg, header, HttpStatus.OK);

	}

}
