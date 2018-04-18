package co.id.fifgroup.audit.impl;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.audit.ActionType;
import co.id.fifgroup.audit.AttributeComparator;
import co.id.fifgroup.audit.AuditDAO;
import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.AuditTrailObject;
import co.id.fifgroup.audit.AuditWrapper;
import co.id.fifgroup.audit.ControlType;
import co.id.fifgroup.audit.ToTraversableConverter;
import co.id.fifgroup.audit.TraversableMap;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.model.entity.AuditDetail;
import co.id.fifgroup.audit.model.entity.AuditMapping;
import co.id.fifgroup.audit.model.entity.AuditTrail;



/**
 * This is THE entry point to audit engine.
 * @author kiton
 */
@Service("auditPerformer")
@Transactional
public class DefaultAuditPerformer implements AuditPerformer{
	private SqlSessionFactory sqlSessionFactory;
	
	private AuditDAO dao = new DefaultAuditDAO();
	private static final Logger log = LoggerFactory.getLogger(DefaultAuditPerformer.class);
	private static final String PREFIX_GET= "get";
	private static final String RETURN_TYPE_DATE = "Date";
	private int noOfActiveProcesses = 0;
	private final TraversableMap traversableMap = new DefaultTraversableMap();
	private ToTraversableConverter toTraversableConverter = new DefaultToTraversableConverter();
	private Class<? extends AttributeComparator> attributeComparatorClass = DefaultAttributeComparator.class;
	
