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
import java.util.UUID;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMTransaction implements Serializable{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2897274639640473459L;
	private UUID avmId;
	private UUID avmTrxId;
	private int levelSequence;
    private int remainingApproval;
    private int avmTrxStatus;
    private Date avmTimeStamp;
    private byte[] serializedData;
    private int trxType;
    
    private String subject;
    private int avmVersionId;
    private Long companyId;
    private int flagSuccess;
    private int flagOpen;
	
	public UUID getAvmId() {
		return avmId;
	}

	public void setAvmId(UUID avmid) {
	    this.avmId = avmid;
	}

	public UUID getAvmTrxId() {
	    return avmTrxId;
	}

	public void setAvmTrxId(UUID avmtrxid) {
	    this.avmTrxId = avmtrxid;
	}

	public int getLevelSequence() {
	    return levelSequence;
	}

	public void setLevelSequence(int levelsequence) {
	    this.levelSequence = levelsequence;
	}

    public int getRemainingApproval() {
        return remainingApproval;
    }

    public void setRemainingApproval(int remainingapproval) {
        this.remainingApproval = remainingapproval;
    }

    public int getAvmTrxStatus() {
        return avmTrxStatus;
    }
    
    public void setAvmTrxStatus(int avmtrxstatus) {
        this.avmTrxStatus = avmtrxstatus;
    }

	public void setAvmTimeStamp(Date avmTimeStamp) {
		this.avmTimeStamp = avmTimeStamp;
	}

	public Date getAvmTimeStamp() {
		return avmTimeStamp;
	}

	public void setSerializedData(byte[] serializedData) {
		this.serializedData = serializedData;
	}

	public byte[] getSerializedData() {
		return serializedData;
	}

	public void setTrxType(int trxType) {
		this.trxType = trxType;
	}

	public int getTrxType() {
		return trxType;
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

	public int getFlagOpen() {
		return flagOpen;
	}

	public void setFlagOpen(int flagOpen) {
		this.flagOpen = flagOpen;
	}
	
	
}
