package co.id.fifgroup.workstructure.organization.level.test;

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
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;

import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:workstructureTest-context.xml")
@Transactional(readOnly=false)
@TransactionConfiguration(defaultRollback=true)
public class OrganizationLevelSetupTest {
	

	@Autowired
	private OrganizationLevelSetupService orgLevelService;
	
	@Test
	public void organizationLevelTest() throws Exception{
		
		OrganizationLevelDto level = new OrganizationLevelDto();
		level.setCompanyId(1L);
		level.setStartDate(new Date());
		level.setEndDate(DateUtil.MAX_DATE);
		level.setCode("DIV");
		level.setName("Division");
		level.setDescription("division level");
		level.setColor("FFFFFF");
		
		orgLevelService.save(level);
		
		assertNotNull(level.getId());
		
	}
}
