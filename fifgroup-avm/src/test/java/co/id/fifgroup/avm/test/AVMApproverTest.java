package co.id.fifgroup.avm.test;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.exception.FifException;

public class AVMApproverTest {

	ApplicationContext ctx = new ClassPathXmlApplicationContext("avm-context.xml");
	AVMEngine engine = (AVMEngine) ctx.getBean("avmEngine");
	
	@Test
	public void getTopPriorityApproverList() throws FifException {
		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
		int levelSequence = 0;
		List<AVMApprover> approverList = engine.getTopPriorityApproverList(avmId, levelSequence);
		junit.framework.Assert.assertEquals(2, approverList.size());
 	}
	
	@Test
	public void getAVMApproverList() throws FifException {
		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
		List<AVMApprover> approverList = engine.getAVMApproverList(avmId);
		junit.framework.Assert.assertEquals(2, approverList.size());
	}
}
