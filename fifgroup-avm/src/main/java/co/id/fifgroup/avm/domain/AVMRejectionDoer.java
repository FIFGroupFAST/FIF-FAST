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

import java.util.Date;
import java.util.UUID;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMRejectionDoer {

	private UUID approverId;
	private String remarks;
	private Date avmActionTimeStamp;
	
	private int sequenceNumber;
	private int levelSequence;
	
	public UUID getApproverId() {
		return approverId;
	}
	public void setApproverId(UUID approverId) {
		this.approverId = approverId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getAvmActionTimeStamp() {
		return avmActionTimeStamp;
	}
	public void setAvmActionTimeStamp(Date avmActionTimeStamp) {
		this.avmActionTimeStamp = avmActionTimeStamp;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public int getLevelSequence() {
		return levelSequence;
	}
	public void setLevelSequence(int levelSequence) {
		this.levelSequence = levelSequence;
	}
	
	
}
