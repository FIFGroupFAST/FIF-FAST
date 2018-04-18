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

package co.id.fifgroup.avm.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import co.id.fifgroup.avm.domain.AVMLevel;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMLevelMapper {

	//insert
	public int insertNewAVMLevel(AVMLevel avmLevel);
	
	//select
	public int getTheLastLevelSequence(Map<String, Object> parameterMap);
	public AVMLevel getAVMLevel(Map<String, Object> parameterMap);
	public List<AVMLevel> getAVMLevels(Map<String, Object> parameterMap);
	
	//delete add yahya
	public int deleteLevelList (Map<String, Object> parameterMap);
}
