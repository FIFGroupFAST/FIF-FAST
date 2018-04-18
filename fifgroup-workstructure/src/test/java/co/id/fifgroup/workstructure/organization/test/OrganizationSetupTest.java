package co.id.fifgroup.workstructure.organization.test;

import static org.junit.Assert.assertNotNull;

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
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationContactDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:workstructureTest-context.xml")
@Transactional(readOnly=false)
@TransactionConfiguration(defaultRollback=true)
public class OrganizationSetupTest {
	

	@Autowired
	private OrganizationSetupService service;
	
	@Test
	public void organizationCreationTest() throws Exception{
		
		OrganizationDTO organization = new OrganizationDTO();
		organization.setCompanyId(1L);
		organization.setDateFrom(new Date());
		organization.setDateTo(DateUtil.MAX_DATE);
		organization.setCode("107");
		organization.setName("107 Sukabumi Credit");
		organization.setDescription("Sukabumi Credit Organization");
		organization.setLevelCode("TEST");
		organization.setNpwp("10101010011");
		organization.setKppCode("TEST");
		organization.setLocation(new LocationDTO());
		organization.setOrganizationHeadId(1L);
		organization.setPicId(null);
		organization.setOrganizationHeadId(1L);
		organization.setIsHeadOffice(false);
		organization.setOrganizationSpvId(null);
		organization.setBranchCode("107");
		organization.setCostCenterCode("TEST");
		organization.setAddress("Address test test test");
		organization.setKelurahanCode("001");
		organization.setKecamatanCode("001");
		organization.setZipCode("001");
		
		organization.setContacts(Arrays.asList(
				new OrganizationContactDTO("Phone", "021-0091282", new Date(), new Date()),
				new OrganizationContactDTO("WhatsApp", "021-0091282", new Date(), new Date()),
				new OrganizationContactDTO("Blackberry PIN", "0091282", new Date(), new Date())
			));
		
		
		service.save(organization);
		
		assertNotNull(organization.getId());
		assertNotNull(organization.getVersionId());
		
		OrganizationDTO result = service.findByIdAndVersionNumber(organization.getId(), organization.getVersionNumber());
		System.out.println(result);
		
	}
}
