package com.mindtree.EMandi.modules.mandi.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.EMandi.exception.ResourceNotFoundException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;
import com.mindtree.EMandi.modules.mandi.service.MandiService;

@Service
public class MandiServiceImpl implements MandiService{
	
	@Autowired
	private MandiRepository repository;

	@Override
	public Mandi addMandi(Mandi mandi) {
		// TODO Auto-generated method stub
		repository.save(mandi);
		return mandi;
	}

	@Override
	public Mandi getMandiByMandiPincode(int mandiPincode) 
	{
		return repository.findById(mandiPincode).orElse(null);
	}

	@Override
	public Mandi updateMandiStorage(int mandiPincode, double storage) 
	{
		Mandi mandi = getMandiByMandiPincode(mandiPincode);
		mandi.setStorage(mandi.getStorage() - storage);
		repository.save(mandi);
		return mandi;
	}

	@Override
	public Set<Mandi> getMandiByAdminId(String adminId) throws ServiceException {
		Set<Mandi> mandi=null;
		try {
		mandi=repository.findMandiByAdminId(adminId).stream().collect(Collectors.toSet());
		if(mandi.isEmpty()) {
			throw new ResourceNotFoundException("No data found");
		}
		}catch(ResourceNotFoundException e) {
			throw new ServiceException(e.getMessage(),e);
		}catch(Exception e) {
			throw new ServiceException("Some exception ouccered",e);
		}
		return mandi;
	}
}
