package com.mindtree.EMandi.modules.buyer.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mindtree.EMandi.modules.buyer.dto.BuyerRequestDto;
import com.mindtree.EMandi.modules.buyer.entity.BuyerRequest;


@Component
public class BuyerRequestConverter {

	public BuyerRequestDto entityToDto(BuyerRequest buyer) 
	{
		ModelMapper mapper = new ModelMapper();
		BuyerRequestDto byerDto = mapper.map(buyer, BuyerRequestDto.class);
		return byerDto;
	}

	public BuyerRequest dtoToEntity(BuyerRequestDto buyerDto) 
	{
		ModelMapper mapper = new ModelMapper();
		BuyerRequest buyer = mapper.map(buyerDto, BuyerRequest.class);
		return buyer;
	}

	public List<BuyerRequestDto> entityToDtoForList(List<BuyerRequest> buyer) 
	{
		return buyer.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
	}
}
