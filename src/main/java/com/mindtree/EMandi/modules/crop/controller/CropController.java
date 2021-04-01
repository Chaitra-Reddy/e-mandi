package com.mindtree.EMandi.modules.crop.controller;

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

import com.mindtree.EMandi.exception.CropException;
import com.mindtree.EMandi.exception.InvalidCropException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.CropServiceException;
import com.mindtree.EMandi.modules.crop.converter.CropConverter;
import com.mindtree.EMandi.modules.crop.converter.CropVarietyConverter;
import com.mindtree.EMandi.modules.crop.dto.CropDto;
import com.mindtree.EMandi.modules.crop.dto.CropVarietyDto;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.service.CropService;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;

@RestController
@RequestMapping("/crop")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CropController {
	@Autowired
	private CropService cropService;

	@Autowired
	private CropConverter cropConverter;

	@Autowired
	CropVarietyConverter cropVarietyConvertor;
    @Autowired
    MandiRepository mandiRepository;
	@PostMapping("/addCrop")
	public ResponseEntity<String> addCrop(@RequestBody CropDto cropDto) {
		Crop crop = cropConverter.dtoToEntity(cropDto);
		String message = cropService.addCrop(crop);
		HttpHeaders header = new HttpHeaders();
		header.add("Description", "Adding a crop");
		return ResponseEntity.status(HttpStatus.CREATED).headers(header).body(message);
	}

	@GetMapping("/getAllCrops")
	public ResponseEntity<List<CropDto>> getAllCrops() {
		List<Crop> crops = new ArrayList<Crop>();
		List<CropDto> cropsDtos = new ArrayList<CropDto>();
		try {
			crops = cropService.getAllCrops();
			cropsDtos = cropConverter.entityToDtoForList(crops);
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting all crops success");
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(cropsDtos);
		} catch (ServiceException e) {
			HttpHeaders header = new HttpHeaders();
			header.add("Description", "Getting all crops failed");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(null);
		}
	}

	@GetMapping("/getCropMSP")
	public ResponseEntity<CropDto> getCropMSP(@RequestParam("cropName") String cropName,
			@RequestParam("adminId") String adminId) {
		Crop crop;
		try {
			crop = cropService.getCropMSP(cropName, adminId);
			return new ResponseEntity<CropDto>(cropConverter.entityToDto(crop), HttpStatus.OK);
		} catch (InvalidCropException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/updateMSP")
	public ResponseEntity<CropDto> updateMSP(@RequestBody CropDto cropDto) {
		System.out.println(cropDto.getAdminId() + " " + cropDto.getCropMSP() + " " + cropDto.getCropName());
		Crop crop = cropConverter.dtoToEntity(cropDto);
		try {
			String message = cropService.updateMSP(crop);
			return new ResponseEntity<CropDto>(cropDto, HttpStatus.OK);
		} catch (InvalidCropException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/getCropPrice/{adminId}/{cropName}/{cropClass}")
	public ResponseEntity<Double> getCropPriceForBuyer(@PathVariable("cropName") String cropName,
			@PathVariable("cropClass") String cropClass, @PathVariable("adminId") String adminId) throws CropException {
		HttpHeaders header = new HttpHeaders();
		CropVariety cropVariety = null;

		try {
			cropVariety = cropService.getCropCostForBuyer(cropName, cropClass, adminId);

		} catch (CropServiceException e) {
			System.out.println("No such crop found.");
		}
		if (cropVariety == null) {
			header.add("desc", "Crop not found");
			return new ResponseEntity<Double>(null, header, HttpStatus.BAD_REQUEST);
		}
		CropVarietyDto cropVarietyDTO = cropVarietyConvertor.entityToDto(cropVariety);
		return new ResponseEntity<Double>(cropVarietyDTO.getBuyerCropPrice(), HttpStatus.OK);
	}

	
}