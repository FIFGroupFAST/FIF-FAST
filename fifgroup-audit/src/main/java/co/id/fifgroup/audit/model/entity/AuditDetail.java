package co.id.fifgroup.audit.model.entity;

import java.util.UUID;

public class AuditDetail {
	
	private long sequenceNumber;
	private UUID auditId;
	private UUID attributeId;
	private String attributeName;
	private String attributeCurrent;
	private String attributeNew;
	private int flagResolveCode;
	private UUID parentAttribute;

	public AuditDetail(UUID auditId, UUID attributeId, String attributeName,
			String attributeCurrent, String attributeNew, int flagResolveCode,
			UUID parentAttribute) {
		super();
		this.auditId = auditId;
		this.attributeId = attributeId;
		this.attributeName = attributeName;
		this.attributeCurrent = attributeCurrent;
		this.attributeNew = attributeNew;
		this.flagResolveCode = flagResolveCode;
		this.parentAttribute = parentAttribute;
	}
	
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public long getSequenceNumber() {
		return sequenceNumber;
	}
	
	public void setAuditId(UUID auditId) {
		this.auditId = auditId;
	}
	public UUID getAuditId() {
		return auditId;
	}
	public UUID getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(UUID attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeCurrent() {
		return attributeCurrent;
	}
	public void setAttributeCurrent(String attributeCurrent) {
		this.attributeCurrent = attributeCurrent;
	}
	public String getAttributeNew() {
		return attributeNew;
	}
	public void setAttributeNew(String attributeNew) {
		this.attributeNew = attributeNew;
	}
	public void setFlagResolveCode(int flagResolveCode) {
		this.flagResolveCode = flagResolveCode;
	}

	public int getFlagResolveCode() {
		return flagResolveCode;
	}

	public UUID getParentAttribute() {
		return parentAttribute;
	}
	public void setParentAttribute(UUID parentAttribute) {
		this.parentAttribute = parentAttribute;
	}
	
	public String toString() {
		return "auditId: " + auditId + " attributeId: " + attributeId 
			+ " parentAttribute: " + parentAttribute + " attributeName: " + attributeName
			+ " currentValue: " + attributeCurrent + " newValue: " + attributeNew;
	}
}
