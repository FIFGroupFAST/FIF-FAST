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
 * yahya			17/10/2012	 add AVM_RECEIVED_TIME for inquiry/reprot interval time
 *  
 */

package co.id.fifgroup.avm.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMApprovalHistory implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2417488032013349254L;
	private int sequenceNumber;
    private UUID approverId;
    private UUID avmTrxId;
    private int levelSequence;
    private int avmActionType;
    private Date avmActionTimeStamp;
    private String remarks;
    private int priority;
    private int revisionNr;
    
    private Long organizationId;    
    private Date avmReceivedTimeStamp;
    private Long branchId;
    private UUID vacationApproverId;
    
    private List<UUID> approverUUIDs = new ArrayList<UUID>();
    
    public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequencenumber) {
		this.sequenceNumber = sequencenumber;
	}

    public UUID getApproverId() {
        return approverId;
    }

    public void setApproverId(UUID approverid) {
        this.approverId = approverid;
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
    
    public int getAvmActionType() {
		return avmActionType;
	}

    public void setAvmActionType(int avmactiontype) {
        this.avmActionType = avmactiontype;
    }

    public Date getAvmActionTimeStamp() {
        return avmActionTimeStamp;
    }

    public void setAvmActionTimeStamp(Date avmactiontimestamp) {
        this.avmActionTimeStamp = avmactiontimestamp;
    }
 
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setRevisionNr(int revisionNr) {
		this.revisionNr = revisionNr;
	}

	public int getRevisionNr() {
		return revisionNr;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Date getAvmReceivedTimeStamp() {
		return avmReceivedTimeStamp;
	}

	public void setAvmReceivedTimeStamp(Date avmReceivedTimeStamp) {
		this.avmReceivedTimeStamp = avmReceivedTimeStamp;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public UUID getVacationApproverId() {
		return vacationApproverId;
	}

	public void setVacationApproverId(UUID vacationApproverId) {
		this.vacationApproverId = vacationApproverId;
	}

	public List<UUID> getApproverUUIDs() {
		return approverUUIDs;
	}

	public void setApproverUUIDs(List<UUID> approverUUIDs) {
		this.approverUUIDs = approverUUIDs;
	}		
	
}
