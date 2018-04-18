package co.id.fifgroup.audit.impl;


import java.util.LinkedList;

import co.id.fifgroup.audit.TempQueue;
import co.id.fifgroup.audit.model.entity.AuditDetail;



/**
 * Default Temporary Queue implementation
 * @author kiton
 *
 */
public class DefaultTempQueue extends LinkedList<AuditDetail> implements TempQueue {

	private static final long serialVersionUID = 3199121546021863254L;
	
	/* (non-Javadoc)
	 * @see jatis.audit.TempQueue#add(jatis.audit.model.entity.AuditDetail)
	 */
	@Override
	public boolean add(AuditDetail auditDetail) {
		return super.add(auditDetail);
	}
	
	/* (non-Javadoc)
	 * @see jatis.audit.TempQueue#pop()
	 */
	@Override
	public AuditDetail pop() {
		if (size() == 0) {
			return null;
		}
		return super.removeLast();
	}
}
