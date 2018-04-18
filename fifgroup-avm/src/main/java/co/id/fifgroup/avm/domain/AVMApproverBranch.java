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

public class AVMApproverBranch implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 607763986580668019L;
	private UUID approverId;
    private UUID avmId;
    private int levelSequence;
    private int priorityLevel;
    private String approverName;
    private UUID branchId;
    private String branchName;
    private short approverType;
    

    public UUID getApproverId() {
        return approverId;
    }

    public void setApproverId(UUID approverid) {
        this.approverId = approverid;
    }

    public UUID getAvmId() {
        return avmId;
    }

    public void setAvmId(UUID avmid) {
        this.avmId = avmid;
    }

    public int getLevelSequence() {
        return levelSequence;
    }

    public void setLevelSequence(int levelsequence) {
        this.levelSequence = levelsequence;
    }

	public void setPriorityLevel(int priority) {
		this.priorityLevel = priority;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setBranchId(UUID branchId) {
		this.branchId = branchId;
	}

	public UUID getBranchId() {
		return branchId;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setApproverType(short approverType) {
		this.approverType = approverType;
	}

	public short getApproverType() {
		return approverType;
	}

}
