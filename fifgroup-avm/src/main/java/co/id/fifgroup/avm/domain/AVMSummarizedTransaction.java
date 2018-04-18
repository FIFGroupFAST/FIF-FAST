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

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMSummarizedTransaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6112409322246430683L;
	private int taskCount;
	private int trxType;
	private int actionType;
	
	public int getCount() {
		return taskCount;
	}
	public void setCount(int count) {
		this.taskCount = count;
	}
	public int getTrxType() {
		return trxType;
	}
	public void setTrxType(int trxType) {
		this.trxType = trxType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public int getActionType() {
		return actionType;
	}
	
}
