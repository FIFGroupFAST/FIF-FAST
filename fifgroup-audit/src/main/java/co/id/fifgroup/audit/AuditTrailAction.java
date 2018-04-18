/**
 * Copyright (c) 2007 Jatis Solutions. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of Firium
 * Solutions Pte Ltd. ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * Firium; other than in accordance with the written permission of Firium. <BR>
 * 
 * @version $Revision: 1.1 $
 * 
 * Author Date Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * Andeka Putra Oct 25, 2007 1.0 Created <BR>
 * Bob Nicolaus Nov 1, 2007 1.1 Merged with BPI Framework <BR>
 *  
 */
package co.id.fifgroup.audit;

public class AuditTrailAction {
	public static final AuditTrailAction EDIT_SYS_PARAM = new AuditTrailAction(
			0, "Edit data");

	public static final AuditTrailAction NEW_GROUP = new AuditTrailAction(1,
			"Insert new data");

	public static final AuditTrailAction EDIT_GROUP = new AuditTrailAction(2,
			"Edit data");

	public static final AuditTrailAction DELETE_GROUP = new AuditTrailAction(3,
			"Delete data");

	public static final AuditTrailAction NEW = new AuditTrailAction(0,
			"Insert new data");

	public static final AuditTrailAction EDIT = new AuditTrailAction(1,
			"Edit data");

	public static final AuditTrailAction DELETE = new AuditTrailAction(2,
			"Remove data");

	/* public static final AuditTrailAction ...(please add more as necessary) */

	private final int id;

	private final String description;

	protected AuditTrailAction(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "AuditTrailAction [description=" + description + ", id=" + id
				+ "]";
	}
}
