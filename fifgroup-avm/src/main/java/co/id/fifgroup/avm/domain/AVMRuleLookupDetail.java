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

public class AVMRuleLookupDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3855578951981488579L;
	private int lookupDetailId;
	private String lookupHeaderId;
	private String lookupValueDomain;
	private String lookupDetailValue;
	private String description;
	private short activeStatus;
	private String lookupDetailCode;
	
	
	public int getLookupDetailId() {
		return lookupDetailId;
	}
	public void setLookupDetailId(int lookupDetailId) {
		this.lookupDetailId = lookupDetailId;
	}
	public String getLookupHeaderId() {
		return lookupHeaderId;
	}
	public void setLookupHeaderId(String lookupHeaderId) {
		this.lookupHeaderId = lookupHeaderId;
	}
	public String getLookupValueDomain() {
		return lookupValueDomain;
	}
	public void setLookupValueDomain(String lookupValueDomain) {
		this.lookupValueDomain = lookupValueDomain;
	}
	public String getLookupDetailValue() {
		return lookupDetailValue;
	}
	public void setLookupDetailValue(String lookupDetailValue) {
		this.lookupDetailValue = lookupDetailValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public short getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(short activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getLookupDetailCode() {
		return lookupDetailCode;
	}
	public void setLookupDetailCode(String lookupDetailCode) {
		this.lookupDetailCode = lookupDetailCode;
	}
	
	

}
