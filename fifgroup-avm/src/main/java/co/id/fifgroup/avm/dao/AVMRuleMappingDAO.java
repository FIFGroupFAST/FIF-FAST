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

package co.id.fifgroup.avm.dao;

import java.util.List;

import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMRuleMappingDAO{

	public AVMRuleMapping getAVMRuleMapping(String attributeLabel) throws FifException;
	
	public List<AVMRuleLookupDetail> getRuleLookupDetails(String lookupHeaderId) throws FifException;
	
	public AVMRuleLookupDetail getRuleLookupDetail(String lookupHeaderId, String lookupValue) throws FifException;
}
