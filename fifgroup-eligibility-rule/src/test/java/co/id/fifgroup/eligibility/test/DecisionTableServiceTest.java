package co.id.fifgroup.eligibility.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.eligibility.dto.Result;
import co.id.fifgroup.eligibility.dto.Results;
import co.id.fifgroup.eligibility.service.DecisionTableService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional(readOnly=true)
public class DecisionTableServiceTest {
	
	@Autowired
	private DecisionTableService decisionTableService;

	public void testParameterExtractor() {
	
		String query = "select job_id as \"job\" organization_id as \"organization\" grade_id as \"grade\" from person_dummies where person_id = #{person_id, jdbcType=NUMERIC}"; 
		Pattern pattern = Pattern.compile("\\#\\{(.*),");
		Matcher matcher = pattern.matcher(query);
		while (matcher.find()) {
			System.out.println(matcher.group(1));
		}
		
		System.out.println(12 == new Long(12));
		
		
		
	}
	//@Test
	public void testExecuteUsingParam() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", 240L);
		params.put("organizationId", 234L);
		Results res = decisionTableService.execute("ELR", 63L, null, null, params );
		for (Result result : res.getElements()) {
			System.out.println(result);
		}
	}
	@Test
	public void testExecute() throws Exception {
		Results res = decisionTableService.execute("ELR", 103L, 1778L, new Date(), null);
		for (Result result : res.getElements()) {
			System.out.println(result);
		}
	}
}
