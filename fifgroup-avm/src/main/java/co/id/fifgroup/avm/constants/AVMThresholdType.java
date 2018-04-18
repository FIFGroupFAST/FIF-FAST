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

public enum AVMThresholdType {
	DUEDATE_TYPE(1), DURATION_TYPE(2);
	
	int code;
	private AVMThresholdType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
