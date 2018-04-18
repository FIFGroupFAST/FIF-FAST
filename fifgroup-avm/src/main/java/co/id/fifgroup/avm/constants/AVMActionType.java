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

package co.id.fifgroup.avm.constants;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public enum AVMActionType {
	RESUBMIT_TRX(4), SUBMIT_TRX(3), APPROVE_TRX_DUE_TO_REACH_OF_MIN_APPROVALNUMBERS(2), 
	APPROVE_TRX(1), READY_TO_APPROVE(0), REJECT_TRX(-1), 
	REJECT_DUE_TO_OCCURING_REJECTION(-2), CANCEL_TRX(-3), 
	NOT_READY(-4),
	LIST_PENDING(53), DETAIL_PENDING(54), LIST_REJECTED_TRANSACTIONS(55),
	
	//add yahya
	RE_ASSIGN_BY_ADMIN(5), AUTOAPPROVE_BY_SYSTEM(6);
	
	private int code;
	
	private AVMActionType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}