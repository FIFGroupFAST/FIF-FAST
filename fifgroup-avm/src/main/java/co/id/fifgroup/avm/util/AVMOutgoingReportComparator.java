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

package co.id.fifgroup.avm.util;

import co.id.fifgroup.avm.domain.AVMOutgoingReport;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMOutgoingReportComparator implements SerializableComparator<AVMOutgoingReport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3891654393717416776L;

	@Override
	public int compare(AVMOutgoingReport o1, AVMOutgoingReport o2) {
		boolean result = o1.getSubmittedTime().before(o2.getSubmittedTime());
		if (result == true) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
