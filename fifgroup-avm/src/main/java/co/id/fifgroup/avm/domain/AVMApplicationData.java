/**
 * Copyright (c) 2012 FIF. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of FIF.
 * ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * FIF; other than in accordance with the written permission of FIF. <BR>
 * 
 * Created on: Jun 22, 2010
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */

package co.id.fifgroup.avm.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMApplicationData implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1112627180067377271L;
	private int sequenceNumber;
	private UUID avmTrxId;
	private byte[] serializedData;
	private int trxType;
	private Date actionTime;
	private int actionType;
	private Date submitTime;
	private UUID submitterId;
	private String remarks;
	
	private String subject;
	private Long companyId;
	
	public Date getActionTime() {
		return actionTime;
	}
	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public UUID getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(UUID submitterId) {
		this.submitterId = submitterId;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public UUID getAvmTrxId() {
		return avmTrxId;
	}
	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
	}
	public byte[] getSerializedData() {
		return serializedData;
	}
	public void setSerializedData(byte[] serializedData) {
		this.serializedData = serializedData;
	}
	public int getTrxType() {
		return trxType;
	}
	public void setTrxType(int trxType) {
		this.trxType = trxType;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "AVMApplicationData [sequenceNumber=" + sequenceNumber
				+ ", avmTrxId=" + avmTrxId + ", serializedData="
				+ Arrays.toString(serializedData) + ", trxType=" + trxType
				+ ", actionTime=" + actionTime + ", submitTime=" + submitTime
				+ ", submitterId=" + submitterId + ", remarks=" + remarks + "]";
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public int getActionType() {
		return actionType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
}
