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
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMOutgoingReport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6820106775389019539L;
	private UUID avmTrxId;
	private UUID avmId;
	private int trxType;
	private int levelSequence;
	private String levelSequenceName;
	private Date submittedTime;
	private int minimumRequiredApproval;
	private int remainingApproval;
	private int completeApproval;
	private byte[] serializedData;
	private List<AVMApprover> approverList;
	
	private String subject;
	private int avmVersionId;
	private int trxStatus;
	private Long companyId;
    private int flagSuccess;
	
	public UUID getAvmTrxId() {
		return avmTrxId;
	}
	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
	}
	public UUID getAvmId() {
		return avmId;
	}
	public void setAvmId(UUID avmId) {
		this.avmId = avmId;
	}
	public int getTrxType() {
		return trxType;
	}
	public void setTrxType(int trxType) {
		this.trxType = trxType;
	}
	public void setLevelSequence(int levelSequence) {
		this.levelSequence = levelSequence;
	}
	public int getLevelSequence() {
		return levelSequence;
	}
	public void setLevelSequenceName(String levelSequenceName) {
		this.levelSequenceName = levelSequenceName;
	}
	public String getLevelSequenceName() {
		return levelSequenceName;
	}
	public Date getSubmittedTime() {
		return submittedTime;
	}
	public void setSubmittedTime(Date submittedTime) {
		this.submittedTime = submittedTime;
	}
	public int getMinimumRequiredApproval() {
		return minimumRequiredApproval;
	}
	public void setMinimumRequiredApproval(int minimumRequiredApproval) {
		this.minimumRequiredApproval = minimumRequiredApproval;
	}
	public void setRemainingApproval(int remainingApproval) {
		this.remainingApproval = remainingApproval;
	}
	public int getRemainingApproval() {
		return remainingApproval;
	}
	public int getCompleteApproval() {
		return completeApproval;
	}
	public void setCompleteApproval(int completeApproval) {
		this.completeApproval = completeApproval;
	}
	public void setSerializedData(byte[] serializedData) {
		this.serializedData = serializedData;
	}
	public byte[] getSerializedData() {
		return serializedData;
	}
	public void setApproverList(List<AVMApprover> approverList) {
		this.approverList = approverList;
	}
	public List<AVMApprover> getApproverList() {
		return approverList;
	}	
	
	public int getAvmVersionId() {
		return avmVersionId;
	}
	public void setAvmVersionId(int avmVersionId) {
		this.avmVersionId = avmVersionId;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public int getFlagSuccess() {
		return flagSuccess;
	}
	public void setFlagSuccess(int flagSuccess) {
		this.flagSuccess = flagSuccess;
	}
	@Override
	public String toString() {
		return "AVMOutgoingReport [avmTrxId=" + avmTrxId + ", avmId=" + avmId
				+ ", trxType=" + trxType + ", levelSequence=" + levelSequence
				+ ", submittedTime=" + submittedTime
				+ ", minimumRequiredApproval=" + minimumRequiredApproval
				+ ", remainingApproval=" + remainingApproval
				+ ", completeApproval=" + completeApproval + ", approverList="
				+ approverList + "]";
	}
	
	public int getTrxStatus() {
		return trxStatus;
	}
	public void setTrxStatus(int trxStatus) {
		this.trxStatus = trxStatus;
	}	
}
