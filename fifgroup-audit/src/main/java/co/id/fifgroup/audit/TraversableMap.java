package co.id.fifgroup.audit;

import java.util.List;
import java.util.Map;
/**
 * Map of traversables
 * @author kiton
 */
public interface TraversableMap {
	
	/**
	 * Set list of auditable classes (equivalent to addList)
	 * @param traversableClasses traversable class names
	 */
	public void setTraversables(List<String> traversableClasses);
	
	/**
	 * Set traversable class (equivalent to add)
	 * @param traversable traversable class name
	 */
	public void setTraversable(String traversable);
	
	/**
	 * Add list
	 * @param traversableClasses
	 */
	public TraversableMap addList(List<Class<?>> traversableClasses);
	
	/**
	 * Add traversable class name
	 * @param traversableClass
	 */
	public TraversableMap add(Class<?> traversableClass);
	
	/**
	 * Is object traversable
	 * @param object  
	 * @return true if traversable
	 */
	public boolean isAuditable(Object object);
	
	/**
	 * Add all list of traversable maps
	 * @param listOfTraversableMaps
	 */
	public TraversableMap addAll(List<TraversableMap> listOfTraversableMaps);
	
	/**
	 * Add all list of traversable maps (equivalent to addAll)
	 * @param listOfTraversableMaps
	 */
	public void setTraversableMaps(List<TraversableMap> listOfTraversableMaps);
	
	/**
	 * Get backing map
	 * @return backing map of this map
	 */
	public Map<String, Boolean> getBackingMap();
}
