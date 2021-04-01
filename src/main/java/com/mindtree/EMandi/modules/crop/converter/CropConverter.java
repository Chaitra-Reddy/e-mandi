package com.mindtree.EMandi.modules.crop.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.mindtree.EMandi.modules.crop.dto.CropDto;
import com.mindtree.EMandi.modules.crop.entity.Crop;

@Component
public class CropConverter {
	public CropDto entityToDto(Crop crop) {
		ModelMapper mapper = new ModelMapper();

		mapper.addMappings(new PropertyMap<Crop, CropDto>() {
			protected void configure() {
				map().setAdminId(source.getAdmin().getAdminId());
			}
		});

		CropDto cropDto = mapper.map(crop, CropDto.class);

		return cropDto;
	}

	public Crop dtoToEntity(CropDto cropDto) {
		ModelMapper mapper = new ModelMapper();
		Crop crop = mapper.map(cropDto, Crop.class);

		return crop;
	}

	public List<CropDto> entityToDtoForList(List<Crop> crops) {
		return crops.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
	}
	
	public List<CropDto> entityToDto(List<Crop> crops){
		return crops.stream().map(x->entityToDto(x)).collect(Collectors.toList());
	}
}
