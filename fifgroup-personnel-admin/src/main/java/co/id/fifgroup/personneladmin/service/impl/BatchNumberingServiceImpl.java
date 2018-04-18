package co.id.fifgroup.personneladmin.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.service.SequenceGeneratorUtilService;
import co.id.fifgroup.core.service.BatchNumberServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;

@Service
public class BatchNumberingServiceImpl implements BatchNumberServiceAdapter {

	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private SequenceGeneratorUtilService sequenceGeneratorUtilServiceImpl;
	
	@Override
	@Transactional(readOnly=true)
	public String getBatchNumber(String trxName) {
		DateFormat sdf = new SimpleDateFormat("yy");
		return sdf.format(new Date()) + sequenceGeneratorUtilServiceImpl.getSequenceGeneratorFormat(
				securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(),
				securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), trxName);
	}
}
