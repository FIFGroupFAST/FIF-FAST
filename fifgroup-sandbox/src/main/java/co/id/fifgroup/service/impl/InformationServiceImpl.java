package co.id.fifgroup.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.dao.InformationMapper;
import co.id.fifgroup.domain.Information;
import co.id.fifgroup.service.InformationService;
import co.id.fifgroup.systemworkflow.constants.TrxStatus;

@Transactional
@Service
public class InformationServiceImpl implements InformationService {

	@Autowired
	private InformationMapper informationMapper;
	@Autowired
	private SecurityService securityServiceImpl;

	@Override
	public void insertInformation(Information information) {
		informationMapper.insertInformation(information);
	}

	@Override
	public void updateInformation(Information information) {
		informationMapper.updateInformation(information);
	}

	@Override
	public void deleteInformation(int informationId) {
		informationMapper.deleteInformation(informationId);
	}

	@Override
	public List<Information> getAllInformation() {
		return informationMapper.getAllInformation();
	}

	@Override
	public List<Information> getInformationByFilter(Information information) {
		return informationMapper.getInformationByFilter(information);
	}

	@Override
	public String getSupervisor(String employeeNumber) {
		return informationMapper.getSupervisor(employeeNumber);
	}

	@Override
	public void doValidateBeforeApprove(Object transaction) 
			throws ValidationException {
	}

	@Override
	public void doValidateBeforeReject(Object transaction) 
			throws ValidationException {
	}

	@Override
	public void doApprove(Object transaction, UUID avmTrxId, UUID approverId) throws Exception {
		Information information = (Information) transaction;
		information.setAvmTrxId(avmTrxId);
		information.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getEmployeeNumber());
		information.setLastUpdatedDate(new Date());
		informationMapper.updateInformation(information);
	}

	@Override
	public void doAfterApproved(Object transaction) throws Exception {
		Information information = (Information) transaction;
		information.setApprovalStatus(TrxStatus.APPROVED.toString());
		information.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getEmployeeNumber());
		information.setLastUpdatedDate(new Date());
		informationMapper.updateInformation(information);		
	}

	@Override
	public void doAfterRejected(Object transaction) throws Exception {
		Information information = (Information) transaction;
		information.setApprovalStatus(TrxStatus.REJECTED.toString());
		information.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getEmployeeNumber());
		information.setLastUpdatedDate(new Date());
		informationMapper.updateInformation(information);		
	}

	@Override
	public void doAfterCanceled(Object transaction) throws Exception {
		Information information = (Information) transaction;
		information.setApprovalStatus(TrxStatus.CANCELED.toString());
		information.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getEmployeeNumber());
		information.setLastUpdatedDate(new Date());
		informationMapper.updateInformation(information);		
	}


}