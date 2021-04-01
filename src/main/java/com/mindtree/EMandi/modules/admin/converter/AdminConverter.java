package com.mindtree.EMandi.modules.admin.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mindtree.EMandi.modules.admin.dto.AdminDto;
import com.mindtree.EMandi.modules.admin.entity.Admin;

@Component
public class AdminConverter {
	public AdminDto entityToDto(Admin admin) {
		AdminDto dto = new AdminDto();
		dto.setAdminId(admin.getAdminId());
		dto.setEmailId(admin.getEmailId());
		dto.setPassword(admin.getPassword());
		dto.setState(admin.getState());
		dto.setsAdmin(admin.getAdmin());
		return dto;
	}

	public List<AdminDto> entityToDto(List<Admin> admins) {
		return admins.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
	}
	
	public Admin dtoToEntity(AdminDto adminDto) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(adminDto, Admin.class);
	}
}
