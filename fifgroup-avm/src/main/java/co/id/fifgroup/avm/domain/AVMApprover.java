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

import co.id.fifgroup.avm.audit.Traversable;
/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMApprover implements Serializable, Traversable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4388037642619298358L;
	private UUID approverId;
	private String approverName;
    private UUID avmId;
    private int levelSequence;
    private int priority;
    private short approverType;
    
    private int avmVersionId;
    private int basedOn;
    
    public UUID getApproverId() {
        return approverId;
    }

    public void setApproverId(UUID approverid) {
        this.approverId = approverid;
    }

    public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverName() {
		return approverName;
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

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setApproverType(short approverType) {
		this.approverType = approverType;
	}

	public short getApproverType() {
		return approverType;
	}
	
	public int getAvmVersionId() {
		return avmVersionId;
	}

	public void setAvmVersionId(int avmVersionId) {
		this.avmVersionId = avmVersionId;
	}
	
	public int getBasedOn() {
		return basedOn;
	}

	public void setBasedOn(int basedOn) {
		this.basedOn = basedOn;
	}

	@Override
	public Object getId() {
		return avmId.toString() + "|" + levelSequence + "|" + approverId.toString();
	}

}
