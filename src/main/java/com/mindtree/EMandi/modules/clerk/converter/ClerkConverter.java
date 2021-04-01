package com.mindtree.EMandi.modules.clerk.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mindtree.EMandi.modules.clerk.dto.ClerkDto;
import com.mindtree.EMandi.modules.clerk.entity.Clerk;

@Component
public class ClerkConverter {
public Clerk dtoToEntity(ClerkDto clerkDto) {
		
		ModelMapper mapper=new ModelMapper();
		Clerk clerk=mapper.map(clerkDto, Clerk.class);
		
		return clerk;
	}
	
	public ClerkDto entityToDto(Clerk clerk) {
		
		ModelMapper mapper=new ModelMapper();
		ClerkDto clerkDto = mapper.map(clerk, ClerkDto.class);
		
		return clerkDto;
	}
	
}
