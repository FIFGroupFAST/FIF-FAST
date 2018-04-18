package co.id.fifgroup.audit;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.audit.Traversable;


/**
 * Core audit engine
 * @author kiton
 *
 */
public interface AuditPerformer {
	/**
	 * Set audit DAO
	 * @param dao if not null, this dao will be used by AuditPerformer
	 */
	public void setAuditDAO(AuditDAO dao);
	
	/**
	 * Set attribute comparator class
	 * @param attributeComparator
	 */
	public void setAttributeComparatorClass(Class<? extends AttributeComparator> attributeComparator);
	
	/**
	 * Set to {@link Traversable} converter
	 * @param toTraversableConverter object of the converter
	 */
	public void setToTraversableConverter(ToTraversableConverter toTraversableConverter);
	
	/**
	 * Add traversable maps to this performer
	 * @param traversableMaps traversable maps
	 */
	public void setTraversableMaps(List<TraversableMap> traversableMaps);

	/**
	 * Get default traversable map of this performer
	 * @return TraversableMap
	 */
	public TraversableMap getDefaultTraversableMap();
	
	/**
	 * Generate audit records in database by using a wrapper
	 * @param auditWrapper a wrapper
	 * @return auditWrapper itself
	 */
	public abstract void doAuditWrapped(AuditWrapper auditWrapper);

	/**
	 * Generate audit records in database
	 * @param auditId Audit ID
	 * @param sessionId Session ID
	 * @param previousObject previous object
	 * @param newObject new object
	 * @param moduleId module ID
	 * @param controlType control type
	 * @param actionType action type
	 * @throws JatisRuntimeException Jatis runtime exception
	 */
	public abstract void doAudit(UUID auditId, UUID sessionId,
			Object previousObject, Object newObject, int moduleId,
			ControlType.Control controlType, ActionType.Action actionType);
	
	/**
	 * 
	 * @param auditId Audit ID
	 * @param sessionId Session ID
	 * @param previousObject previous object
	 * @param newObject new object
	 * @param moduleId module ID
	 * @param controlType control type
	 * @param actionType action type
	 * @param timestamp audit timestamp
	 */
	public abstract void doAudit(UUID auditId, UUID sessionId,
			Object previousObject, Object newObject, int moduleId,
			ControlType.Control controlType, ActionType.Action actionType, Date timestamp);

	/**
	 * Check if audit process is currently in-progress
	 * @return true if in-progress
	 */
	public boolean isInProgress();
	
	/**
	 * Wait until no remaining audit processes in-progress.
	 * @param millis maximum waiting time in milliseconds (less than 0 means infinite - not recommended)
	 * @throws InterruptedException 
	 */
	public void waitUntilIdle(long millis) throws InterruptedException;
	
	/**
	 * 
	 */
	public abstract void doAudit(UUID auditId, UUID sessionId,
			Object previousObject, Object newObject, int moduleId,
			ControlType.Control controlType, ActionType.Action actionType, Long userId, String trxType, Long trxId);
}