package com.mindtree.EMandi.modules.mandi.service;

import java.util.Set;

import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;

public interface MandiService {
	
	Mandi addMandi(Mandi mandi);
	
	Mandi getMandiByMandiPincode(int mandiPincode);
	
	Mandi updateMandiStorage(int mandiPincode, double storage);
	
	Set<Mandi> getMandiByAdminId(String adminId) throws ServiceException;
}
