package co.id.fifgroup.core.audit;

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
