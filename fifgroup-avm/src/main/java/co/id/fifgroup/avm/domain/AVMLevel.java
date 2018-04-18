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

public class AVMLevel implements Serializable, Traversable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2627322045944392098L;
	private UUID avmId;
    private int levelSequence;
    private int numberOfApprovals;
    private String rule;
    private String levelName;
    
    private int avmVersionId;
    private int levelMethod;
    
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
    
    public int getNumberOfApprovals() {
        return numberOfApprovals;
    }

    public void setNumberOfApprovals(int numberofapprovals) {
        this.numberOfApprovals = numberofapprovals;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}
	
	public int getAvmVersionId() {
		return avmVersionId;
	}

	public void setAvmVersionId(int avmVersionId) {
		this.avmVersionId = avmVersionId;
	}

	public int getLevelMethod() {
		return levelMethod;
	}

	public void setLevelMethod(int levelMethod) {
		this.levelMethod = levelMethod;
	}

	@Override
	public Object getId() {
		return avmId.toString() + "|" + levelSequence;
	}
}
