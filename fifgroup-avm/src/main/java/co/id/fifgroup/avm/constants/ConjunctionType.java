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

public enum ConjunctionType {
	AND(1, "&"), OR(2, "|");
	
	private int code;
	private String conjunction;
	private ConjunctionType(int code, String conjunction) {
		this.code = code;
		this.conjunction = conjunction;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getConjunction() {
		return conjunction;
	}
	
	public static ConjunctionType getConjunctionTypeFromString(String conjunction) {
		ConjunctionType selectedConjunctionType = null;
		for (ConjunctionType conjunctionType : ConjunctionType.values()) {
			if (conjunctionType.getConjunction().equals(conjunction)) {
				selectedConjunctionType = conjunctionType;
				break;
			}
			
		}
		return selectedConjunctionType;
	}
}
