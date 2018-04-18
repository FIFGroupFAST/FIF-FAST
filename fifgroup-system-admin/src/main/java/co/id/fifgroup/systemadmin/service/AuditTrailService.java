package co.id.fifgroup.systemadmin.service;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import co.id.fifgroup.systemadmin.dto.AuditTrailDTO;

public interface AuditTrailService {
	
	public int countAuditByExample(AuditTrailDTO auditTrailDTO, Date dateFrom, Date dateTo);
	
	public List<AuditTrailDTO> getAuditByExample(AuditTrailDTO auditTrailDTO, Date dateFrom, Date dateTo, int limit, int offset);

	public void generatePDF(ListModelList<AuditTrailDTO> listModelAudit);
	
}
