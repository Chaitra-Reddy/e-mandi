package com.mindtree.EMandi;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.EMandi.EMandiApplication;
import com.mindtree.EMandi.exception.service.CropServiceException;
import com.mindtree.EMandi.modules.crop.converter.CropVarietyConverter;
import com.mindtree.EMandi.modules.crop.dto.CropVarietyDto;
import com.mindtree.EMandi.modules.crop.entity.CropVariety;
import com.mindtree.EMandi.modules.crop.service.CropService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EMandiApplication.class)
//@ContextConfiguration(classes = EMandiApplication.class)
@AutoConfigureMockMvc
class CropVarietyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CropService cService;
	
	@MockBean
	private CropVarietyConverter cropVConverter;

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void testUpdateCropPriceForBuyer() {
		Map<String, String> map= new HashMap<>();
		CropVariety cVariety= new CropVariety();
		map.put("cropName", "wheat");
		map.put("cropClass", "A");
		map.put("cropPrice", "120");
		map.put("adminId", "11");
		cVariety.setCropClass("A");
	CropVarietyDto cVarietyDto= new CropVarietyDto();
		try {
			doReturn(cVariety).when(cService).updateCropCostForBuyer("wheat","A","120", "11");
		} catch (CropServiceException e) {
			
		}
		doReturn(cVarietyDto).when(cropVConverter).entityToDto(cVariety);
		String json;
		try {
			 json= mapper.writeValueAsString(map);
			 mockMvc.perform(put("/cropVariety/updateCropPrice").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
		              .content(json).accept(MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			
		}
		
		
	}

}
