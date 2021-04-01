package com.mindtree.EMandi.modules.superadmin.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.admin.repository.AdminRepository;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;
import com.mindtree.EMandi.modules.superadmin.repository.SuperAdminRepository;
import com.mindtree.EMandi.modules.superadmin.service.SuperAdminService;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

	@Autowired
	SuperAdminRepository sAdminRepo;
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	SpringTemplateEngine tempEngine;
	@Autowired
	private JavaMailSender sender;

	@Override
	public String validateLogin(Map<String, String> map) {
		SuperAdmin sAdmin = sAdminRepo.findById(Integer.parseInt(map.get("userId"))).orElse(null);
		if (sAdmin != null)
			if (sAdmin.getPassword().equals(map.get("password")))
				return map.get("userId");
			else
				return null;
		return null;
	}

	@Override
	public SuperAdmin getSAdmin(int id) throws ServiceException {
		SuperAdmin sAdmin;
		try {
			sAdmin = sAdminRepo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new ServiceException("No data found for that id", e);
		}

		return sAdmin;
	}

	@Override
	public SuperAdmin updatePassword(Map<String, String> map) {
		int id = Integer.parseInt(map.get("userId"));
		SuperAdmin sAdmin = sAdminRepo.findById(id).get();
		sAdmin.setPassword(map.get("password"));
		sAdminRepo.save(sAdmin);
		return sAdmin;

	}

	@Override
	public String addAdmin(Map<String, String> map) throws ServiceException {
		String id = getAdminId();
		Admin admin = new Admin();
		admin.setAdminId(id);
		admin.setEmailId(map.get("emailId"));
		admin.setPassword(map.get("password"));
		admin.setState(map.get("state"));
		try {
			admin.setsAdmin(sAdminRepo.findById(Integer.parseInt(map.get("sAdminId"))).get());
			List<Admin> admins = adminRepo.findAll();
			for (Admin a : admins) {
				if (admin.getEmailId().equalsIgnoreCase(a.getEmailId()))
					throw new ServiceException("already existing data");
			}

			adminRepo.save(admin);
		} catch (Exception e) {
			throw new ServiceException("Entity is empty", e);
		}
		sendMail(admin);
		return "successfully added admin";
	}

	private String getAdminId() {
		List<Admin> listOfAdmins = adminRepo.findAll();
		int n = listOfAdmins.size();
		String s = "A05000";
		if (n < 10) {
			s += "0";
			s += n;
		} else
			s += n;

		return s;
	}

	@Override
	public String sendMail(Admin admin) throws ServiceException {
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("adminId", admin.getAdminId());
			model.put("state", admin.getState());
			model.put("emailId", admin.getEmailId());
			model.put("password", admin.getPassword());

			Context context = new Context();
			context.setVariables(model);
			String htmlPage = tempEngine.process("adminSignUpTemp", context);
			helper.setTo(admin.getEmailId());
			helper.setText(htmlPage, true);
			helper.setSubject("Credentials for login and usage purposes ");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		sender.send(message);
		return "sent mail";
	}

	@Override
	public String passwordMail(Map<String, String> map) throws ServiceException {
		MimeMessage message = sender.createMimeMessage();
		try {
			SuperAdmin sAdmin = sAdminRepo.findById(Integer.parseInt(map.get("userId"))).get();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("password", sAdmin.getPassword());

			Context context = new Context();
			context.setVariables(model);
			String htmlPage = tempEngine.process("passwordTemp", context);
			helper.setTo(sAdmin.getEmailId());
			helper.setText(htmlPage, true);
			helper.setSubject("Password for logging into the system");
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		sender.send(message);
		return "sent mail";
	}
}
