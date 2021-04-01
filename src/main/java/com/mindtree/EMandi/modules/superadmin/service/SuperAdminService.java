package com.mindtree.EMandi.modules.superadmin.service;

import java.util.Map;

import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

public interface SuperAdminService {

	public String validateLogin(Map<String, String> map);

	public SuperAdmin getSAdmin(int id) throws ServiceException;

	public SuperAdmin updatePassword(Map<String, String> map);
	
	public String addAdmin(Map<String, String> map) throws ServiceException;
	
	public String sendMail(Admin admin) throws ServiceException;
	
	public String passwordMail(Map<String, String> map) throws ServiceException;
}
