package com.mindtree.EMandi.modules.mandi.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.mandi.converter.MandiConverter;
import com.mindtree.EMandi.modules.mandi.dto.MandiDto;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.mandi.service.MandiService;

@RestController
@RequestMapping("/mandi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MandiController {

	@Autowired
	private MandiService mandiService;

	@Autowired
	private MandiConverter converter;

	@PostMapping("/add")
	public ResponseEntity<MandiDto> addMandi(@RequestBody MandiDto mandiDto) {
		Mandi mandi = converter.dtoToEntity(mandiDto);
		mandi = mandiService.addMandi(mandi);
		return new ResponseEntity<MandiDto>(mandiDto, HttpStatus.OK);
	}

	@PostMapping("/getMandiByAdminId")
	public ResponseEntity<List<MandiDto>> getMandiByAdminId(@RequestBody Map<String, String> map) {
		List<MandiDto> mandiesDto = null;
		List<Mandi> mandies = null;
		HttpHeaders header = new HttpHeaders();
		try {
			mandies = mandiService.getMandiByAdminId(map.get("adminId")).stream().collect(Collectors.toList());
			mandiesDto = mandies.stream().map(x -> converter.entityToDto(x)).collect(Collectors.toList());
		} catch (ServiceException e) {
			e.printStackTrace();
			header.add("Description", "Mandies are found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(null);
		}
		header.add("Description", "Mandies found");
		return ResponseEntity.status(HttpStatus.OK).headers(header).body(mandiesDto);

	}
}
