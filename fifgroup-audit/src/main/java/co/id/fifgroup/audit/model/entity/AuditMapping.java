package co.id.fifgroup.audit.model.entity;

public class AuditMapping {

	private int mappingId;
	private int controlType;
	private String className;
	private String attributeName;
	private String attributeLabel;
	private String format;
	private int moduleId;
	private int flagRecordCode;
	private int flagResolveCode;
	private String lookupCategory;
	
	public int getMappingId() {
		return mappingId;
	}
	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}
	public int getControlType() {
		return controlType;
	}
	public void setControlType(int actionId) {
		this.controlType = actionId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeLabel() {
		return attributeLabel;
	}
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public void setFlagRecordCode(int flagRecordCode) {
		this.flagRecordCode = flagRecordCode;
	}
	public int getFlagRecordCode() {
		return flagRecordCode;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getFlagResolveCode() {
		return flagResolveCode;
	}
	public void setFlagResolveCode(int flagResolveCode) {
		this.flagResolveCode = flagResolveCode;
	}
	public String getLookupCategory() {
		return lookupCategory;
	}
	public void setLookupCategory(String lookupCategory) {
		this.lookupCategory = lookupCategory;
	}
	@Override
	public String toString() {
		return "AuditMapping [attributeLabel=" + attributeLabel
				+ ", attributeName=" + attributeName + ", className="
				+ className + ", controlType=" + controlType + ", mappingId="
				+ mappingId + ", moduleId=" + moduleId + "]";
	}
	
	
}
