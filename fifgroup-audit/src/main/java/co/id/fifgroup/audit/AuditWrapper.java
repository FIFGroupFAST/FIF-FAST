package co.id.fifgroup.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * A simple audit information wrapper to-be-used in asynchronous calls.  
 * @author kiton
 *
 */
public class AuditWrapper implements Serializable{
	private static final long serialVersionUID = 1L;
	private UUID auditId;
	private UUID sessionId;
	private Object previousObject;
	private Object newObject;
	private int moduleId;
	private ControlType.Control controlType;
	private ActionType.Action actionType;
	private Date timestamp;
	
	public AuditWrapper(){}
	
	public AuditWrapper(UUID auditId, UUID sessionId, Object previousObject, Object newObject, int moduleId, 
			ControlType.Control controlType, ActionType.Action actionType, Date timestamp){
		this.auditId = auditId;
		this.sessionId = sessionId;
		this.previousObject = previousObject;
		this.newObject = newObject;
		this.moduleId = moduleId;
		this.controlType = controlType;
		this.actionType = actionType;
		this.timestamp = timestamp;
	}
	
	public UUID getAuditId() {
		return auditId;
	}
	public void setAuditId(UUID auditId) {
		this.auditId = auditId;
	}
	public UUID getSessionId() {
		return sessionId;
	}
	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}
	public Object getPreviousObject() {
		return previousObject;
	}
	public void setPreviousObject(Object previousObject) {
		this.previousObject = previousObject;
	}
	public Object getNewObject() {
		return newObject;
	}
	public void setNewObject(Object newObject) {
		this.newObject = newObject;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public ControlType.Control getControlType() {
		return controlType;
	}
	public void setControlType(ControlType.Control controlType) {
		this.controlType = controlType;
	}
	public ActionType.Action getActionType() {
		return actionType;
	}
	public void setActionType(ActionType.Action actionType) {
		this.actionType = actionType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
