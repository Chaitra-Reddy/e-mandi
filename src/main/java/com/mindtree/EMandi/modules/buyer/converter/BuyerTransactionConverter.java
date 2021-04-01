package com.mindtree.EMandi.modules.buyer.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mindtree.EMandi.modules.buyer.dto.BuyerTransactionDto;
import com.mindtree.EMandi.modules.buyer.entity.BuyerTransaction;

@Component
public class BuyerTransactionConverter {
	public BuyerTransactionDto transactionToDto(BuyerTransaction buyerTransaction) 
	{
		ModelMapper mapper = new ModelMapper();
		BuyerTransactionDto TransactionDto = mapper.map(buyerTransaction,BuyerTransactionDto.class);
		return TransactionDto;
	}
	
	public List<BuyerTransactionDto> transactionToDto(List<BuyerTransaction> buyerTransaction){
		return buyerTransaction.stream().map(x -> transactionToDto(x)).collect(Collectors.toList());
	}
}
