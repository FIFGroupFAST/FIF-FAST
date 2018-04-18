package co.id.fifgroup.avm.test;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.exception.FifException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:avm-context-test.xml"})
public class AVMLevelTest {
	
	@Autowired
	AVMEngine avmEngine;
	
	@Test
	public void getAVMLevel() throws FifException {
		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
		List<AVMLevel> avmLevels = avmEngine.getAVMLevels(avmId);
		System.out.println(avmLevels.size());
	}
	
//	@Test
//	public void getApplicableLevelSequence() throws FifException {
//		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
//		int levelSequence = 0;
//		Object application = new Object();
//		AVMLevel avmLevel = engine.getApplicableLevelSequence(avmId, levelSequence, application);
//		junit.framework.Assert.assertEquals("level1", avmLevel.getLevelName());
//	}
	
//	@Test
//	public void createAVMLevel() throws FifException {
//		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
//		int requiredNumberOfApprovals = 1;
//		String ruleExpression = "";
//		engine.createAVMLevel(avmId, requiredNumberOfApprovals, ruleExpression);
//	}
	
//	@Test
//	public void getLastApproverResponseTransaction() throws FifException {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("avm-context.xml");
//		AVMTransactionManager manager = (AVMTransactionManager) ctx.getBean("avmTransactionManager");
//		UUID avmId = UUID.fromString("43BBE226-E307-4A79-98E0-538D5750D4B3");
////		int trxType = AVMActionType.APPROVE_TRX.getCode();
//		int trxType = AVMActionType.REJECT_TRX.getCode();
//		AVMRejectionDoer avmApprover = manager.getLastApproverResponseTransaction(avmId, trxType);
//		System.out.println(avmApprover.getApproverId() + " - " + avmApprover.getSequenceNumber() + " - "
//				+ avmApprover.getRemarks());
//		
//	}
}
