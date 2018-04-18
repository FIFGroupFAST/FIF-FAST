package co.id.fifgroup.avm.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMSetupManager;

public class AVMServiceTest {
	
	ApplicationContext ctx = new ClassPathXmlApplicationContext("avm-context.xml");
	AVMEngine engine = (AVMEngine) ctx.getBean("avmEngine");
	AVMSetupManager avmSetupManager = (AVMSetupManager) ctx.getBean("avmSetupManager");
	
	@Test
	public void createAVMTemplate() throws FifException {
		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
		Calendar cal = GregorianCalendar.getInstance();
		cal.clear();
		cal.set(4712, 12, 31);
		Date MAX_DATE = cal.getTime();
		
		AVM avm = new AVM();
		avm.setAvmId(avmId);
		avm.setAvmName("testAVM");
		int versionNumber = 1;
		
		AVMVersions avmVersions = new AVMVersions();
		avmVersions.setAvmId(avmId);
		avmVersions.setVersionNumber(versionNumber);
		avmVersions.setEffectiveStartDate(new Date());
		avmVersions.setEffectiveEndDate(MAX_DATE);
		
		List<AVMLevel> avmLevels = new ArrayList<AVMLevel>();
		AVMLevel avmLevel1 = new AVMLevel();
		avmLevel1.setAvmId(avmId);
		avmLevel1.setLevelSequence(0);
		avmLevel1.setNumberOfApprovals(1);
		avmLevel1.setLevelName("level1");
		avmLevel1.setRule("");
		avmLevels.add(avmLevel1);
		
		AVMLevel avmLevel2 = new AVMLevel();
		avmLevel2.setAvmId(avmId);
		avmLevel2.setLevelSequence(1);
		avmLevel2.setNumberOfApprovals(1);
		avmLevel2.setLevelName("level2");
		avmLevel2.setRule("");
		avmLevels.add(avmLevel2);
		
		List<AVMApprover> avmApprovers = new ArrayList<AVMApprover>();
		UUID approverId1 = UUID.fromString("11111111-1112-1111-1111-111111111111");
		AVMApprover approver = new AVMApprover();
		approver.setApproverId(approverId1);
		approver.setAvmId(avmId);
		approver.setLevelSequence(0);
		approver.setPriority(0);
		avmApprovers.add(approver);
		
		UUID approverId2 = UUID.fromString("11111111-1113-1111-1111-111111111111");
		AVMApprover approver2 = new AVMApprover();
		approver2.setApproverId(approverId2);
		approver2.setAvmId(avmId);
		approver2.setLevelSequence(1);
		approver2.setPriority(0);
		avmApprovers.add(approver2);
	
		engine.createAVMTemplate(avm, avmVersions, avmLevels, avmApprovers);
	}
	
//	@Test
//	public void testSubmitTransaction(){
//		Transaction test = new Transaction();
//		test.setTrxId(UUID.randomUUID());
//		test.setBenefitType("Medical check");
//		test.setAmount(1002300);
//		test.setTrxDate(new Date());
//		
//		int trxType = 1;
//		
//		UUID submitterId = UUID.fromString("77777777-7777-7777-7777-777777777777");
//		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
//		
//		try {		
////			engine.setUpNewAVMTransaction(test.getTrxId(), avmId, submitterId, test, trxType);
//			engine.setUpNewAVMTransaction(test.getTrxId(), avmId, submitterId, test, trxType, (long)0, "subject message 2");
//			
//			System.err.println("submit done");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testRejectTransaction(){
//		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("avm-context.xml");
//		AVMEngine avmEngine = (AVMEngine) ctx.getBean("AVMEngine");
//		
//		UUID trxId = UUID.fromString("B503CCC5-0D18-4A7E-B49F-BF82BEF6E0E8");
//		UUID approverId = UUID.fromString("11111111-1112-1111-1111-111111111111");
//		String remarks = "reject transaction";
//		
//		try {		
//			AVMReturnCode returnCode = avmEngine.rejectTransaction(trxId, approverId, remarks);
//			System.err.println("reject transaction - " + returnCode.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testResubmitTransaction(){
//		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("avm-context.xml");
//		AVMEngine avmEngine = (AVMEngine) ctx.getBean("AVMEngine");
//		
//		UUID trxId = UUID.fromString("B503CCC5-0D18-4A7E-B49F-BF82BEF6E0E8");
//		UUID submitterId = UUID.fromString("77777777-7777-7777-7777-777777777777");
//		Object application = new Object();
//		
//		try {		
//			avmEngine.resubmitTransaction(trxId, submitterId, application);
//			System.err.println("resubmit transaction");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testApproveTransaction(){
//				
//		UUID trxId = UUID.fromString("D37D2A02-CD80-48F1-9854-AB864A892555");
//		UUID approverId = UUID.fromString("11111111-1112-1111-1111-111111111111");
//		UUID approverId = UUID.fromString("11111111-1113-1111-1111-111111111111");
//		String remarks = "approve transaction";
//		
//		try {		
//			engine.approveTransaction(trxId, approverId, remarks);
//			System.err.println("approve transaction");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}	
	
//	@Test
//	public void checkAVMTransactionStatus() throws FifException {
//		UUID trxId = UUID.fromString("B503CCC5-0D18-4A7E-B49F-BF82BEF6E0E8");
//		int code = engine.checkAVMTransactionStatus(trxId);
//		junit.framework.Assert.assertEquals(1, code);
//	}
	
//	@Test
//	public void cancelTransaction() throws FifException {
//		UUID trxId = UUID.fromString("3135DF30-92ED-4097-AE9C-E9BAA93321F3");
//		UUID approverId = UUID.fromString("11111111-1112-1111-1111-111111111111");
//		String remarks = "cancel transaction";
//		AVMReturnCode returnCode = engine.cancelTransaction(trxId, approverId, remarks);
//		junit.framework.Assert.assertEquals(-2, returnCode);
//	}
}
