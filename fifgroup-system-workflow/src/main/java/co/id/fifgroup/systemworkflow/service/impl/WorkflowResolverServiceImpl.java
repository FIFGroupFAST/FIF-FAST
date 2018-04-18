package co.id.fifgroup.systemworkflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.systemworkflow.constants.BasedOn;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingHeader;
import co.id.fifgroup.systemworkflow.service.ApprovalModelMappingService;
import co.id.fifgroup.systemworkflow.service.WorkflowResolverService;
import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.util.Serialization;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.core.service.WorkflowService;
import co.id.fifgroup.systemworkflow.adapter.ResolverDepartmentHead;
import co.id.fifgroup.systemworkflow.adapter.ResolverDepartmentOwner;
import co.id.fifgroup.systemworkflow.adapter.ResolverEmployee;
import co.id.fifgroup.systemworkflow.adapter.ResolverJob;
import co.id.fifgroup.systemworkflow.adapter.ResolverJobGroup;
import co.id.fifgroup.systemworkflow.adapter.ResolverJobOwner;
import co.id.fifgroup.systemworkflow.adapter.ResolverJobRole;
import co.id.fifgroup.systemworkflow.adapter.ResolverManager;
import co.id.fifgroup.systemworkflow.adapter.ResolverRole;
import co.id.fifgroup.systemworkflow.adapter.ResolverSupervisor;
import co.id.fifgroup.systemworkflow.adapter.WorkflowServiceFactory;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@Transactional(readOnly=true)
@Service
public class WorkflowResolverServiceImpl implements WorkflowResolverService {

	private static final Logger logger = LoggerFactory.getLogger(WorkflowResolverServiceImpl.class);
	
	@Autowired
	private ApprovalModelMappingService approvalModelMappingServiceImpl;
	@Autowired
	private OrganizationHierarchySetupService organizationHierarchySetupServiceImpl;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;

	@Override
	public List<AVMApprovalHistory> getResolvingApproverList(
			List<AVMApprover> rawApproverList, AVMTransaction avmTransaction,
			UUID submitterId) throws FifException {
		
		Object serializedData = Serialization.deserializedObject(avmTransaction.getSerializedData());
		CommonTrx trx = (CommonTrx) serializedData;
		Long orgHierId = null;
		Long destHierId = null;
		
		PersonDTO personBasedLine = null;
		if (trx.isBasedLineRequestor()) { 
			personBasedLine = personService.getPersonByPersonUUID(submitterId, DateUtil.truncate(avmTransaction.getAvmTimeStamp()), null); 
			if (personBasedLine == null) { 
				personBasedLine = personService.getPersonByPersonUUIDForInternship(submitterId, DateUtil.truncate(avmTransaction.getAvmTimeStamp()), null); 
			} 
		} else {

			// Bug 9470 
			if (trx.getTransactionType()==null || !isCwkTransaction(trx.getTransactionType())){ 
				personBasedLine = personService.getPersonByPersonUUID((UUID) trx.getObjectEmployeeUUID(), DateUtil.truncate(new Date()), avmTransaction.getCompanyId()); 
			} 
			// end 9470 
			 
			//Add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review 
			if (personBasedLine == null && avmTransaction.getTrxType() == 18) 
				personBasedLine = personService.getPersonByPersonUUIDInactive((UUID) trx.getObjectEmployeeUUID(), DateUtil.truncate(new Date()), avmTransaction.getCompanyId());  
			//End add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review 
			 
			// handling for cwk 
			if (personBasedLine == null) { 
				personBasedLine = personService.getCwkPersonByPersonUUID((UUID) trx.getObjectEmployeeUUID(), DateUtil.truncate(new Date()), avmTransaction.getCompanyId()); 
			} 


			//personBasedLine = personService.getPersonByPersonUUID((UUID) trx.getObjectEmployeeUUID(), DateUtil.truncate(new Date()), avmTransaction.getCompanyId()); 
			//change by HBP 15031814272238 - Tidak Bisa Definitive Status Atas Organisasi NonAktif 
			//personBasedLine = personService.getPersonByPersonUUIDInactive((UUID) trx.getObjectEmployeeUUID(), DateUtil.truncate(new Date()), avmTransaction.getCompanyId());  
 
		}
		List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
		if (personBasedLine != null) {
//			try {
				// TODO get org hierarchy id from approval model mapping header
				ApprovalModelMappingHeader modelMappingHeader = approvalModelMappingServiceImpl.getApprovalModelMappingActiveByScopeAndTransactionId(avmTransaction.getCompanyId(), (long) avmTransaction.getTrxType(), DateUtil.truncate(avmTransaction.getAvmTimeStamp()));
				if (modelMappingHeader == null) {
					modelMappingHeader = approvalModelMappingServiceImpl.getApprovalModelMappingActiveByScopeAndTransactionId(ScopeType.COMMON.getValue(), (long) avmTransaction.getTrxType(), DateUtil.truncate(avmTransaction.getAvmTimeStamp()));
				}
				
				if (modelMappingHeader.getOrgHierId() == 0) {
					OrganizationHierarchyExample example = new OrganizationHierarchyExample();
					example.createCriteria().andCompanyIdEqualTo(personBasedLine.getCompanyId()).andOrgHierTypeEqualTo(HierarchyType.STRUCTURAL.getDesc());
					orgHierId = organizationHierarchySetupServiceImpl.findByExample(example).get(0).getId();
				} else {
					orgHierId = modelMappingHeader.getOrgHierId();
				}
				
				// Bug - 9470
				if (trx.getOrganizationDestinationId() != null){
					OrganizationDTO destOrg = organizationSetupServiceImpl.findById(trx.getOrganizationDestinationId());
					if (destOrg != null){
						OrganizationHierarchyExample example = new OrganizationHierarchyExample();
						example.createCriteria().andCompanyIdEqualTo(destOrg.getCompanyId()).andOrgHierTypeEqualTo(HierarchyType.STRUCTURAL.getDesc());
						destHierId = organizationHierarchySetupServiceImpl.findByExample(example).get(0).getId();
					}
				}
				// end 9470
				
				for (AVMApprover approver : rawApproverList) {
					if (approver.getApproverType() == 
							ApproverType.Supervisor.getCode()) {
						historyList.addAll(ResolverSupervisor.getApprovers(personBasedLine, approver, trx, orgHierId, avmTransaction.getAvmTrxId()));
					} else if (approver.getApproverType() ==
							ApproverType.Role.getCode()) {
						historyList.addAll(ResolverRole.getApprovers(personBasedLine, approver));
					} else if (approver.getApproverType() == 
							ApproverType.DepartmentOwner.getCode()) {
						historyList.addAll(ResolverDepartmentOwner.getApprovers(personBasedLine, approver, trx));
					} else if (approver.getApproverType() ==
							ApproverType.Job.getCode()) {
						historyList.addAll(ResolverJob.getApprovers(personBasedLine, approver, trx, orgHierId));
					} else if (approver.getApproverType() ==
							ApproverType.Employee.getCode()) {
						historyList.addAll(ResolverEmployee.getApprovers(approver));
					} else if (approver.getApproverType() ==
							ApproverType.JobRole.getCode()) {
						historyList.addAll(ResolverJobRole.getApprovers(personBasedLine, approver, trx, orgHierId));
					} else if (approver.getApproverType() ==
							ApproverType.Manager.getCode()) {
						// Bug - 9470
						if (approver.getBasedOn() == BasedOn.NewAssignment.getCode() && destHierId != null) {
							historyList.addAll(ResolverManager.getApprovers(personBasedLine, approver, trx, destHierId, avmTransaction.getAvmTrxId()));
						}
						else {
							historyList.addAll(ResolverManager.getApprovers(personBasedLine, approver, trx, orgHierId, avmTransaction.getAvmTrxId()));
						}
						// end 9470
					} else if (approver.getApproverType() ==
							ApproverType.JobGroup.getCode()) {
						ResolverJobGroup resolverGroupJob = new ResolverJobGroup();
						historyList.addAll(resolverGroupJob.getApprovers(personBasedLine, approver, trx, orgHierId));
					} else if (approver.getApproverType() ==
							ApproverType.DepartmentHead.getCode()) {
						historyList.addAll(ResolverDepartmentHead.getApprovers(personBasedLine, approver, trx, orgHierId));
					} else if (approver.getApproverType() ==
							ApproverType.JobOwner.getCode()) {
						historyList.addAll(ResolverJobOwner.getApprovers(personBasedLine, approver, trx));
					}
				}
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//				throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), AVMReturnCode.ERROR_MANAGER.getMessage());
//			}
		}
		return historyList;
	}

