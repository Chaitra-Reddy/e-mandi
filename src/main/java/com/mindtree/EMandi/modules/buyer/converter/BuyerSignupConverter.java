package com.mindtree.EMandi.modules.buyer.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import com.mindtree.EMandi.modules.buyer.dto.BuyerSignupCode;
import com.mindtree.EMandi.modules.buyer.entity.Buyer;
@Component
public class BuyerSignupConverter {

public Buyer dtoToEntity(BuyerSignupCode buyerDto) {
		
		ModelMapper mapper=new ModelMapper();
		Buyer buyer=mapper.map(buyerDto, Buyer.class);
		
		return buyer;
	}
	
	public BuyerSignupCode entityToDto(Buyer buyer) {
		
		ModelMapper mapper=new ModelMapper();
		BuyerSignupCode buyerDto = mapper.map(buyer, BuyerSignupCode.class);
		
		return buyerDto;
	}
	
	public List<BuyerSignupCode> entityToDtoForList(List<Buyer> buyer) 
	{
		return buyer.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
	}
}
