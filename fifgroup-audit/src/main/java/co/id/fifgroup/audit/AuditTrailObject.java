package co.id.fifgroup.audit;

import java.io.Serializable;

import co.id.fifgroup.audit.model.entity.AuditTrail;


public class AuditTrailObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3632818654610447005L;
	private AuditTrail auditTrail;
	private Object currentObject;
	private Object newObject;

	public AuditTrailObject(AuditTrail auditTrail, Object currentObject,
			Object newObject) {
		this.setAuditTrail(auditTrail);
		this.setCurrentObject(currentObject);
		this.setNewObject(newObject);
	}

	public void setAuditTrail(AuditTrail auditTrail) {
		this.auditTrail = auditTrail;
	}

	public AuditTrail getAuditTrail() {
		return auditTrail;
	}

	public void setCurrentObject(Object currentObject) {
		this.currentObject = currentObject;
	}

	public Object getCurrentObject() {
		return currentObject;
	}

	public void setNewObject(Object newObject) {
		this.newObject = newObject;
	}

	public Object getNewObject() {
		return newObject;
	}

	@Override
	public String toString() {
		return "AuditTrailObject [auditTrail=" + auditTrail
				+ ", currentObject=" + currentObject + ", newObject="
				+ newObject + "]";
	}
}
