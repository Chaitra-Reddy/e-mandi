package com.mindtree.EMandi.modules.crop.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.EMandi.exception.CropException;
import com.mindtree.EMandi.exception.service.CropServiceException;
import com.mindtree.EMandi.modules.crop.converter.CropVarietyConverter;
import com.mindtree.EMandi.modules.crop.dto.CropVarietyDto;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.service.CropService;

@RestController
@RequestMapping("/cropVariety")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CropVarietyController {

	@Autowired
	CropService cropService;
	@Autowired
	CropVarietyConverter cropVarietyConvertor;

	@PutMapping("/updateCropPrice")
	public ResponseEntity<Double> updateCropPriceForBuyer(@RequestBody Map<String, String> cropDetail)
			throws CropException {
		HttpHeaders header = new HttpHeaders();
		CropVariety cropVariety = null;
		String cropName = cropDetail.get("cropName");
		String cropClass = cropDetail.get("cropClass");
		String cropPrice = cropDetail.get("cropPrice");
		String adminId = cropDetail.get("adminId");
		try {
			cropVariety = cropService.updateCropCostForBuyer(cropName, cropClass, cropPrice, adminId);
		} catch (CropServiceException e) {
			System.out.println("Crop price not updated");
		}
         header.add("Description", "Crop price not updated");
		if (cropVariety == null) {
			return new ResponseEntity<Double>(null, header, HttpStatus.BAD_REQUEST);
		}
		CropVarietyDto cropVarietyDTO = cropVarietyConvertor.entityToDto(cropVariety);
		return new ResponseEntity<Double>(cropVarietyDTO.getBuyerCropPrice(), HttpStatus.OK);
	}
}

