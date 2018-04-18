package co.id.fifgroup.workstructure.job.test;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.workstructure.dto.JobDTO;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional(readOnly=false)
@TransactionConfiguration(defaultRollback=false)
public class JobSetupTest {
	
	@Autowired
	private JobSetupService service;
	
	@Test
	public void addJob() throws Exception {
		JobDTO job = new JobDTO();
		job.setCompanyId(1L);
		job.setJobCode("Job code");
		job.setJobName("Officer");
		job.setIsManager(false);
		job.setDateFrom(new Date());
		job.setDateTo(DateUtil.MAX_DATE);
		job.setCreatedBy(1L);
		job.setCreationDate(new Date());
		job.setLastUpdatedBy(1L);
		job.setLastUpdateDate(new Date());
		
		service.save(job);
		
		assertNotNull(job.getId());
		assertNotNull(job.getVersionId());
		assertNotNull(job.getVersionNumber());
		
		//GradeDto result = service.findByIdAndVersionNumber(grade.getId(), grade.getVersionNumber());
		
		//assertEquals(4, result.getGradeRates().size());
	}
	
}
