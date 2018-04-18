package co.id.fifgroup.audit;


import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import co.id.fifgroup.audit.model.entity.AuditDetail;
import co.id.fifgroup.audit.model.entity.AuditMapping;
import co.id.fifgroup.audit.model.entity.AuditTrail;

public interface AuditDAO {

	public abstract SqlSessionFactory getSqlSessionFactory();

	public abstract void setSqlSessionFactory(
			SqlSessionFactory sqlSessionFactory);

	public abstract AuditTrail insertUserActivity(AuditTrail auditTrail)
			throws Exception;

	public abstract void insertAttributesValueChange(
			long sequenceNumberFromAuditTrail, List<AuditDetail> auditDetailList)
			throws Exception;

	public abstract AuditMapping getAttributeCode(String className,
			int moduleId, int controlType) throws Exception;

}