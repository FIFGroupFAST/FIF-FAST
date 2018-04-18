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

public class AVM implements Serializable, Traversable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7660696683233299194L;
	private UUID avmId;
	private String avmName;
	private int thresholdType;
	private long threshold;

	public UUID getAvmId() {
		return avmId;
	}

	public void setAvmId(UUID avmId) {
		this.avmId = avmId;
	}

	public String getAvmName() {
		return avmName;
	}

	public void setAvmName(String avmname) {
		this.avmName = avmname;
	}

	public int getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(int thresholdtype) {
		this.thresholdType = thresholdtype;
	}

	public long getThreshold() {
		return threshold;
	}

	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public Object getId() {
		return getAvmId();
	}
}
