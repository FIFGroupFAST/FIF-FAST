package co.id.fifgroup.audit.impl;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.id.fifgroup.audit.ActionType;
import co.id.fifgroup.audit.AuditDAO;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.model.entity.AuditDetail;
import co.id.fifgroup.audit.model.entity.AuditMapping;
import co.id.fifgroup.audit.model.entity.AuditTrail;



/**
 * Default implementation of Audit DAO (Using MyBatis and connected to Oracle)
 * @author kiton
 *
 */
@Repository
public class DefaultAuditDAO implements AuditDAO {

	private static final Logger log = LoggerFactory.getLogger(DefaultAuditDAO.class);

	static final String MY_BATIS_ID = "audit";
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	public DefaultAuditDAO(){}
	
	public DefaultAuditDAO(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}

	
	/* (non-Javadoc)
	 * @see jatis.audit.AuditDAO#getSqlSessionFactory()
	 */
	@Override
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}


	/* (non-Javadoc)
	 * @see jatis.audit.AuditDAO#setSqlSessionFactory(org.apache.ibatis.session.SqlSessionFactory)
	 */
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}


	/* (non-Javadoc)
	 * @see jatis.audit.AuditDAO#insertUserActivity(jatis.audit.model.entity.AuditTrail)
	 */
	@Override
	public AuditTrail insertUserActivity(AuditTrail auditTrail) throws Exception {
		SqlSession session = getSqlSessionFactory().openSession();
		if (log.isDebugEnabled()){
			log.debug("Session is: "+session);
		}
		//long lastSequenceNumber = 0;
		Long refSequenceNumber = null;
		try {
			
			synchronized(this) {
				long seqNo = (Long) session.selectOne("Audit_AuditTrail.getAuditTrailSeqNo");
				auditTrail.setSequenceNumber(seqNo);
				auditTrail.setRefSequenceNumber(seqNo);
				session.insert("Audit_AuditTrail.insertNewAuditTrail", auditTrail);
				session.commit();
				
				AuditTrail attributeCode = null;
				if (auditTrail.getActionType() == ActionType.getActionValue(ActionType.Action.SUBMIT)
						|| auditTrail.getActionType() == ActionType
								.getActionValue(ActionType.Action.RESUBMIT) 
						|| auditTrail.getActionType() == Action.OTHERS.getCode()) {
					//lastSequenceNumber = seqNo;
				}
				else {
					refSequenceNumber = (Long)session.selectOne("Audit_AuditTrail.getRefSequenceNumber", auditTrail);
					if (refSequenceNumber != null) {
						attributeCode = (AuditTrail) session.selectOne("Audit_AuditTrail.getRefAttributeCode", refSequenceNumber);
						if (attributeCode != null) {
							auditTrail.setAttributeCodeLabel(attributeCode.getAttributeCodeLabel());
							auditTrail.setAttributeCodeValue(attributeCode.getAttributeCodeValue());
						}
						auditTrail.setRefSequenceNumber(refSequenceNumber);
						session.update("Audit_AuditTrail.updateRefSequenceNumber", auditTrail);
					}
					
				}
				session.commit();
				session.close();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			if (session != null)
				session.close();
			
		}
		return auditTrail;
	}

	/* (non-Javadoc)
	 * @see jatis.audit.AuditDAO#insertAttributesValueChange(long, java.util.List)
	 */
	@Override
	public void insertAttributesValueChange(long sequenceNumberFromAuditTrail, List<AuditDetail> auditDetailList)
			throws Exception {
		SqlSession session = getSqlSessionFactory().openSession();
		if (log.isDebugEnabled()){
			log.debug("Session = "+session);
		}
		try {
			for (int i = 0; i < auditDetailList.size(); i++) {
				AuditDetail auditDetail = auditDetailList.get(i);
				auditDetail.setSequenceNumber(sequenceNumberFromAuditTrail);
				session.insert("Audit_AuditDetail.insertNewAuditDetail",
						auditDetail);
			}
			session.commit();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			if (session != null)
				session.close();
		}

	}
	
	/* (non-Javadoc)
	 * @see jatis.audit.AuditDAO#getAttributeCode(java.lang.String, int, int)
	 */
	@Override
	public AuditMapping getAttributeCode(String className, int moduleId, int controlType) throws Exception {
		SqlSession session = null;
		AuditMapping auditMapping = new AuditMapping();
		auditMapping.setClassName(ClassNameCleaner.cleanupClassName(className));
		auditMapping.setModuleId(moduleId);
		auditMapping.setControlType(controlType);
		AuditMapping attributeCode = null;
		try {
			session = getSqlSessionFactory().openSession();
			attributeCode = (AuditMapping) 
				session.selectOne("Audit_AuditMapping.getAttributeCode", auditMapping);
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			if (session != null)
				session.close();
		}
		return attributeCode; 
	}
	
}
