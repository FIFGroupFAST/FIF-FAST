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

public class AVMRuleMapping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5327358747301567012L;
	private int mappingId;
	private String className;
	private String methodName;
	private String attributeLabel;
	private String lookupHeaderId;
	private short withValue;
	
	public int getMappingId() {
		return mappingId;
	}
	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getAttributeLabel() {
		return attributeLabel;
	}
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}
	public void setLookupHeaderId(String lookupHeaderId) {
		this.lookupHeaderId = lookupHeaderId;
	}
	public String getLookupHeaderId() {
		return lookupHeaderId;
	}
	public void setWithValue(short withValue) {
		this.withValue = withValue;
	}
	public short getWithValue() {
		return withValue;
	}
}
