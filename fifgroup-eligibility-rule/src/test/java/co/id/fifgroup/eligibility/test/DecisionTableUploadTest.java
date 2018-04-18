package co.id.fifgroup.eligibility.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;
import co.id.fifgroup.eligibility.service.DecisionTableUploadService;
import co.id.fifgroup.eligibility.upload.TemplateGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional(readOnly=false)
@TransactionConfiguration(defaultRollback=false)
public class DecisionTableUploadTest {
	
	@Autowired
	private DecisionTableModelSetupService modelService;
	
	@Autowired
	private TemplateGenerator templateGenerator;
	
	@Autowired
	private DecisionTableUploadService uploadService;
	
	//@Test
	public void testDownloadTemplate() throws Exception {
		DecisionTableModelDTO model = modelService.findByIdAndVersionNumber("test", 1);
		OutputStream os = new FileOutputStream("target" + File.separator +"testDownload.xls");
		os.write(templateGenerator.generate("ELR", model, 1L));
		os.close();
		
	}
	
	@Test
	public void testUpload() throws Exception {
		InputStream is = new ClassPathResource("testUpload.csv").getInputStream();
		uploadService.process(is, true, 1L);
	}
	
	

}