	/**
	 * Get MyBatis SqlSessionFactory of this audit performer
	 * @return sql session factory
	 */
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	/**
	 * Set MyBatis SqlSessionFactory of this audit performer
	 * @param sqlSessionFactory sql session factory
	 */
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.dao.setSqlSessionFactory(this.sqlSessionFactory);
		if (log.isDebugEnabled()){
			log.debug("DAO is using = "+this.dao.getSqlSessionFactory());
		}
	}
	
	/**
	 * Set traversable maps
	 * @param traversableMaps list of traversable maps
	 */
	public void setTraversableMaps(List<TraversableMap> traversableMaps){
		this.traversableMap.addAll(traversableMaps);
	}
	
	/* (non-Javadoc)
	 * @see jatis.audit.AuditPerformer#doAuditWrapped(jatis.audit.AuditWrapper)
	 */
	@Override
	public void doAuditWrapped(AuditWrapper auditWrapper){
		if (log.isDebugEnabled()){
			log.debug("Audit wrapper: " +auditWrapper);
		}
		doAudit(auditWrapper.getAuditId(), auditWrapper.getSessionId(), 
				auditWrapper.getPreviousObject(), auditWrapper.getNewObject(), auditWrapper.getModuleId(),
				auditWrapper.getControlType(), auditWrapper.getActionType(), auditWrapper.getTimestamp());
		//return auditWrapper;
	}

	@Override
	public void doAudit(UUID auditId, UUID sessionId, Object previousObject,
			Object newObject, int moduleId,
			ControlType.Control controlType, ActionType.Action actionType){
		doAudit(auditId, sessionId, previousObject, newObject, moduleId, controlType, actionType,
				new Date());
	}
	/* (non-Javadoc)
	 * @see jatis.audit.AuditPerformer#doAudit(java.util.UUID, java.util.UUID, java.lang.Object, java.lang.Object, int, jatis.audit.ControlType.Control, jatis.audit.ActionType.Action)
	 */
	@Override
	public void doAudit(UUID auditId, UUID sessionId, Object previousObject,
			Object newObject, int moduleId,
			ControlType.Control controlType, ActionType.Action actionType, Date timestamp){
		try {
			this.beginProcess();
			log.info("getting objects to be audited");

			// must be action time of user not the audit time, should add
			// parameter for time
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setSessionId(sessionId);
			auditTrail.setAuditId(auditId);
			auditTrail.setControlType(controlType.getCode());
			auditTrail.setActionType(actionType.getCode());
			auditTrail.setModuleId(moduleId);
			auditTrail.setAuditTimestamp(timestamp == null ? new Date() : timestamp);

			AuditTrailObject auditTrailObject = new AuditTrailObject(
					auditTrail, previousObject, newObject);

			auditTrail = auditTrailObject.getAuditTrail();

			String className = "";
			AuditMapping attributeCode = null;
			String attributeCodeLabel = "";
			String attributeCodeValue = "";

			if (actionType.getCode() == Action.SUBMIT.getCode()
					|| actionType.getCode() == Action.RESUBMIT.getCode()) {

				// if (controlType.getCode() == Control.CREATE.getCode()) {
				// className = currentObject.getClass().getName();
				// attributeCode = dao.getAttributeCode(className, moduleId,
				// controlType.getCode());
				// if (attributeCode != null) {
				// attributeCodeValue = getAttributeCodeValue(currentObject,
				// attributeCode);
				// }
				//
				// }
				// else {
				// className = previousObject.getClass().getName();
				// attributeCode = dao.getAttributeCode(className, moduleId,
				// controlType.getCode());
				// if (attributeCode != null) {
				// attributeCodeValue = getAttributeCodeValue(previousObject,
				// attributeCode);
				// }
				// }
				Object reviewedObject = null;
				if (controlType.getCode() == Control.CREATE.getCode()) {
					if (newObject != null) {
						reviewedObject = newObject;
					} else {
						reviewedObject = previousObject;
					}
				} else {
					if (previousObject != null) {
						reviewedObject = previousObject;
					} else {
						reviewedObject = newObject;
					}
				}

				if (reviewedObject != null) {
					className = reviewedObject.getClass().getName();
					attributeCode = dao.getAttributeCode(className, moduleId,
							controlType.getCode());
					if (attributeCode != null) {
						attributeCodeValue = getAttributeCodeValue(
								reviewedObject, attributeCode);
					}
				}

			}

			// else if (actionType.getCode() == Action.OTHERS.getCode()
			// && newObject != null) {
			// className = newObject.getClass().getName();
			// attributeCode = dao.getAttributeCode(className, moduleId,
			// controlType.getCode());
			// if (attributeCode != null) {
			// attributeCodeValue = getAttributeCodeValue(newObject,
			// attributeCode);
			// }
			// }

			else {
				if (newObject != null) {
					className = newObject.getClass().getName();
					attributeCode = dao.getAttributeCode(className, moduleId,
							controlType.getCode());
					if (attributeCode != null) {
						attributeCodeValue = getAttributeCodeValue(newObject,
								attributeCode);
					}
				} else if (previousObject != null) {
					className = previousObject.getClass().getName();
					attributeCode = dao.getAttributeCode(className, moduleId,
							controlType.getCode());
					if (attributeCode != null) {
						attributeCodeValue = getAttributeCodeValue(
								previousObject, attributeCode);
					}
				}
			}

			if (attributeCode != null) {
				attributeCodeLabel = attributeCode.getAttributeLabel();
			}
			auditTrail.setAttributeCodeLabel(attributeCodeLabel);
			auditTrail.setAttributeCodeValue(attributeCodeValue);
			// long sequenceNumber = dao.insertUserActivity(auditTrail);

			auditTrail = dao.insertUserActivity(auditTrail);

			// check the action type with application transaction type
			if (auditTrail.getActionType() == ActionType
					.getActionValue(ActionType.Action.SUBMIT)
					|| auditTrail.getActionType() == ActionType
							.getActionValue(ActionType.Action.RESUBMIT)
					|| auditTrail.getActionType() == Action.OTHERS.getCode()
					|| auditTrail.getSequenceNumber() == auditTrail
							.getRefSequenceNumber()) {
				// add moduleId
				List<AuditDetail> auditDetailList = doAttributeComparison(
						auditTrailObject.getCurrentObject(),
						auditTrailObject.getNewObject(),
						auditTrail.getControlType(), auditTrail.getAuditId(),
						auditTrail.getModuleId());

				dao.insertAttributesValueChange(auditTrail.getSequenceNumber(),
						auditDetailList);
			}
		} catch (Throwable t){
			log.error("Failed doing audit", t);
		} finally {
			this.endProcess();
		}
	}

	private List<AuditDetail> doAttributeComparison(Object currentObject,
			Object newObject, int controlType, UUID auditId, int moduleId) throws InstantiationException, IllegalAccessException {
		AttributeComparator comparator = this.attributeComparatorClass.newInstance();
		comparator.setSqlSessionFactory(this.sqlSessionFactory);
		comparator.setTraversableMap(this.traversableMap);
		comparator.setToTraversableConverter(this.toTraversableConverter == null ? new DefaultToTraversableConverter() 
			: this.toTraversableConverter);
		comparator.doAttributeComparison(currentObject, newObject, controlType,
				auditId, moduleId);
		
		if (log.isDebugEnabled()){
			if (comparator instanceof DefaultAttributeComparator){
				((DefaultAttributeComparator)comparator).printComparisonList();
			}
		}
		
		List<AuditDetail> auditDetailList = comparator.getAuditDetailList();
		return auditDetailList;
	}
	
	private String getAttributeCodeValue(Object object, AuditMapping attributeCode) {
		
		String attributeName = attributeCode.getAttributeName();
		String upperFirstChar = attributeName.substring(0,1).toUpperCase();
		String methodName = PREFIX_GET + upperFirstChar + attributeName.substring(1);
		String attributeCodeValue = "";
		try {
			Method method = object.getClass().getMethod(methodName);
			Class<?> returnTypeClass = method.getReturnType();
			String returnType = method.getReturnType().getSimpleName();
			Object attributeCodeObject = method.invoke(object);
			if (attributeCodeObject != null) {
				attributeCodeValue = attributeCodeObject.toString();
				if (attributeCode.getFormat() != null && !attributeCode.getFormat().isEmpty()) {
					if ((returnTypeClass.isPrimitive()
							&& isPrimitiveNumberType(returnTypeClass))
						|| isSubClassOfNumber(returnTypeClass)) {
						NumberFormat numberFormat = new DecimalFormat(attributeCode.getFormat());
						if (!attributeCodeValue.isEmpty()) {
							BigDecimal bigDecimal = new BigDecimal(attributeCodeValue);
							long parsedCurrentNumber = bigDecimal.longValue();
							attributeCodeValue = numberFormat.format(parsedCurrentNumber);
						}
						
					}
					else if(returnType.equalsIgnoreCase(RETURN_TYPE_DATE)){
						SimpleDateFormat dateFormatCurrentValue = new SimpleDateFormat(attributeCode.getFormat());
		
						if (!attributeCodeValue.equals("")) {
						//	long current = Long.parseLong(currentValue);
							attributeCodeValue = dateFormatCurrentValue.format(attributeCodeObject);
						}
					}
				}
			}
			
		} catch (SecurityException e) {
			log.error(e.getMessage(),e );
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(),e);
		}
		return attributeCodeValue;
	}
	
	private boolean isPrimitiveNumberType(Class<?> aClass) {
		return (aClass.getName().equals("double") 
				|| aClass.getName().equals("int")
				|| aClass.getName().equals("long")
				|| aClass.getName().equals("short")
				|| aClass.getName().equals("float")
				|| aClass.getName().equals("byte"));
	}
	
	private boolean isSubClassOfNumber(Class<?> aClass) {
		Class<?> superClass = aClass.getSuperclass();
		if (superClass.equals(java.lang.Object.class)) {
			return false;
		}
		else if(superClass.equals(java.lang.Number.class)) {
			return true;
		}
		else {
			return isSubClassOfNumber(aClass);
		}
	}

	@Override
	public boolean isInProgress() {
		synchronized(this){
			return this.noOfActiveProcesses == 0;
		}
	}
	
	/**
	 * Get number of active audit processes
	 * @return
	 */
	public int getNoOfActiveProcesses() {
		synchronized(this){
			return noOfActiveProcesses;
		}
	}

	public void waitUntilIdle(long millis) throws InterruptedException{
		synchronized(this){
			do {
				if (millis < 0){
					this.wait();
				} else {
					this.wait(millis);
				}
			}
			while(this.noOfActiveProcesses > 0);
		}
	}
	
	private void beginProcess(){
		synchronized(this){
			++this.noOfActiveProcesses;
		}
	}
	
	private void endProcess(){
		synchronized(this){
			--this.noOfActiveProcesses;
			if (this.noOfActiveProcesses <= 0){
				this.notifyAll();
			}
		}
	}

	@Override
	public void setAuditDAO(AuditDAO dao) {
		if (dao != null){
			this.dao = dao;
		}
	}

	@Override
	public void setAttributeComparatorClass(
			Class<? extends AttributeComparator> attributeComparator) {
		this.attributeComparatorClass = attributeComparator;
	}

	@Override
	public void setToTraversableConverter(
			ToTraversableConverter toTraversableConverter) {
		this.toTraversableConverter = toTraversableConverter;
	}

	@Override
	public TraversableMap getDefaultTraversableMap() {
		return this.traversableMap;
	}

	@Override
	public void doAudit(UUID auditId, UUID sessionId, Object previousObject,
			Object newObject, int moduleId, Control controlType,
			Action actionType, Long userId, String trxType, Long trxId) {
		try {
			this.beginProcess();
			log.info("getting objects to be audited");

			// must be action time of user not the audit time, should add
			// parameter for time
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setSessionId(sessionId);
			auditTrail.setAuditId(auditId);
			auditTrail.setControlType(controlType.getCode());
			auditTrail.setActionType(actionType.getCode());
			auditTrail.setModuleId(moduleId);
			auditTrail.setAuditTimestamp(new Date());
			auditTrail.setUserId(userId);
			auditTrail.setTransactionType(trxType);
			auditTrail.setTrxId(trxId);

			AuditTrailObject auditTrailObject = new AuditTrailObject(
					auditTrail, previousObject, newObject);

			auditTrail = auditTrailObject.getAuditTrail();

			String className = "";
			AuditMapping attributeCode = null;
			String attributeCodeLabel = "";
			String attributeCodeValue = "";

			if (actionType.getCode() == Action.SUBMIT.getCode()
					|| actionType.getCode() == Action.RESUBMIT.getCode()) {

				Object reviewedObject = null;
				if (controlType.getCode() == Control.CREATE.getCode()) {
					if (newObject != null) {
						reviewedObject = newObject;
					} else {
						reviewedObject = previousObject;
					}
				} else {
					if (previousObject != null) {
						reviewedObject = previousObject;
					} else {
						reviewedObject = newObject;
					}
				}

				if (reviewedObject != null) {
					className = reviewedObject.getClass().getName();
					attributeCode = dao.getAttributeCode(className, moduleId,
							controlType.getCode());
					if (attributeCode != null) {
						attributeCodeValue = getAttributeCodeValue(
								reviewedObject, attributeCode);
					}
				}

			}

			else {
				if (newObject != null) {
					className = newObject.getClass().getName();
					attributeCode = dao.getAttributeCode(className, moduleId,
							controlType.getCode());
					if (attributeCode != null) {
						attributeCodeValue = getAttributeCodeValue(newObject,
								attributeCode);
					}
				} else if (previousObject != null) {
					className = previousObject.getClass().getName();
					attributeCode = dao.getAttributeCode(className, moduleId,
							controlType.getCode());
					if (attributeCode != null) {
						attributeCodeValue = getAttributeCodeValue(
								previousObject, attributeCode);
					}
				}
			}

			if (attributeCode != null) {
				attributeCodeLabel = attributeCode.getAttributeLabel();
			}
			auditTrail.setAttributeCodeLabel(attributeCodeLabel);
			auditTrail.setAttributeCodeValue(attributeCodeValue);
			// long sequenceNumber = dao.insertUserActivity(auditTrail);

			auditTrail = dao.insertUserActivity(auditTrail);

			// check the action type with application transaction type
			if (auditTrail.getActionType() == ActionType
					.getActionValue(ActionType.Action.SUBMIT)
					|| auditTrail.getActionType() == ActionType
							.getActionValue(ActionType.Action.RESUBMIT)
					|| auditTrail.getActionType() == Action.OTHERS.getCode()
					|| auditTrail.getSequenceNumber() == auditTrail
							.getRefSequenceNumber()) {
				// add moduleId
				List<AuditDetail> auditDetailList = doAttributeComparison(
						auditTrailObject.getCurrentObject(),
						auditTrailObject.getNewObject(),
						auditTrail.getControlType(), auditTrail.getAuditId(),
						auditTrail.getModuleId());

				dao.insertAttributesValueChange(auditTrail.getSequenceNumber(),
						auditDetailList);
			}
		} catch (Throwable t){
			log.error("Failed doing audit", t);
		} finally {
			this.endProcess();
		}
		
	}

}
