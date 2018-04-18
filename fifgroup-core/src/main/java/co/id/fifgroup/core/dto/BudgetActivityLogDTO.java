package co.id.fifgroup.core.dto;

import java.io.Serializable;

public class BudgetActivityLogDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String pReferenceNo;
	private String pGuidNo;
	private String pMessage;
	private String pMenuId;
	private String pUserId;
	private String pSeqNo;
	public String getpReferenceNo() {
		return pReferenceNo;
	}
	public void setpReferenceNo(String pReferenceNo) {
		this.pReferenceNo = pReferenceNo;
	}
	public String getpGuidNo() {
		return pGuidNo;
	}
	public void setpGuidNo(String pGuidNo) {
		this.pGuidNo = pGuidNo;
	}
	public String getpMessage() {
		return pMessage;
	}
	public void setpMessage(String pMessage) {
		this.pMessage = pMessage;
	}
	public String getpMenuId() {
		return pMenuId;
	}
	public void setpMenuId(String pMenuId) {
		this.pMenuId = pMenuId;
	}
	public String getpUserId() {
		return pUserId;
	}
	public void setpUserId(String pUserId) {
		this.pUserId = pUserId;
	}
	public String getpSeqNo() {
		return pSeqNo;
	}
	public void setpSeqNo(String pSeqNo) {
		this.pSeqNo = pSeqNo;
	}

}
