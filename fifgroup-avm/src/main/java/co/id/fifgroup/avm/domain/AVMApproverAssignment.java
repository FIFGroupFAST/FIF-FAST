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
import java.util.UUID;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMApproverAssignment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7594894315262080945L;
	private UUID avmTrxId;
	private UUID avmId;
	private int levelSequence;
	private int numberOfApprovals;
	private String rule;
	private String levelName;
	private UUID domainId;
	private UUID approverId;
	private int priority;
	private String avmName;
	private long threshold;
	private int thresholdType;
	private short approvalStatus;
	
	
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
	public int getLevelSequence() {
		return levelSequence;
	}
	public void setLevelSequence(int levelSequence) {
		this.levelSequence = levelSequence;
	}
	public int getNumberOfApprovals() {
		return numberOfApprovals;
	}
	public void setNumberOfApprovals(int numberOfApprovals) {
		this.numberOfApprovals = numberOfApprovals;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public UUID getDomainId() {
		return domainId;
	}
	public void setDomainId(UUID domainId) {
		this.domainId = domainId;
	}
	public UUID getApproverId() {
		return approverId;
	}
	public void setApproverId(UUID approverId) {
		this.approverId = approverId;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getAvmName() {
		return avmName;
	}
	public void setAvmName(String avmName) {
		this.avmName = avmName;
	}
	public long getThreshold() {
		return threshold;
	}
	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}
	public int getThresholdType() {
		return thresholdType;
	}
	public void setThresholdType(int thresholdType) {
		this.thresholdType = thresholdType;
	}
	public short getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(short approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	
}
