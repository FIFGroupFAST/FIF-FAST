package co.id.fifgroup.audit;

import co.id.fifgroup.core.audit.Traversable;

/**
 * Converter to traversable object
 * @author kiton
 *
 */
public interface ToTraversableConverter {
	/**
	 * Clear Traversable cache (if implemented)
	 * Travesable cache is recommended to be implemented as a ThreadLocal
	 */
	public void clearCache();
	/**
	 * Convert object o to traversable
	 * @param o Object
	 * @return Traversable object (must also be an instance of original object's class)
	 */
	public Traversable toTraversable(Object o);
}
