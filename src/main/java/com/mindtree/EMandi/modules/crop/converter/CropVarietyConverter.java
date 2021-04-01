package com.mindtree.EMandi.modules.crop.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mindtree.EMandi.modules.crop.dto.CropVarietyDto;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
@Component
public class CropVarietyConverter {
	public CropVarietyDto entityToDto(CropVariety crop) {

		ModelMapper mapper = new ModelMapper();
		CropVarietyDto cropVarietyDto = mapper.map(crop, CropVarietyDto.class);
		return cropVarietyDto;
	}

	public CropVariety dtoToEntity(CropVariety crop) {

		ModelMapper mapper = new ModelMapper();
		CropVariety cropVariety = mapper.map(crop, CropVariety.class);
		return cropVariety;
	}
	
	public CropVariety dtoToEntity(CropVarietyDto cropVarietyDto) {
		ModelMapper mapper = new ModelMapper();
		CropVariety crop = mapper.map(cropVarietyDto, CropVariety.class);
		
		return crop;
	}
}