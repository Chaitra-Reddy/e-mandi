package com.mindtree.EMandi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.FarmerException;
import com.mindtree.EMandi.exception.InvalidCropException;
import com.mindtree.EMandi.exception.ServiceException;
import com.mindtree.EMandi.exception.service.CropServiceException;
import com.mindtree.EMandi.modules.admin.entity.Admin;
import com.mindtree.EMandi.modules.crop.entity.Crop;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.repository.CropRepository;
import com.mindtree.EMandi.modules.crop.repository.CropVarietyRepository;
import com.mindtree.EMandi.modules.crop.service.CropService;
import com.mindtree.EMandi.modules.farmer.entity.Farmer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
@AutoConfigureMockMvc
//@ActiveProfiles("cloudx")
//@ContextConfiguration(classes = EMandiApplication.class)
class CropServiceTest {

	@Autowired
	CropService cropService;

	@MockBean
	CropRepository cropRepo;

	@MockBean
	CropVarietyRepository cropVarietyRepo;

	@Test
	@DisplayName("CropServiceChecking1")
	void getCroptest() throws ServiceException {

		List<Crop> cropList = new ArrayList<Crop>();
		Crop crop1 = new Crop(1, "wheat", 11);
		Crop crop2 = new Crop(2, "brown rice", 22);
		cropList.add(crop1);
		cropList.add(crop2);

		Mockito.when(cropRepo.findById(11)).thenReturn(Optional.of(cropList.get(0)));

		assertNotNull(cropService.findCropByAdminId("11"));

	}

	@Test
	@DisplayName("CropServiceChecking2")
	void getCropMsptest() throws ServiceException, InvalidCropException {
		Crop crop = new Crop(1, "wheat", 11);

		Mockito.when(cropRepo.findMSP("wheat", "11")).thenReturn(crop);

		assertNotNull(cropService.getCropMSP("wheat", "11"));

	}

	@Test
	@DisplayName("CropServiceChecking3")
	void findAllCroptest() throws ServiceException {

		List<Crop> cropList = new ArrayList<Crop>();
		Crop crop1 = new Crop(1, "wheat", 11);
		Crop crop2 = new Crop(2, "brown rice", 22);
		cropList.add(crop1);
		cropList.add(crop2);

		Mockito.when(cropRepo.findAll()).thenReturn(cropList);

		assertNotNull(cropService.getAllCrops());

	}

	@Test
	@DisplayName("CropServiceChecking4")
	void addCroptest() throws ServiceException {

		Crop crop1 = new Crop(75, "jaw", 11);

		Mockito.when(cropRepo.save(crop1)).thenReturn(crop1);

		assertNotNull(cropService.addCrop(crop1));

	}

	@Test
	@DisplayName("CropServiceChecking5")
	void getCropCostForBuyertest() throws ServiceException, CropServiceException {

		CropVariety cropVar = new CropVariety(10, "A", 100, 564);
		Crop crop1 = new Crop(1, "wheat", 11);
		Mockito.when(cropRepo.findMSP("wheat", "11")).thenReturn(crop1);

		Mockito.when(cropVarietyRepo.getBuyerCropPrice(1, "A")).thenReturn(cropVar);

		assertNotNull(cropService.getCropCostForBuyer("wheat", "A", "11"));

	}

	@Test
	@DisplayName("CropServiceChecking6")
	void updateMsptest() throws ServiceException, InvalidCropException {

		Admin a= new Admin();
		a.setAdminId("11");
		Crop crop1 = new Crop(1, "wheat", 11);
		crop1.setAdmin(a);
		Crop crop2 = new Crop(1, "wheat", 12);
		crop2.setAdmin(a);
		Mockito.when(cropRepo.findMSP("wheat", "11")).thenReturn(crop1);
		Mockito.when(cropRepo.save(crop1)).thenReturn(crop2);
		
		assertNotNull(cropService.updateMSP(crop2));

	}

	@Test
	@DisplayName("CropServiceChecking7")
	void updateCropCostForBuyertest() throws ServiceException, CropServiceException {

		CropVariety cropVar = new CropVariety(1, "A", 90, 80);
		CropVariety cropVar1 = new CropVariety(1, "A", 90, 300);
		Crop crop1 = new Crop(1, "wheat", 11);
		CropVariety variety = new CropVariety();
		variety.setBuyerCropPrice(300);
		variety.setCrop(crop1);
		variety.setCropClass("A");
		Mockito.when(cropRepo.findMSP("wheat", "11")).thenReturn(crop1);

		Mockito.when(cropVarietyRepo.getBuyerCropPrice(1, "A")).thenReturn(cropVar);

		Mockito.when(cropVarietyRepo.save(variety)).thenReturn(cropVar1);

		assertNull(cropService.updateCropCostForBuyer("wheat", "A", "300", "11"));

	}

}
