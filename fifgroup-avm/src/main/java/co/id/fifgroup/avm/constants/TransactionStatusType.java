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

public enum TransactionStatusType {

	AVM_APPROVAL_IS_VALID(5),
	IN_PROGRESS(0), COMPLETED(1), REJECTED(-1), CANCELED(-2),
	APPROVER_HAS_ALREADY_TAKEN_ACTION(-4), INCOMPATIBLE_LEVEL_SEQUENCE(-5),
	TRANSACTION_IS_NOT_IN_PROGRESS(-6), TRANSACTION_IS_NOT_AVAILABLE(-7);
	
	private int code;
	
	private TransactionStatusType(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	//add yahya
	public static TransactionStatusType fromCode(int code) {
		for (TransactionStatusType transactionStatusType : TransactionStatusType.values()) {
			if (transactionStatusType.getCode() == code) {
				return transactionStatusType;
			}
		}
		return null;
	}
}
