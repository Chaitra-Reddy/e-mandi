package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.mandi.entity.Mandi;
import com.mindtree.EMandi.modules.mandi.repository.MandiRepository;
import com.mindtree.EMandi.modules.mandi.service.MandiService;
import com.mindtree.EMandi.modules.superadmin.entity.SuperAdmin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ContextConfiguration(classes = EMandiApplication.class)
public class MandiServiceTest 
{
	@Autowired
	MandiService service;
	
	@MockBean
	MandiRepository repo;
	
	@Test
	@DisplayName("GetMandiByMandiPincodeChecking")
	void testGetMandiByMandiPincode() 
	{
		Mandi mandi = new Mandi(101010,100);
		repo.save(mandi);
		
		int mandiPincode = 101010;
		
		Mockito.when(repo.findById(mandiPincode)).thenReturn(Optional.of(mandi));
		
		try 
		{
			Assertions.assertEquals(mandi.getMandiPincode(),service.getMandiByMandiPincode(mandiPincode).getMandiPincode());
			Assertions.assertEquals(mandi.getStorage(),service.getMandiByMandiPincode(mandiPincode).getStorage());
		} 
		catch (Exception e) 
		{

		}
	}
	
	@Test
	@DisplayName("AddMandiChecking")
	void testAddMandi() 
	{
		Mandi mandi = new Mandi(101010,100);
		repo.save(mandi);
		
		Mockito.when(repo.save(mandi)).thenReturn(mandi);
		
		try 
		{
			Assertions.assertEquals(mandi,service.addMandi(mandi));
		} 
		catch (Exception e) 
		{
		
		}
	}
	
	@Test
	@DisplayName("UpdateMandiStorageChecking")
	void testUpdateMandiStorage() 
	{
		Mandi mandi1 = new Mandi(101010,100);
		repo.save(mandi1);
		mandi1.setStorage(50);
		Mandi mandi2 = new Mandi(101010,50);
		
		Mockito.when(repo.save(mandi1)).thenReturn(mandi2);
		
		try 
		{
			assertNotNull(service.updateMandiStorage(1010101, 50));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("GetMandiByAdminIdChecking")
    public void testGetMandiByAdminId()
    {
        List<Mandi> mandis = new ArrayList<Mandi>();
        SuperAdmin sAdmin = new SuperAdmin(0,"superadmin@0");
        Admin admin = new Admin("0", "admin0", "admin0@gmail.com", "karnataka", sAdmin);
        Mandi mandi = new Mandi(101010,100,admin);
        repo.save(mandi);
        mandis.add(mandi);
        
        String adminId = "0";
        
        Mockito.when(repo.findMandiByAdminId(adminId)).thenReturn(mandis);
        
        try
        {
        	Assertions.assertEquals(mandis.get(0).getMandiPincode(),service.getMandiByAdminId(adminId).stream().findFirst().get().getMandiPincode());
        }
        catch(Exception e)
        {
        	
        }
    }
	
//	@Test
//	@DisplayName("UpdateMandiStorageChecking")
//    public void updateMandiStorageTest()
//    {
//		
//	Mandi mandi =new Mandi();
//	mandi.setMandiPincode(800006);
//	mandi.setStorage(100);
//	
//	
//	Mockito.when(repo.save(mandi)).thenReturn(mandi);
//	
//	assertNotNull(service.updateMandiStorage(800006, 10));
//    }
	
	
}
