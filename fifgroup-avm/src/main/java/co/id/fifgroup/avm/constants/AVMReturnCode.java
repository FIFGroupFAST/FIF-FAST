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

public enum AVMReturnCode {

	DATA_SUCCESSFULLY_SENT_TO_APPROVER(1, "Data Successfully Sent To Approvers"),
	TRANSACTION_IS_SUCCESSFULLY_CREATED(2, "Transaction is successfully created"),
	DATA_IS_SUCCESSFULLY_UPDATED(3, "Data is successfully updated"),
	
	TRANSACTION_IS_IN_PROGRESS(0, "Transaction is in progress"),
	TRANSACTION_IS_COMPLETED(1, "Transaction is completed"),
	
	TRANSACTION_IS_NOT_AVAILABLE(-1, "Transaction is unavailable"),
	MISSING_APPROVERS(-2, "Approvers are missing"),
	NO_APPLICABLE_AVM_FOUND(-3, "No Applicable AVM is found"),
	INSUFFICIENT_APPROVERS(-4, "Insufficient Approvers"),
	ERROR_DATABASE_ACCESS(-5, "Error Database Access"),
	ERROR_MANAGER(-7, "Error Manager"),
	DATA_IS_FAILED_TO_UPDATE(-6, "Data is failed to update"),
	TRANSACTION_IS_CANCELLED(-2, "Transaction is cancelled"),
	TRANSACTION_IS_REJECTED(-1, "Transaction is rejected"),
	
	INCOMPATIBLE_LEVEL_SEQUENCE(-1, "Incompatible Level Sequence between approver " +
			"and current transaction"),
	NOT_READY_FOR_APPROVAL(-2, "Transaction is not ready for approval"),
	APPROVER_HAS_ALREADY_TAKEN_ACTION (-3, "Approver has already taken action"),
	
	READY_FOR_APPROVAL(1, "Ready for approval"),
	
	//add	
	NO_APPLICABLE_AVM_LEVEL_FOUND(-1, "No applicable AVM is found"),
	APPLICABLE_AVM_LEVEL_FOUND(-1, "Applicable AVM is found");
	
	
	private int code;
	private String message;
	
	private AVMReturnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getMessage(){
		return message;
	}
	
	public static AVMReturnCode fromCode(int code) {
		for (AVMReturnCode returnCode : AVMReturnCode.values()) {
			if (returnCode.getCode() == code) {
				return returnCode;
			}
		}
		return null;
	}
}
