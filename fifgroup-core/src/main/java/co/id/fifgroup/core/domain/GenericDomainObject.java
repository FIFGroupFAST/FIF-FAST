package co.id.fifgroup.core.domain;

public class GenericDomainObject implements DomainObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer objectVersion;
	private AuditInfo auditInfo;
	
	@Override
	public Integer getObjectVersion() {
		return objectVersion;
	}

	@Override
	public void setObjectVersion(Integer objectVersion) {
		this.objectVersion = objectVersion;
		
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

}
