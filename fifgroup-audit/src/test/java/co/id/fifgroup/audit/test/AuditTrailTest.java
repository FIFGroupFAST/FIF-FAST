package co.id.fifgroup.audit.test;



import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.audit.ActionType;
import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.listtraversal.Address;
import co.id.fifgroup.audit.listtraversal.Customer;
import co.id.fifgroup.audit.objectcopy.DeepCopy;




public class AuditTrailTest {
	
	
	ApplicationContext ctx = new ClassPathXmlApplicationContext("jatis-config.xml");
	/*AuditCaller auditCaller = (AuditCaller) ctx.getBean("auditCaller");*/
	AuditPerformer auditPerformer = (AuditPerformer) ctx.getBean("auditPerformer");
	
	
	
	public void testConnection() {
		/*XmlConfigurationManager.setNamespaceHandlers("com/jatis/audit/config/test/infinity.handlers");*/
		/*XmlConfigurationManager.initDefault("classpath:com/jatis/audit/config/test/jatis-config.xml");*/
	}
	
	@Test
	public void testAuditTrailAsynch() throws InterruptedException, ExecutionException{
		
		System.out.println();
		System.out.println("===================================== TEST INSERT ====================================");
		UUID sessionId = UUID.randomUUID();
		UUID auditId = UUID.randomUUID();
		
		int controlType = 0;
		int actionType = ActionType.getActionValue(Action.SUBMIT);
		int moduleId = 0;
		Date auditTimestamp = new Date();
		
		LinkedList<Address> l = new LinkedList<Address>();
		Customer customer = new Customer();
		customer.setName("dipo");
		Address address1 = new Address();
		address1.setId(10);
		address1.setLiteralAddress("Ragunan");
		l.add(address1);
		customer.setAddresslist(l);
		
		
		LinkedList<Address> l2 = new LinkedList<Address>();
		Customer customer2 = (Customer) DeepCopy.copy(customer);
		Address address2 = new Address();
		address2.setId(10);
		address2.setLiteralAddress("Pasar Minggu");
		l2.add(address2);
		customer2.setAddresslist(l2);
		customer2.setAge(20);
		customer2.setDateOfBirth(new Date());
		customer2.setEmploymentStatus("TEst");

	
		auditPerformer.doAudit(auditId, sessionId, customer, customer2, moduleId, Control.CREATE, Action.SUBMIT);		

		
	}
	
	public void testAuditTrailUpdate() throws InterruptedException{
		System.out.println();
		System.out.println("===================================== TEST UPDATE ====================================");
		UUID sessionId = UUID.randomUUID();
		UUID auditId = UUID.randomUUID();
		
		int controlType = 0;
		int actionType = ActionType.getActionValue(Action.SUBMIT);
		int moduleId = 0;
		Date auditTimestamp = new Date();
		
		LinkedList<Address> l = new LinkedList<Address>();
		Customer customer = new Customer();
		customer.setName("dipo");
		Address address1 = new Address();
		address1.setId(10);
		address1.setLiteralAddress("Ragunan");
		l.add(address1);
		customer.setAddresslist(l);
		LinkedList<Address> l2 = new LinkedList<Address>();
		Customer customer2 = (Customer) DeepCopy.copy(customer);
		customer.setName("bobo");
		Address address2 = new Address();
		address2.setId(10);
		address2.setLiteralAddress("Pasar Minggu");
		l2.add(address2);
		customer2.setAddresslist(l2);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SESSION_ID", sessionId);
		map.put("AUDIT_ID", auditId);
		map.put("CONTROL_TYPE", controlType);
		map.put("ACTION_TYPE", actionType);
		map.put("MODULE_ID", moduleId);
		map.put("AUDIT_DATE", auditTimestamp);
		Map<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("CURRENT_OBJ", null);
		objectMap.put("NEW_OBJ", customer2);
		map.put("TRANSFER_OBJECT", objectMap);
	
		/*AuditCaller caller = (AuditCaller)ConfigurationManagerSupport.getDefaultCommonConfiguration()
				.getSpringContext().getBean(AuditCaller.class);*/
	/*	auditCaller.call(auditId, sessionId, customer, customer2, moduleId, Control.UPDATE, Action.SUBMIT);*/
				
		//This is needed to make sure this main thread not exit before others finished.
		/*AuditPerformer performer = (AuditPerformer)ConfigurationManagerSupport.getDefaultCommonConfiguration()
				.getSpringContext().getBean(AuditPerformer.class);*/
		auditPerformer.waitUntilIdle(30000);
	}
}
