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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.avm.constants.ConjunctionType;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMRuleExpression implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059845365904499314L;
	private UUID avmRuleStatementId;
	private AVMRuleMapping avmRuleMapping;
	private AVMRuleLookupDetail	avmRuleLookupDetail;
	private AVMRuleMapping avmRuleMappingSecond;
	private AVMRuleLookupDetail avmRuleLookupDetailSecond;
	private ConjunctionType conjunctionType;
	private Object value;
	
	private List<AVMRuleMapping> avmRuleMappingList;
	private List<AVMRuleLookupDetail> avmRuleLookupDetailList;
	private List<Object> valueList;
	private List<ConjunctionType> conjunctionTypeList;
	private List<AVMRuleStatement> avmRuleStatementList;
	
	public AVMRuleExpression() {
		avmRuleMappingList = new ArrayList<AVMRuleMapping>();
		avmRuleLookupDetailList = new ArrayList<AVMRuleLookupDetail>();
		valueList = new ArrayList<Object>();
		conjunctionTypeList = new ArrayList<ConjunctionType>();
		avmRuleStatementList = new ArrayList<AVMRuleStatement>();
	}
	
	public List<AVMRuleMapping> getAvmRuleMappingList() {
		return avmRuleMappingList;
	}

	public void setAvmRuleMappingList(List<AVMRuleMapping> avmRuleMappingList) {
		this.avmRuleMappingList = avmRuleMappingList;
	}

	public List<AVMRuleLookupDetail> getAvmRuleLookupDetailList() {
		return avmRuleLookupDetailList;
	}

	public void setAvmRuleLookupDetailList(
			List<AVMRuleLookupDetail> avmRuleLookupDetailList) {
		this.avmRuleLookupDetailList = avmRuleLookupDetailList;
	}

	public List<Object> getValueList() {
		return valueList;
	}

	public void setValueList(List<Object> valueList) {
		this.valueList = valueList;
	}

	public List<ConjunctionType> getConjunctionTypeList() {
		return conjunctionTypeList;
	}

	public void setConjunctionTypeList(List<ConjunctionType> conjunctionTypeList) {
		this.conjunctionTypeList = conjunctionTypeList;
	}
	
	public void setAvmRuleStatementList(List<AVMRuleStatement> avmRuleStatementList) {
		this.avmRuleStatementList = avmRuleStatementList;
	}

	public List<AVMRuleStatement> getAvmRuleStatementList() {
		return avmRuleStatementList;
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
	public void setConjunctionType(ConjunctionType conjunctionType) {
		this.conjunctionType = conjunctionType;
	}
	public ConjunctionType getConjunctionType() {
		return conjunctionType;
	}
	public void setAvmRuleMappingSecond(AVMRuleMapping avmRuleMappingSecond) {
		this.avmRuleMappingSecond = avmRuleMappingSecond;
	}
	public AVMRuleMapping getAvmRuleMappingSecond() {
		return avmRuleMappingSecond;
	}
	public void setAvmRuleLookupDetailSecond(AVMRuleLookupDetail avmRuleLookupDetailSecond) {
		this.avmRuleLookupDetailSecond = avmRuleLookupDetailSecond;
	}
	public AVMRuleLookupDetail getAvmRuleLookupDetailSecond() {
		return avmRuleLookupDetailSecond;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public void setAvmRuleStatementId(UUID avmRuleStatementId) {
		this.avmRuleStatementId = avmRuleStatementId;
	}

	public UUID getAvmRuleStatementId() {
		return avmRuleStatementId;
	}
}
