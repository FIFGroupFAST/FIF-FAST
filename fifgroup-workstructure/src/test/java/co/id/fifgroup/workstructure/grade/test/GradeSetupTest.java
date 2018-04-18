package co.id.fifgroup.workstructure.grade.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.service.GradeSetupService;

import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.GradeRateDTO;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:workstructureTest-context.xml")
@Transactional(readOnly=false)
@TransactionConfiguration(defaultRollback=false)
public class GradeSetupTest {
	
	@Autowired
	private GradeSetupService service;
	
	@Test
	public void testGradeCreation() throws Exception {
		GradeDTO grade = new GradeDTO();
		grade.setName("3A");
		grade.setGrade("3");
		grade.setSubGrade("A");
		grade.setCode("3A");
		grade.setDescription("Grade 3A");
		grade.setDateFrom(new Date());
		grade.setDateTo(DateUtil.MAX_DATE);
		grade.setCreatedBy(1L);
		grade.setCreationDate(new Date());
		grade.setLastUpdatedBy(1L);
		grade.setLastUpdateDate(new Date());
		
		grade.setGradeRates(Arrays.asList(
				new GradeRateDTO(1L, new BigDecimal("1000000"), new BigDecimal("2000000")),
				new GradeRateDTO(2L, new BigDecimal("5000000"), new BigDecimal("20000000")),
				new GradeRateDTO(3L, new BigDecimal("1000000"), new BigDecimal("2000000")),
				new GradeRateDTO(4L, new BigDecimal("1000000"), new BigDecimal("2000000"))
			));
		
		service.save(grade);
		
		assertNotNull(grade.getId());
		assertNotNull(grade.getVersionId());
		assertNotNull(grade.getVersionNumber());
		
		GradeDTO result = service.findByIdAndVersionNumber(grade.getId(), grade.getVersionNumber());
		
		assertEquals(4, result.getGradeRates().size());
	}
	
}