	@Override
	@Transactional(readOnly=false)
	public void doAfterApprovalProcess(AVMReturnCode returnCode,
		Long transactionId, AVMTransaction avmTransaction, Object application) throws FifException, Exception {
		
		Object serializedData;
		
		if (avmTransaction != null) {
			serializedData = Serialization.deserializedObject(avmTransaction
					.getSerializedData());			
		} else {
			serializedData = application;
		}
		
		WorkflowService workflowService = WorkflowServiceFactory.getInstances(transactionId);
		
		if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_COMPLETED)) {
			workflowService.doAfterApproved(serializedData);
//			avmAdapterServiceImpl.pullApplicationData(avmTransaction.getAvmTrxId());
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_REJECTED)) {
			workflowService.doAfterRejected(serializedData);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_CANCELLED)) {
			workflowService.doAfterCanceled(serializedData);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void doApproveTransaction(Long transactionId, Object application, UUID avmTrxId, UUID approverId) throws Exception {
		WorkflowService workflowService = WorkflowServiceFactory.getInstances(transactionId);
		workflowService.doApprove(application, avmTrxId, approverId);			
	}

	@Override
	@Transactional(readOnly=true)
	public void doValidateBeforeApprove(Long transactionId, byte[] serializedData) throws ValidationException {
		WorkflowService workflowService = WorkflowServiceFactory.getInstances(transactionId);
		Object application = Serialization.deserializedObject(serializedData);
		workflowService.doValidateBeforeApprove(application);			
	}

	@Override
	@Transactional(readOnly=true)
	public void doValidateBeforeReject(Long transactionId, byte[] serializedData) throws ValidationException {
		WorkflowService workflowService = WorkflowServiceFactory.getInstances(transactionId);
		Object application = Serialization.deserializedObject(serializedData);
		workflowService.doValidateBeforeReject(application);				
	}
	
	public boolean isCwkTransaction(Long transactionType){
		boolean flag = false;
		if(transactionType != null && (transactionType.equals(TrxType.CWK_TRANSFER_WITHIN_GROUP.getCode()) 
		||transactionType.equals(TrxType.CWK_TERMINATION_REQUEST.getCode())
		||transactionType.equals(TrxType.CWK_TERMINATION_REVERSE.getCode())
		||transactionType.equals(TrxType.CWK_PKWT_EXTENTIONS.getCode())
		||transactionType.equals(TrxType.CWK_TRANSFER_REQUEST.getCode()))
		){
			flag = true;
		}
		return flag;
	}
}
