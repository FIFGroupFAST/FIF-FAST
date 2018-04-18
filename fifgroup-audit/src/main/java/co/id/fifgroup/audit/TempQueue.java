package co.id.fifgroup.audit;

import java.util.List;

import co.id.fifgroup.audit.model.entity.AuditDetail;


/**
 * Temporary queue utilized by AttributeComparator
 * @author Papah
 *
 */
public interface TempQueue extends List<AuditDetail>{

	/**
	 * Add audit detail at the end of the queue
	 */
	public abstract boolean add(AuditDetail auditDetail);

	/**
	 * Pop audit detail
	 * @return AuditDetail
	 */
	public abstract AuditDetail pop();

	/**
	 * Get last audit detail
	 * @return last AuditDetail object
	 */
	public AuditDetail getLast();
}