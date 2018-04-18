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

package co.id.fifgroup.avm.audit;

/**
 * This is usually used by objects passed on to jatis audit engine
 * as a marker interface.
 * This interface is placed in jatis.configuration to prevent 
 * forced dependencies to jatis.audit in modules using jatis.core 
 * jatis.audit audit engine
 * @author kiton
 *
 */
public interface Traversable {
	/**
	 * Get primary key ID of the Traversable object
	 * @return primary key ID
	 */
	public Object getId();
}
