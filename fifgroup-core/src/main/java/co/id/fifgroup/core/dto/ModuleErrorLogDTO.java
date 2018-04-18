package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

public class ModuleErrorLogDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long errorLogId;
    private Long trxId;
    private Date logTime;
    private String moduleName;
    private String errorMessage;
    
	public Long getErrorLogId() {
		return errorLogId;
	}
	public void setErrorLogId(Long errorLogId) {
		this.errorLogId = errorLogId;
	}
	public Long getTrxId() {
		return trxId;
	}
	public void setTrxId(Long trxId) {
		this.trxId = trxId;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
}
