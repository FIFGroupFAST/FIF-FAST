package co.id.fifgroup.audit;


import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSessionFactory;

import co.id.fifgroup.audit.model.entity.AuditDetail;

/**
 * Attribute comparator
 * @author kiton
 *
 */
public interface AttributeComparator {
	/**
	 * Set ToTraversableConverter
	 * @param traversableConverter
	 */
	public abstract void setToTraversableConverter(ToTraversableConverter traversableConverter);
	
	/**
	 * Set SqlSessionFactory
	 * @param sqlSessionFactory
	 */
	public abstract void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);
	
	/**
	 * Set TraversableMap
	 * @param traversableMap
	 */
	public abstract void setTraversableMap(TraversableMap traversableMap);
	
	/**
	 * Get comparisons list
	 * @return comparisons list
	 */
	public abstract List<AttributesValuesComparison> getComparisonList();

	/**
	 * Get audit details list
	 * @return audit details list
	 */
	public abstract List<AuditDetail> getAuditDetailList();

	/**
	 * Do attribute comparison
	 * @param oldObject old object
	 * @param newObject new object
	 * @param controlType control type
	 * @param auditId audit ID
	 * @param moduleId module ID
	 */
	public abstract void doAttributeComparison(Object oldObject,
			Object newObject, int controlType, UUID auditId, int moduleId);

}