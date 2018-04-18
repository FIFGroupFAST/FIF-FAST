package co.id.fifgroup.audit;

import java.util.Date;
import java.util.UUID;

public interface AuditCaller {
	/**
	 * Call audit performer
	 * @param auditId Audit ID
	 * @param sessionId Session ID
	 * @param previousObject previous object
	 * @param newObject new object
	 * @param moduleId module ID
	 * @param controlType control type
	 * @param actionType action type
	 */
	public abstract void call(UUID auditId, UUID sessionId,
			Object previousObject, Object newObject, int moduleId,
			ControlType.Control controlType, ActionType.Action actionType);

	/**
	 * Call audit performer
	 * @param auditId Audit ID
	 * @param sessionId Session ID
	 * @param previousObject previous object
	 * @param newObject new object
	 * @param moduleId module ID
	 * @param controlType control type
	 * @param actionType action type
	 * @param timestamp audit timestamp
	 */
	public abstract void call(UUID auditId, UUID sessionId,
			Object previousObject, Object newObject, int moduleId,
			ControlType.Control controlType, ActionType.Action actionType, Date timestamp);
}
