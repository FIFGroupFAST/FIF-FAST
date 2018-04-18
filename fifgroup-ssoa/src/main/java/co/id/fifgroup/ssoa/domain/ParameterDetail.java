package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class ParameterDetail {
	
	private String parameterDtlCode;
	private String parameterCode;
	private String parameterDtlName;
	private String lineNo;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private Integer sequence;
	private Parameter parameter;

	public ParameterDetail() {
		
	}

	public String getParameterDtlCode() {
		return parameterDtlCode;
	}

	public void setParameterDtlCode(String parameterDtlCode) {
		this.parameterDtlCode = parameterDtlCode;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterDtlName() {
		return parameterDtlName;
	}

	public void setParameterDtlName(String parameterDtlName) {
		this.parameterDtlName = parameterDtlName;
	}

	

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}


	

	
	
	
	
}
