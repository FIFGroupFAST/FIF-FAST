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

import co.id.fifgroup.avm.constants.ConjunctionType;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMRuleStatement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1944711747578103121L;
	private UUID avmRuleStatementId;
	private AVMRuleMapping avmRuleMapping;
	private AVMRuleLookupDetail	avmRuleLookupDetail;
	private ConjunctionType conjunctionType;
	private Object value;
	
	public UUID getAvmRuleStatementId() {
		return avmRuleStatementId;
	}
	public void setAvmRuleStatementId(UUID avmRuleStatementId) {
		this.avmRuleStatementId = avmRuleStatementId;
	}
	public AVMRuleMapping getAvmRuleMapping() {
		return avmRuleMapping;
	}
	public void setAvmRuleMapping(AVMRuleMapping avmRuleMapping) {
		this.avmRuleMapping = avmRuleMapping;
	}
	public AVMRuleLookupDetail getAvmRuleLookupDetail() {
		return avmRuleLookupDetail;
	}
	public void setAvmRuleLookupDetail(AVMRuleLookupDetail avmRuleLookupDetail) {
		this.avmRuleLookupDetail = avmRuleLookupDetail;
	}
	public ConjunctionType getConjunctionType() {
		return conjunctionType;
	}
	public void setConjunctionType(ConjunctionType conjunctionType) {
		this.conjunctionType = conjunctionType;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
