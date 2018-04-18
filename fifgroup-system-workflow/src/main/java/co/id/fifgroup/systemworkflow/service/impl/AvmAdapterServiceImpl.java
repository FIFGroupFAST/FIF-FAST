package co.id.fifgroup.systemworkflow.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.constants.WorkflowReference;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingHeader;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.Employee;
import co.id.fifgroup.systemworkflow.domain.ParameterMessage;
import co.id.fifgroup.systemworkflow.domain.TemplateMessageMapping;
import co.id.fifgroup.systemworkflow.dto.AVMApplicationDataDTO;
import co.id.fifgroup.systemworkflow.dto.AVMOutgoingReportDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelDTO;
import co.id.fifgroup.systemworkflow.service.ApprovalModelMappingService;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;
import co.id.fifgroup.systemworkflow.service.WorkflowResolverService;
import co.id.fifgroup.systemworkflow.validation.ApprovalModelValidator;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.avm.constants.AVMActionType;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.constants.TransactionStatusType;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.domain.AVMRejectionDoer;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.avm.manager.AVMRuleEvaluationManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.avm.manager.AVMTransactionManager;
import co.id.fifgroup.avm.util.Serialization;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.security.SecurityProfile;
/*import co.id.fifgroup.core.service.CwkServiceAdapter;*/
import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.systemworkflow.controller.WorkflowApprovalModelMappingAddComposer;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@Service
@Transactional(readOnly=true)
public class AvmAdapterServiceImpl implements AvmAdapterService {

	private static final Logger logger = LoggerFactory.getLogger(AvmAdapterServiceImpl.class);
	
	@Autowired
	private AVMEngine avmEngineHcms;
	@Autowired
	private AVMSetupManager avmSetupManager;
	@Autowired
	private ApprovalModelValidator approvalModelValidator;
	@Autowired
	private ApprovalModelMappingService approvalModelMappingServiceImpl;
	@Autowired
	private AVMRuleEvaluationManager avmRuleEvaluationManager;
	@Autowired
	private WorkflowResolverService workflowResolverServiceImpl;
	@Autowired
	private AVMTransactionManager avmTransactionManager;
	@Autowired
	private AVMApprovalManager avmApprovalManagerHcms;
	@Autowired
	private TemplateMessageService templateMessageServiceImpl;
	@Autowired
	private ApproverMappingService approverMappingServiceImpl;
	@Autowired
	private PersonService personService;
	@Autowired
	private JobSetupService jobSetupServiceImpl;
	@Autowired
	private LookupService lookupServiceImpl;
	@Autowired
	private CompanyService companyServiceImpl;
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired
	private WorkflowLookupServiceAdapter workflowLookupServiceAdapterImpl;
	/*@Autowired
	private CwkServiceAdapter CwkPersonDTOServiceImpl;*/
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<AVM> getAVMByName(String avmName) throws FifException {
		return avmSetupManager.findAVM(avmName);
	}

	@Override
	public List<AVMVersions> getAllAVMVersion(AVM avm) throws FifException {
		return avmSetupManager.getAllAVMVersion(avm);
	}

	@Override
	public int getLastVersionNumber(UUID avmId) throws FifException {
		return avmSetupManager.getLastVersionNumber(avmId);
	}

	@Override
	public List<AVMLevel> getAVMLevels(AVM avm, AVMVersions avmVersions) throws FifException {
		return avmSetupManager.getAVMLevelList(avm.getAvmId(), avmVersions.getAvmVersionId());
	}

	@Override
	public List<AVMApprover> getAVMApproversByLevel(AVMLevel avmLevel, AVMVersions avmVersions) throws FifException {
		return avmSetupManager.getAVMApproversByLevel(avmLevel.getAvmId(), avmLevel.getLevelSequence(), avmVersions.getAvmVersionId());
	}
	
	@Override
	@Transactional(readOnly=false)
	public void saveAVMTemplate(ApprovalModelDTO approvalModelDto,
			Calendar dateFromBefore) throws FifException, ValidationException {
//		approvalModelValidator.setDateFromBefore(dateFromBefore);
		approvalModelValidator.validate(approvalModelDto);
		if (approvalModelDto.getAvm().getAvmId() != null) {
			updateAVMTemplate(approvalModelDto, dateFromBefore);
		} else {
			createAVMTemplate(approvalModelDto, dateFromBefore);
		}
	}	

	@Override
	@Transactional(readOnly=false)
	public void createAVMTemplate(ApprovalModelDTO approvalModelDto, Calendar dateFromBefore)
			throws FifException {
		approvalModelDto.getAvm().setAvmId(UUID.randomUUID());
		approvalModelDto.getAvmVersions().setAvmId(approvalModelDto.getAvm().getAvmId());
		avmSetupManager.createAVMTemplate(approvalModelDto.getAvm(), approvalModelDto.getAvmVersions(), approvalModelDto.getListLevel(), approvalModelDto.getListApprovers());
	}
	
	@Override
	public AVMVersions getAVMVersionsByNumberVersion(UUID avmId, int numberVersion)
			throws FifException {
		return avmSetupManager.getAVMVersionByNumberVersion(avmId, numberVersion);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateAVMTemplate(ApprovalModelDTO approvalModelDto, Calendar dateFromBefore)
			throws FifException {
		Calendar today = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE); 
		Calendar startDateFuture = Calendar.getInstance();
		startDateFuture.setTime(approvalModelDto.getAvmVersions().getEffectiveStartDate());
		startDateFuture.add(Calendar.DATE, -1);
		
		// new version
		if (approvalModelDto.getAvmVersions().getAvmVersionId() == 0) {
			avmSetupManager.createNewVersionAVM(approvalModelDto.getAvmVersions(), approvalModelDto.getListLevel(), approvalModelDto.getListApprovers());
			AVMVersions previousVersion = avmSetupManager.getPreviousVersion(approvalModelDto.getAvmVersions());
			if (previousVersion.getEffectiveEndDate().after(today.getTime())) {
				previousVersion.setEffectiveEndDate(startDateFuture.getTime());
				avmSetupManager.updateAVMVersions(previousVersion);
			}
			return;
		}
		
		// update version
		if (isCurrent(approvalModelDto.getAvmVersions())) {
			avmSetupManager.updateAVMVersions(approvalModelDto.getAvmVersions());
		} else {
			avmSetupManager.updateAVMTemplate(approvalModelDto.getAvmVersions(), approvalModelDto.getListLevel(), approvalModelDto.getListApprovers());
			AVMVersions previousVersion = avmSetupManager.getPreviousVersion(approvalModelDto.getAvmVersions());
			if (previousVersion != null) {
				if (previousVersion.getEffectiveEndDate().after(today.getTime())) {
					previousVersion.setEffectiveEndDate(startDateFuture.getTime());
					avmSetupManager.updateAVMVersions(previousVersion);
				}
			} 
		}
	}
	
	@Override
	@Transactional(readOnly=false)
	public void deleteAVMTemplate(AVMVersions avmVersions) throws FifException {
		if(isFuture(avmVersions)) {
			AVMVersions previousVersion = avmSetupManager.getPreviousVersion(avmVersions);
			if(previousVersion != null) {
				avmSetupManager.deleteAVMVersions(avmVersions);
				if(isCurrent(previousVersion)) {
					previousVersion.setEffectiveEndDate(DateUtil.MAX_DATE);
					avmSetupManager.updateAVMVersions(previousVersion);
				}
			} else {
				avmSetupManager.deleteAVMTemplate(avmVersions);
			}
		}
	}
	
	public boolean isCurrent(AVMVersions avmVersions) {
		Calendar today = Calendar.getInstance();
		Calendar startDate = Calendar.getInstance(); 
		startDate.setTime(avmVersions.getEffectiveStartDate());
		Calendar endDate = Calendar.getInstance(); 
		endDate.setTime(avmVersions.getEffectiveEndDate());
		today = DateUtils.truncate(today, Calendar.DATE);
		startDate = DateUtils.truncate(startDate, Calendar.DATE);
		endDate = DateUtils.truncate(endDate, Calendar.DATE);
		if(startDate.before(today) || startDate.equals(today)) {
			if(endDate.equals(today) || endDate.after(today)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isFuture(AVMVersions avmVersions) {
		Calendar today = Calendar.getInstance();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(avmVersions.getEffectiveStartDate());
		today = DateUtils.truncate(today, Calendar.DATE);
		startDate = DateUtils.truncate(startDate, Calendar.DATE);
		if(startDate.after(today))
			return true;
		
		return false;
	}

	@Override
	public int getCountLevelApprovalModel(UUID avmId)
			throws FifException {
		int countLevel = 0;
		AVMVersions avmVersions = getCurrentAVMVersions(avmId, new Date());
		if (avmVersions != null) {
			int versionNumber = getLastVersionNumber(avmId); 
			avmVersions = getAVMVersionsByNumberVersion(avmId, versionNumber);
			countLevel = avmSetupManager.getTheLastLevelSequence(avmId, avmVersions.getAvmVersionId()) + 1;
		}
		return countLevel;
	}

	@Override
	public AVMVersions getCurrentAVMVersions(UUID avmId, Date currentDate) throws FifException {
		return avmSetupManager.getCurrentActiveVersion(avmId, DateUtils.truncate(currentDate, Calendar.DATE));
	}

	@Override
	public AVM getAVMById(UUID avmId) throws FifException {
		return avmSetupManager.getAVMById(avmId);
	}

	@Override
	public long getCountAVM(String avmName) throws FifException {
		return avmSetupManager.countAVM(avmName);
	}

	// After Document Checking
	@Override
	@Transactional(rollbackFor={Exception.class,FifException.class})
	public AVMReturnCode submitAvmTransaction(UUID avmTrxId, UUID submitterId,
			Object application, TrxType trxType, Long companyId)
			throws Exception {
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		Long transactionId = trxType.getCode();
		
		ApprovalModelMappingDetail approvalModelMappingDetail = getApplicableApprovalModelMapping(transactionId, companyId, application, DateUtil.truncate(new Date()));
		
		if (approvalModelMappingDetail != null && approvalModelMappingDetail.getApprovalModel() != null) {
			if (approvalModelMappingDetail.getApprovalModel().equals(WorkflowApprovalModelMappingAddComposer.NO_APPROVAL_MODEL_UUID)){
				//ketika transaction tidak membutuhkan approval
				returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
			} else {
				String subject = getSubjectMessage(approvalModelMappingDetail.getApprovalModel(), application, companyId, trxType.getCode(), approvalModelMappingDetail.getPriority());
				returnCode = avmEngineHcms.setUpNewAVMTransaction(avmTrxId, approvalModelMappingDetail.getApprovalModel(), submitterId, application, transactionId.intValue(), subject, companyId);
				if (returnCode.equals(AVMReturnCode.NO_APPLICABLE_AVM_LEVEL_FOUND) ||
						returnCode.equals(AVMReturnCode.NO_APPLICABLE_AVM_FOUND)) {
					sendNotificationErrorToAdmin(returnCode, subject);
					throw new FifException(returnCode.getCode(), returnCode.getMessage());
				} else {
					doSendMessage(approvalModelMappingDetail.getApprovalModel(), avmTrxId, returnCode, submitterId, application, trxType, companyId, DateUtil.truncate(new Date()));
				}
			}
			workflowResolverServiceImpl.doAfterApprovalProcess(returnCode, transactionId, null, application);
		} else {
			// ketika tidak ada mapping approval yang ditemukan
			returnCode = AVMReturnCode.NO_APPLICABLE_AVM_FOUND;			
			sendNotificationErrorToAdmin(returnCode, trxType.getMessage());
			throw new FifException(returnCode.getCode(), returnCode.getMessage());
		}
		return returnCode;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void sendNotificationErrorToAdmin(AVMReturnCode returnCode, String errorMessage) throws Exception {
		NotificationMessage message = new NotificationMessage();
		message.setFromId(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
		message.setToId(UUID.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN));
		message.setMessageType(MessageType.FYI_MESSAGE.getCode());
		message.setReceivedTime(new Date());
		message.setSubject(returnCode.getMessage());
		message.setContentMessage("<p>Error Message :"+ returnCode.getMessage()+" </p>" +
				"For transaction : " + errorMessage);
		templateMessageServiceImpl.insertNewMessage(message);
	}
	
	@Override
	public ApprovalModelMappingDetail getApplicableApprovalModelMapping(Long transactionId, Long companyId, Object application, Date transactionDate) {
		
		ApprovalModelMappingHeader mappingHeader = approvalModelMappingServiceImpl.getApprovalModelMappingHeaderByTransactionId(transactionId, companyId, DateUtil.truncate(transactionDate));
		if (mappingHeader != null) {
			return getApplicableApprovalModelMappingDetail(mappingHeader.getModelMappingId(), application);
		} else {
			mappingHeader = approvalModelMappingServiceImpl.getApprovalModelMappingHeaderByTransactionId(transactionId, ScopeType.COMMON.getValue(), DateUtil.truncate(transactionDate));
			if (mappingHeader != null) {
				return getApplicableApprovalModelMappingDetail(mappingHeader.getModelMappingId(), application);
			} else {
				return null;
			}
		}
	}
	
	public ApprovalModelMappingDetail getApplicableApprovalModelMappingDetail(Long modelMappingId, Object application) {
		ApprovalModelMappingDetail approvalModelMappingDetail = null;
		Long prioritySequence = (long) 0;
		
		try {
			boolean result = false;
			do {
				String ruleExpression = "";
				approvalModelMappingDetail = approvalModelMappingServiceImpl.getApprovalModelMappingDetailByHeaderIdAndPriority(modelMappingId, prioritySequence++);
				
				if (approvalModelMappingDetail != null) {
					if (approvalModelMappingDetail.getConditionRule() != null) {
						ruleExpression = approvalModelMappingDetail.getConditionRule();
					}
//					avmRuleEvaluationManager.setActiontype(AVMRuleEvaluationManager.RULE_EVALUATION);
//					avmRuleEvaluationManager.setRuleexpression(ruleExpression);
//					avmRuleEvaluationManager.setApplication(application);
					
					result = avmRuleEvaluationManager.evaluateRule(AVMRuleEvaluationManager.RULE_EVALUATION, ruleExpression, application);
				}
			} while (!result && approvalModelMappingDetail != null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		if (approvalModelMappingDetail != null) {
			return approvalModelMappingDetail;
		} else {
			return null;
		}		
	}
	
	@Transactional(readOnly=false)
	public void doSendMessage(UUID avmId, UUID avmTrxId, AVMReturnCode returnCode, UUID submitterId, Object application, TrxType trxType, Long companyId, Date transactionDate) throws Exception {
		if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_COMPLETED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId, AVMActionType.APPROVE_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());
			
			templateMessageServiceImpl.sendNotificationMessage(avmId, application, avmApprovalHistory, submitterId, AVMActionType.APPROVE_TRX.getCode(), trxType, companyId, transactionDate);
			
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_IN_PROGRESS)) {
			List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
			approvalHistories = avmApprovalManagerHcms.getApproverReadyByAVMTrxId(avmTrxId);
			templateMessageServiceImpl.sendMultipleNotificationMessage(avmId, approvalHistories, AVMActionType.SUBMIT_TRX.getCode(), trxType, submitterId, application, companyId, transactionDate);
			
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_REJECTED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId, AVMActionType.REJECT_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());
			
			templateMessageServiceImpl.sendNotificationMessage(avmId, application, avmApprovalHistory, submitterId, AVMActionType.REJECT_TRX.getCode(), trxType, companyId, transactionDate);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_CANCELLED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId, AVMActionType.CANCEL_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());
			
			templateMessageServiceImpl.sendNotificationMessage(avmId, application, avmApprovalHistory, submitterId, AVMActionType.REJECT_TRX.getCode(), trxType, companyId, transactionDate);
		}
	}	

	@Override
	public List<AVMOutgoingReportDTO> getAVMOutgoingReport(UUID submitterId,
			int trxType, String subject, int scopeId, int trxStatus, Date submitedDateFrom, Date submitedDateTo) throws FifException {
		List<AVMOutgoingReport> avmOutgoingReports = avmTransactionManager.getAVMOutgoingReport(submitterId, trxType, subject, scopeId, trxStatus, submitedDateFrom, submitedDateTo);
		List<AVMOutgoingReportDTO> avmOutgoingReportDTOs = new ArrayList<AVMOutgoingReportDTO>();
		for (AVMOutgoingReport avmOutgoingReport : avmOutgoingReports) {
			AVMOutgoingReportDTO avmOutgoingReportDTO = modelMapper.map(avmOutgoingReport, AVMOutgoingReportDTO.class);
			try {
				avmOutgoingReportDTO.setTransactionName(TrxType.fromCode((long) avmOutgoingReportDTO.getTrxType()).getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			avmOutgoingReportDTOs.add(avmOutgoingReportDTO);
		}
		return avmOutgoingReportDTOs;
	}

	@Override
	public List<AVMApprovalHistory> getApprovalHistoryByAVMTrxId(UUID avmTrxId)
			throws FifException {
		return avmEngineHcms.getApprovalHistoryByAVMTrxId(avmTrxId);
	}

	@Override
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(
			UUID approverId, String subject, int trxType, int companyId, Date submitedDateFrom, Date submitedDateTo,
			KeyValue person) throws FifException {
		PersonAssignmentDTO personAssignment = null;
		UUID personUUID = null;
		if (person != null) {
			personAssignment = personService.getAssignmentByPersonUUID((UUID) person.getKey());
			personUUID = (UUID) person.getKey();
		}
		if (personAssignment != null) {
			
			List<UUID> rolesId = new ArrayList<UUID>();
			List<UUID> jobRoles = new ArrayList<UUID>();
			UUID deptOwnerId = null;
			UUID jobId = null;
			UUID jobGroupCode = null;
			Long orgId = null;
			Long branchId = null;
			Long companyPerson = personAssignment.getCompanyId();
			List<Long> companiesGroup = new ArrayList<Long>();
			if (personAssignment.getPersonId() != null) {
				if (personAssignment.getPrimaryAssignmentDTO().getJobId() != null) {
					JobDTO job = jobSetupServiceImpl.findById(personAssignment.getPrimaryAssignmentDTO().getJobId());
					if (job != null) {
						jobId = job.getJobUuid();
						ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(job.getJobGroupCode());
						if (mapping != null) 
							jobGroupCode = mapping.getApproverId();
					}				
				}
				
				orgId = personAssignment.getPrimaryAssignmentDTO().getOrganizationId();
				Long branchOwnerId = organizationSetupServiceImpl.getBranchOwnerByOrganizationId(personAssignment.getPrimaryAssignmentDTO().getOrganizationId());
				branchId = branchOwnerId != null ? branchOwnerId : 0L;
				if (personAssignment.getRoles() != null && personAssignment.getRoles().size() > 0) {
					for (String role : personAssignment.getRoles()) {
						if (role.equals("PICDO")) {
							ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(role);
							if (mapping != null) 
								deptOwnerId = mapping.getApproverId();
						} else if (role.equals("HCAdm") || role.equals("OSSH")) {
							ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(role);
							if (mapping != null) 
								jobRoles.add(mapping.getApproverId());
						} else {
							ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(role);
							if (mapping != null) 
								rolesId.add(mapping.getApproverId());
						}
					}
					companiesGroup = companyServiceImpl.getCompanyInBusinessGroup(personAssignment.getCompanyId());
				}
				
				if (rolesId.size() < 1) {
					rolesId = null;
				}
				
				if (jobRoles.size() < 1) {
					jobRoles = null;
				}
			}
				
			return avmEngineHcms.getPendingApprovalByApproverIdAndTrxType(jobId, rolesId, jobRoles, personUUID, deptOwnerId, orgId, branchId, subject, trxType, companyPerson, jobGroupCode, companiesGroup, submitedDateFrom, submitedDateTo, approverId, companyId);
		} else {
			return avmEngineHcms.getPendingApprovalByApproverIdAndTrxType(approverId, subject, trxType, companyId, submitedDateFrom, submitedDateTo, personUUID);
		}
	}

	@Override
	public List<AVMApprovalProcessData> getApprovalProcess(int companyId,
			int trxType, int actionType, Date receivedDateFrom,
			Date receivedDateTo, List<PersonDTO> listPerson) throws FifException {
		List<UUID> listUUID = null;
		if (listPerson != null) {
			listUUID = new ArrayList<UUID>();
			if (listPerson.size() > 0) {
				for (PersonDTO person : listPerson) {
					listUUID.add(person.getPersonUUID());
				}				
			}
		}
		return avmApprovalManagerHcms.getApprovalProcess(companyId, trxType, actionType, receivedDateFrom, receivedDateTo, listUUID);
	}

	@Override
	public List<AVMApprovalProcessData> getCompletedTaskByApproverId(
			UUID approverId, String subject, int trxType, int trxStatus,
			Date approvedDateFrom, Date approvedDateTo) throws FifException {
		return avmApprovalManagerHcms.getCompletedTaskByApproverId(approverId, subject, trxType, trxStatus, approvedDateFrom, approvedDateTo);
	}

	@Override
	public List<AVMApplicationDataDTO> getRejectedTransaction(UUID submitterId, int transactionId, String subject)
			throws FifException {
		List<AVMApplicationData> avmApplicationDatas = avmEngineHcms.getRejectedTransactions(submitterId, transactionId, subject);
		List<AVMApplicationDataDTO> avmApplicationDataDtos = new ArrayList<AVMApplicationDataDTO>();
		for (AVMApplicationData avmApplicationData : avmApplicationDatas) {
			AVMApplicationDataDTO avmApplicationDataDTO = modelMapper.map(avmApplicationData, AVMApplicationDataDTO.class);
			avmApplicationDataDTO.setTransactionName(TrxType.fromCode((long) avmApplicationDataDTO.getTrxType()).getMessage());
			avmApplicationDataDTO.setName(getName(avmApplicationData.getSubmitterId(), null));
			avmApplicationDataDtos.add(avmApplicationDataDTO);
		}
		return avmApplicationDataDtos;
	}

	@Override
	public AVMApprovalHistory getLastApprovalHistory(UUID avmTrxId)
			throws FifException {
		return avmApprovalManagerHcms.getLastApprovalHistory(avmTrxId);
	}

	@Override
	public String getApprovalPath(AVMApplicationData applicationData)
			throws FifException {
		AVMTransaction avmTransaction = avmEngineHcms.getAVMTransactionById(applicationData.getAvmTrxId());
		List<AVMApprover> listApprovers = avmEngineHcms.getApplicableLevelApproverList(avmTransaction);
		
		StringBuilder sb = new StringBuilder();
		int initiateLevel = listApprovers.get(0).getLevelSequence();
		
		for (AVMApprover avmApprover : listApprovers) {
			String name = getApproverName(avmApprover);			
			
			if (initiateLevel == avmApprover.getLevelSequence()) {
				sb.append(name);
			} else {
				initiateLevel = avmApprover.getLevelSequence();
				sb.append(" > ")
					.append(name);
			}
			
			if (initiateLevel == avmTransaction.getLevelSequence()) {
				sb.append("*");
			}
		}	
		
		return sb.toString();
	}

	@Override
	@Transactional(rollbackFor={Exception.class,FifException.class})
	public AVMReturnCode doApproveTransaction(UUID avmTrxId, UUID approverId,
			String remarks, byte[] serializedNewData, byte[] serializedOldData, UUID actualApproverId) throws ValidationException, Exception {
		AVMReturnCode returnCode = null;
		Object application = null;
		int transactionStatus = avmEngineHcms.checkAVMApprovalIntegrity(avmTrxId, approverId);
		if (transactionStatus == TransactionStatusType.AVM_APPROVAL_IS_VALID.getCode()) {
			AVMTransaction currentAVMTransaction = avmTransactionManager.getAVMTransactionById(avmTrxId);
			
			workflowResolverServiceImpl.doValidateBeforeApprove((long) currentAVMTransaction.getTrxType(), serializedOldData);
		
			UUID submitterId = avmApprovalManagerHcms.getTransactionSubmitter(avmTrxId);
			 
			// update application data when approver change value in transaction detail
			if (serializedNewData != null) {
				application = Serialization.deserializedObject(serializedNewData);
				avmTransactionManager.updateApplicationData(avmTrxId, serializedNewData);
				currentAVMTransaction.setSerializedData(serializedNewData);
			} else {
				application = Serialization.deserializedObject(serializedOldData);
			}
			returnCode = avmEngineHcms.approveTransaction(avmTrxId, approverId, remarks, actualApproverId);
			workflowResolverServiceImpl.doApproveTransaction((long)currentAVMTransaction.getTrxType(), application, avmTrxId, approverId);
			workflowResolverServiceImpl.doAfterApprovalProcess(returnCode, (long)currentAVMTransaction.getTrxType(), currentAVMTransaction, null);
			doSendMessage(currentAVMTransaction.getAvmId(), avmTrxId, returnCode, submitterId, application, TrxType.fromCode((long)currentAVMTransaction.getTrxType()), currentAVMTransaction.getCompanyId(), DateUtil.truncate(currentAVMTransaction.getAvmTimeStamp()));
			
		} else {
			if (transactionStatus == TransactionStatusType.APPROVER_HAS_ALREADY_TAKEN_ACTION.getCode()) {
				returnCode = AVMReturnCode.APPROVER_HAS_ALREADY_TAKEN_ACTION;
			} else if (transactionStatus == TransactionStatusType.TRANSACTION_IS_NOT_IN_PROGRESS.getCode()) {
				returnCode = AVMReturnCode.TRANSACTION_IS_NOT_AVAILABLE;
			} else if (transactionStatus == TransactionStatusType.TRANSACTION_IS_NOT_AVAILABLE.getCode()) {
				returnCode = AVMReturnCode.TRANSACTION_IS_NOT_AVAILABLE;
			} else if (transactionStatus == TransactionStatusType.INCOMPATIBLE_LEVEL_SEQUENCE.getCode()) {
				returnCode = AVMReturnCode.INCOMPATIBLE_LEVEL_SEQUENCE;
			}			
		}
		
		return returnCode;
	}

	@Override
	@Transactional(rollbackFor={Exception.class,FifException.class})
	public AVMReturnCode doRejectTransaction(UUID avmTrxId, UUID approverId,
			String remarks, byte[] serializedOldData, UUID actualApproverId) throws ValidationException, Exception {
		AVMReturnCode returnCode = null;
		int transactionStatus = avmEngineHcms.checkAVMApprovalIntegrity(avmTrxId, approverId);
		if (transactionStatus == TransactionStatusType.AVM_APPROVAL_IS_VALID.getCode()) {
			AVMTransaction currentAVMTransaction = avmTransactionManager.getAVMTransactionById(avmTrxId);
			workflowResolverServiceImpl.doValidateBeforeReject((long) currentAVMTransaction.getTrxType(), serializedOldData);
			returnCode = avmEngineHcms.rejectTransaction(avmTrxId, approverId, remarks, actualApproverId);
			if (returnCode != null) {
				Object application = Serialization.deserializedObject(currentAVMTransaction.getSerializedData());
				UUID submitterId = avmApprovalManagerHcms.getTransactionSubmitter(avmTrxId);
				doSendMessage(currentAVMTransaction.getAvmId(), avmTrxId, returnCode, submitterId, application, TrxType.fromCode((long)currentAVMTransaction.getTrxType()), currentAVMTransaction.getCompanyId(), DateUtil.truncate(currentAVMTransaction.getAvmTimeStamp()));
				workflowResolverServiceImpl.doAfterApprovalProcess(returnCode, (long)currentAVMTransaction.getTrxType(), currentAVMTransaction, null);				
			}			
		} else {
			if (transactionStatus == TransactionStatusType.APPROVER_HAS_ALREADY_TAKEN_ACTION.getCode()) {
				returnCode = AVMReturnCode.APPROVER_HAS_ALREADY_TAKEN_ACTION;
			} else if (transactionStatus == TransactionStatusType.TRANSACTION_IS_NOT_IN_PROGRESS.getCode()) {
				returnCode = AVMReturnCode.TRANSACTION_IS_NOT_AVAILABLE;
			} else if (transactionStatus == TransactionStatusType.TRANSACTION_IS_NOT_AVAILABLE.getCode()) {
				returnCode = AVMReturnCode.TRANSACTION_IS_NOT_AVAILABLE;
			} else if (transactionStatus == TransactionStatusType.INCOMPATIBLE_LEVEL_SEQUENCE.getCode()) {
				returnCode = AVMReturnCode.INCOMPATIBLE_LEVEL_SEQUENCE;
			}			
		}
		
		return returnCode;
	}

	@Override
	@Transactional(readOnly=false)
	public void pullApplicationData(UUID avmTrxId) throws FifException {
		avmTransactionManager.pullApplicationData(avmTrxId);
	}

	@Override
	@Transactional(rollbackFor={Exception.class,FifException.class})
	public AVMReturnCode doCancelTransaction(UUID avmTrxId, UUID approverId,
			String remarks, boolean overideMessage) throws FifException {
		AVMReturnCode returnCode = null;
		try {
			AVMTransaction currentAVMTransaction = avmTransactionManager.getAVMTransactionById(avmTrxId);
			UUID submitterId = avmApprovalManagerHcms.getTransactionSubmitter(avmTrxId);
			Object application = Serialization.deserializedObject(currentAVMTransaction.getSerializedData());
			returnCode = avmEngineHcms.cancelTransaction(avmTrxId, approverId, remarks);
			if (!overideMessage) {
				doSendMessage(currentAVMTransaction.getAvmId(), avmTrxId, returnCode, submitterId, application, TrxType.fromCode((long)currentAVMTransaction.getTrxType()), currentAVMTransaction.getCompanyId(), currentAVMTransaction.getAvmTimeStamp());				
			}
			workflowResolverServiceImpl.doAfterApprovalProcess(returnCode, (long)currentAVMTransaction.getTrxType(), currentAVMTransaction, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), AVMReturnCode.ERROR_MANAGER.getMessage());
		}
		return returnCode;
	}

	@Override
	public List<AVMApprovalHistory> doReassignApprover(UUID avmTrxId, int sequenceNumber,
			UUID newApproverId, String remarks) throws FifException {
		return avmEngineHcms.reassignApproverByAdmin(avmTrxId, sequenceNumber, newApproverId, remarks);
	}

	@Override
	@Transactional(readOnly=false)
	public void closeMultipleTransaction(Set<AVMApplicationDataDTO> listTransaction)
			throws FifException {
		Iterator<AVMApplicationDataDTO> iterator = new LinkedHashSet<AVMApplicationDataDTO>(listTransaction).iterator();
		while (iterator.hasNext()) {
			avmTransactionManager.closeTransaction(iterator.next().getAvmTrxId());
		}
	}

	@Override
	public AVMApprovalHistory getRecentApprovalHistory(UUID avmTrxId,
			int sequenceNumber) throws FifException {
		return avmApprovalManagerHcms.getRecentApproversApprovalHistory(avmTrxId, sequenceNumber);
	}

	@Override
	public UUID getTransactionSubmitter(UUID avmTrxId) throws FifException {
		return avmApprovalManagerHcms.getTransactionSubmitter(avmTrxId);
	}

	@Override
	public Date getLastCompletedActionApproval(UUID avmTrxId)
			throws FifException {
		return avmApprovalManagerHcms.getLastCompletedActionApproval(avmTrxId);
	}

	@Override
	@Transactional(readOnly=false)
	public AVMReturnCode doMultipleCancelTransaction(
			Set<AVMOutgoingReport> listTransaction, UUID approverId,
			String remarks) throws FifException {
		Iterator<AVMOutgoingReport> iterator = new LinkedHashSet<AVMOutgoingReport>(listTransaction).iterator();
		AVMReturnCode returnCode = null;
		while (iterator.hasNext()) {
			try {
				returnCode = doCancelTransaction(iterator.next().getAvmTrxId(), approverId, remarks, false);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return returnCode;
	}

	@Override
	@Transactional(readOnly=false)
	public AVMReturnCode doMultipleReassignApprover(
			Set<AVMApplicationData> listTransaction, UUID newApproverId,
			String remarks) throws FifException, Exception {
		Iterator<AVMApplicationData> iterator = new LinkedHashSet<AVMApplicationData>(listTransaction).iterator();
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		while (iterator.hasNext()) {
			AVMApplicationData applicationData = iterator.next();
			List<AVMApprovalHistory> approvalHistories = doReassignApprover(applicationData.getAvmTrxId(), applicationData.getSequenceNumber(), newApproverId, remarks);
			Object serializedData = Serialization.deserializedObject(applicationData.getSerializedData());
			UUID submitterId = getTransactionSubmitter(applicationData.getAvmTrxId());
			AVMTransaction transaction = avmTransactionManager.getAVMTransactionById(applicationData.getAvmTrxId());
			templateMessageServiceImpl.sendMultipleNotificationMessage(transaction.getAvmId(), approvalHistories, AVMActionType.SUBMIT_TRX.getCode(), TrxType.fromCode((long) applicationData.getTrxType()), submitterId, serializedData, applicationData.getCompanyId(), transaction.getAvmTimeStamp());
		}
		return returnCode;
	}

	@Override
	@Transactional(readOnly=false)
	public AVMReturnCode doMultipleApproveTransaction(
			Set<AVMApplicationData> listApproval, 
			String remarks, UUID actualApproverId) throws ValidationException, FifException, Exception {
		Iterator<AVMApplicationData> iterator = new LinkedHashSet<AVMApplicationData>(listApproval).iterator();
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		while (iterator.hasNext()) {
			AVMApplicationData applicationData = iterator.next();
			AVMApprovalHistory approval = getRecentApprovalHistory(applicationData.getAvmTrxId(), applicationData.getSequenceNumber());
			returnCode = doApproveTransaction(applicationData.getAvmTrxId(), approval.getApproverId(), remarks, null, applicationData.getSerializedData(), actualApproverId);
		}
		return AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
	}

	@Override
	@Transactional(readOnly=false)
	public AVMReturnCode doMultipleRejectTransaction(
			Set<AVMApplicationData> listApproval, 
			String remarks, UUID actualApproverId) throws ValidationException, FifException, Exception {
		Iterator<AVMApplicationData> iterator = new LinkedHashSet<AVMApplicationData>(listApproval).iterator();
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		while (iterator.hasNext()) {
			AVMApplicationData applicationData = iterator.next();
			AVMApprovalHistory approval = getRecentApprovalHistory(applicationData.getAvmTrxId(), applicationData.getSequenceNumber());
			returnCode = doRejectTransaction(applicationData.getAvmTrxId(), approval.getApproverId(), remarks, applicationData.getSerializedData(), actualApproverId);
		}
		return AVMReturnCode.TRANSACTION_IS_REJECTED;
	}

	@Override
	@Transactional(readOnly=false)
	public void sendMultipleFYIMessage(List<Employee> listReceiverMessage, int actionType, AVMApplicationData applicationData)
			throws Exception {
		AVMTransaction currentTransaction = avmTransactionManager.getAVMTransactionById(applicationData.getAvmTrxId());
		Object application = Serialization.deserializedObject(currentTransaction.getSerializedData());
		AVMApprovalHistory lastApprover = avmApprovalManagerHcms.getLastApprovalHistory(applicationData.getAvmTrxId());
		List<UUID> listUUID = new ArrayList<UUID>();
		for (Employee dummy : listReceiverMessage) {
			listUUID.add(dummy.getEmployeeUUID());
		}
		templateMessageServiceImpl.sendMultipleFYIMessage(currentTransaction.getAvmId(), application, listUUID, lastApprover.getApproverId(), actionType, currentTransaction.getTrxType(), currentTransaction.getCompanyId(), lastApprover.getLevelSequence(), DateUtil.truncate(currentTransaction.getAvmTimeStamp()), currentTransaction.getAvmTrxId());
		
	}

	@Override
	public List<AVMApplicationDataDTO> getPendingApproval(SecurityProfile profile,
			String subject, int trxType, Long companyId, Date submitedDateFrom,
			Date submitedDateTo) throws FifException {
		List<AVMApplicationDataDTO> avmApplicationDataDtos = new ArrayList<AVMApplicationDataDTO>();
		List<UUID> rolesId = new ArrayList<UUID>();
		List<UUID> jobRoles = new ArrayList<UUID>();
		UUID deptOwnerId = null;
		UUID jobId = null;
		UUID jobGroupCode = null;
		List<Long> companiesGroup = new ArrayList<Long>();
		if (profile.getPersonId() != null) {
			if (profile.getJobId() != null) {
				JobDTO job = jobSetupServiceImpl.findById(profile.getJobId());
				if (job != null) {
					jobId = job.getJobUuid();
					ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(job.getJobGroupCode());
					if (mapping != null) 
						jobGroupCode = mapping.getApproverId();
				}				
			}
			UUID personUUID = profile.getPersonUUID();
			Long orgId = profile.getOrganizationId();
			Long branchOwnerId = organizationSetupServiceImpl.getBranchOwnerByOrganizationId(profile.getOrganizationId());
			Long branchId = branchOwnerId != null ? branchOwnerId : 0L;
			if (profile.getRoles() != null && profile.getRoles().size() > 0) {
				for (String role : profile.getRoles()) {
					if (role.equals("PICDO")) {
						ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(role);
						if (mapping != null) 
							deptOwnerId = mapping.getApproverId();
					} else if (role.equals("HCAdm") || role.equals("OSSH")) {
						ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(role);
						if (mapping != null) 
							jobRoles.add(mapping.getApproverId());
					} else {
						ApproverMapping mapping = approverMappingServiceImpl.getApproverMappingByName(role);
						if (mapping != null) 
							rolesId.add(mapping.getApproverId());
					}
				}
				companiesGroup = companyServiceImpl.getCompanyInBusinessGroup(companyId);
			}
			
			if (rolesId.size() < 1) {
				rolesId = null;
			}
			
			if (jobRoles.size() < 1) {
				jobRoles = null;
			}
			
			List<AVMApplicationData> avmApplicationDatas = avmEngineHcms.getPendingApprovalByApproverIdAndTrxType(jobId, rolesId, jobRoles, personUUID, deptOwnerId, orgId, branchId, subject, trxType, companyId, jobGroupCode, companiesGroup);
			
			for (AVMApplicationData avmApplicationData : avmApplicationDatas) {
				AVMApplicationDataDTO avmApplicationDataDTO = modelMapper.map(avmApplicationData, AVMApplicationDataDTO.class);
				avmApplicationDataDTO.setTransactionName(TrxType.fromCode((long) avmApplicationDataDTO.getTrxType()).getMessage());
				AVMApprovalHistory approvalHistory = getLastApprovalHistory(avmApplicationDataDTO.getAvmTrxId());
				if (approvalHistory != null) {
					avmApplicationDataDTO.setName(getActualName(approvalHistory, avmApplicationDataDTO.getCompanyId()));
					avmApplicationDataDTO.setLastRemarks(approvalHistory.getRemarks());
					avmApplicationDataDTO.setApprovedTime(approvalHistory.getAvmActionTimeStamp());
				} 
				avmApplicationDataDtos.add(avmApplicationDataDTO);
			}
		}
		return avmApplicationDataDtos; 
	}
	
	private String getSubjectMessage(UUID avmId, Object application, Long companyId, Long trxType, Long priority) throws FifException, Exception {
		String subject = "";
		AVMVersions currentVersion = avmEngineHcms.getApplicableAVMVersion(avmId);
		if (currentVersion != null) {
			AVMLevel applicableAVMLevelSequence = 
					avmEngineHcms.getApplicableLevelSequence(currentVersion, AVMSetupManager.INITIAL_LEVEL_SEQUENCE, application);
			if (applicableAVMLevelSequence != null) {
				TemplateMessageMapping templateMessageMapping = templateMessageServiceImpl.getTemplateMessageMappingByCriteria(companyId, avmId, trxType, (long) applicableAVMLevelSequence.getLevelSequence(), ActionType.SUBMIT, DateUtil.truncate(new Date()), priority);
				if (templateMessageMapping == null) {
					templateMessageMapping = templateMessageServiceImpl.getTemplateMessageMappingByCriteria(companyId, avmId, trxType, (long) 0, ActionType.SUBMIT, DateUtil.truncate(new Date()),priority);
				}
				if (templateMessageMapping == null) {
					templateMessageMapping = templateMessageServiceImpl.getTemplateMessageMappingByCriteria(ScopeType.COMMON.getValue(), avmId, trxType, (long) 0, ActionType.SUBMIT, DateUtil.truncate(new Date()), priority);
				}
				TemplateMessage templateMessage = templateMessageServiceImpl.getTemplateMessageById(templateMessageMapping.getTemplateId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("object", application);
				
				List<ParameterMessage> parameterMessages = templateMessageServiceImpl.getParameterListByTransactionId(trxType);
				String contentMessage = templateMessage.getTemplateContent();
				if (parameterMessages.size() > 0) {
					for (ParameterMessage parameter : parameterMessages) {
						contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()), Matcher.quoteReplacement(parameter.getParameterKey()));
					}
					templateMessage.setTemplateContent(contentMessage);
				}
				
				Message message = templateMessageServiceImpl.templateMessageResolver(templateMessage, map);
				subject = message.getSubject();
			}
		}
		return subject;
	}
	
	public String getApproverName(AVMApprover avmApprover) {
		String name = "";
		if (avmApprover.getApproverType() == ApproverType.DepartmentOwner.getCode() ||
				avmApprover.getApproverType() == ApproverType.Supervisor.getCode() ||
				avmApprover.getApproverType() == ApproverType.Manager.getCode() ||
				avmApprover.getApproverType() == ApproverType.DepartmentHead.getCode() ||
				avmApprover.getApproverType() == ApproverType.JobOwner.getCode()) {
				
				ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByApproverId(avmApprover.getApproverId());
				name = approverMapping != null ? approverMapping.getApproverName() : "";
			} else if (avmApprover.getApproverType() == ApproverType.Employee.getCode()) {
				KeyValue person = workflowLookupServiceAdapterImpl.getValueEmployee(avmApprover.getApproverId());
				name =  (person!=null ?person.getDescription().toString(): null);
			} else if (avmApprover.getApproverType() == ApproverType.Role.getCode() ||
					avmApprover.getApproverType() == ApproverType.JobRole.getCode()) {
				ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByApproverId(avmApprover.getApproverId());
				List<KeyValue> listRole = lookupServiceImpl.getLookupKeyValues(WorkflowReference.PEA_ROLE.name(), approverMapping.getApproverName());
				name = listRole.size() > 0 ? listRole.get(0).getDescription().toString() : ""; 
			} else if (avmApprover.getApproverType() == ApproverType.JobGroup.getCode()) {
				ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByApproverId(avmApprover.getApproverId());
				List<KeyValue> listJobGroup = lookupServiceImpl.getLookupKeyValues(WorkflowReference.WSU_JOB_GROUP.name(), approverMapping.getApproverName());
				name = listJobGroup.size() > 0 ? listJobGroup.get(0).getDescription().toString() : ""; 
			} else if (avmApprover.getApproverType() == ApproverType.Job.getCode()) {
				KeyValue job = workflowLookupServiceAdapterImpl.getJobByUUID(avmApprover.getApproverId());
				name = job != null ? job.getDescription().toString() : "";
			}
		
		return name;
	}

	@Override
	public String getName(UUID personUUID, Long companyId) {
		if (personUUID != null) {
			if (personUUID.toString().toUpperCase().equals(AVMApprovalManager.APPROVER_SYSTEM)) {
				return "System";
			} else if (personUUID.toString().toUpperCase().equals(AVMApprovalManager.APPROVER_SWF_ADMIN)) {
				return "Workflow Admin";
			} else {
				PersonDTO person =  personService.getSimplePersonByUUID(personUUID, DateUtil.truncate(new Date()));
				if (person != null) 
					return person.getEmployeeNumber() + "-" + person.getFullName();
				
				/*KeyValue keyValue = CwkPersonDTOServiceImpl.getCwkByUuuid(personUUID);
				if (keyValue != null)
					return keyValue.getValue().toString() + "-" + keyValue.getDescription();*/
				
				ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByApproverId(personUUID);
				if (approverMapping != null) {
					if (approverMapping.getApproverType().equalsIgnoreCase(ApproverType.Role.name())
							|| approverMapping.getApproverType().equalsIgnoreCase(ApproverType.JobRole.name())
							|| approverMapping.getApproverType().equalsIgnoreCase(ApproverType.DepartmentOwner.name())) {
						List<KeyValue> listRole = lookupServiceImpl.getLookupKeyValues(WorkflowReference.PEA_ROLE.name(), approverMapping.getApproverName());
						return listRole.size() > 0 ? listRole.get(0).getDescription().toString() : approverMapping.getApproverName();
					} else if (approverMapping.getApproverType().equalsIgnoreCase(ApproverType.JobGroup.name())) {
						List<KeyValue> listJobGroup = lookupServiceImpl.getLookupKeyValues(WorkflowReference.WSU_JOB_GROUP.name(), approverMapping.getApproverName());
						return listJobGroup.size() > 0 ? listJobGroup.get(0).getDescription().toString() : approverMapping.getApproverName();
					}
					return approverMapping.getApproverName();
				}
				
				KeyValue job = workflowLookupServiceAdapterImpl.getJobByUUID(personUUID);
				if (job != null) 
					return job.getDescription().toString();
				
				return "anonim";			
			}
		} else {
			return "anonim";
		}
	}

	@Override
	public String getCompanyName(Long companyId) {
		String companyName = "";
		CompanyExample example = new CompanyExample();
		example.createCriteria().andCompanyIdEqualTo(companyId)
			.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
		if (listCompany.size() > 0) {
			companyName = listCompany.get(0).getCompanyName();
		}
		return companyName;
	}

	@Override
	public String getActualName(AVMApprovalHistory avmApprovalHistory,
			Long companyId) {
		if (avmApprovalHistory.getApproverId().toString().toUpperCase().equals(AVMApprovalManager.APPROVER_SYSTEM)) {
			return "System";
		} else if (avmApprovalHistory.getApproverId().toString().toUpperCase().equals(AVMApprovalManager.APPROVER_SWF_ADMIN)) {
			return "Workflow Admin";
		} else {
			PersonDTO person = personService.getSimplePersonByUUID(avmApprovalHistory.getApproverId(), DateUtil.truncate(new Date()));
			if (person != null) 
				return person.getEmployeeNumber() + "-" + person.getFullName();

			// job & organization
			List<PersonAssignmentDTO> listPerson;
			Organization org = null;
			Organization branch = null;
			if (avmApprovalHistory.getOrganizationId() != null) {
				org = organizationSetupServiceImpl.findNameById(avmApprovalHistory.getOrganizationId());				
			}
			if (avmApprovalHistory.getBranchId() != null) {
				if (avmApprovalHistory.getBranchId().equals(-1L)) {
					OrganizationDTO ho = organizationSetupServiceImpl.getHeadOffice();
					branch = new Organization();
					branch.setId(ho.getId());
					branch.setOrganizationCode(ho.getBranchCode());
					branch.setOrganizationName(ho.getName());
				} else {
					branch = organizationSetupServiceImpl.findNameById(avmApprovalHistory.getBranchId());					
				}
			}
			JobDTO job = jobSetupServiceImpl.findByUuid(avmApprovalHistory.getApproverId());
			if (job != null && org != null) {
				listPerson = personService.selectPersonByAssignment(job.getId(), avmApprovalHistory.getOrganizationId(), null, null, null, null, null);
				if (listPerson.size() > 0)
					return getListPerson(listPerson);
				else
					return job.getJobName() + " " + org.getOrganizationName();
				
			}
			
			// role & companyId
			ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByApproverId(avmApprovalHistory.getApproverId());
			if (approverMapping.getApproverType().equals(ApproverType.Role.name()) && 
					!approverMapping.getApproverName().equals("HCAdm") &&
					!approverMapping.getApproverName().equals("OSSH") &&
					!approverMapping.getApproverName().equals("PICDO")) {
				listPerson = personService.selectPersonByAssignment(null, null, null, approverMapping.getApproverName(), null, companyId, null);
				if (listPerson.size() > 0)
					return getListPerson(listPerson);
				else
					return approverMapping.getApproverName();
			}
			
			// job & branchId
			if (job != null && branch != null) {
				listPerson = personService.selectPersonByAssignment(job.getId(), null, avmApprovalHistory.getBranchId(), null, null, null, null);
				if (listPerson.size() > 0)
					return getListPerson(listPerson);
				else
					return job.getJobName() + " " + branch.getOrganizationName();				
			}
			
			// role & branchId
			if (approverMapping != null) {
				if ((approverMapping.getApproverName().equals("HCAdm") || approverMapping.getApproverName().equals("OSSH")) && branch != null) {
					listPerson = personService.selectPersonByAssignment(null, null, avmApprovalHistory.getBranchId(), approverMapping.getApproverName(), null, null, null);
					if (listPerson.size() > 0)
						return getListPerson(listPerson);
					else
						return approverMapping.getApproverName() + " " + branch.getOrganizationName();
				}
				
				// role & organizationId
				if (approverMapping.getApproverName().equals("PICDO") && org != null) {
					listPerson = personService.selectPersonByAssignment(null, avmApprovalHistory.getOrganizationId(), null, approverMapping.getApproverName(), null, null, null);
					if (listPerson.size() > 0)
						return getListPerson(listPerson);
					else 
						return approverMapping.getApproverName() + " " + org.getOrganizationName(); 
				}
				
				if (approverMapping.getApproverType().equalsIgnoreCase(ApproverType.JobGroup.name())) {
					listPerson = personService.selectPersonByAssignment(null, null, avmApprovalHistory.getBranchId(), null, null, null, approverMapping.getApproverName());
					if (listPerson.size() > 0) {
						return getListPerson(listPerson);
					} else {
						listPerson = personService.selectPersonByAssignment(null, avmApprovalHistory.getOrganizationId(), null, null, null, null, approverMapping.getApproverName());
						if (listPerson.size() > 0) {
							return getListPerson(listPerson);
						} else {
							List<KeyValue> listJobGroup = lookupServiceImpl.getLookupKeyValues(WorkflowReference.WSU_JOB_GROUP.name(), approverMapping.getApproverName());
							String jobGroup = listJobGroup.size() > 0 ? listJobGroup.get(0).getDescription().toString() : approverMapping.getApproverName();
							return jobGroup + " - " + avmApprovalHistory.getBranchId() != null ? branch.getOrganizationName() : org.getOrganizationName();
						}
					}
				}
			}
			
		}
		return "anonim";
	}
		
	private String getListPerson(List<PersonAssignmentDTO> listPerson) {
		StringBuilder sb = new StringBuilder();
		for (PersonAssignmentDTO person : listPerson) {
			sb.append(person.getEmployeeNumber() + " - " + person.getFullName())
				.append(", ");
		}
		return sb.toString();
	}

	@Override
	public Object getDataTransactionByAvmTrxId(UUID avmTrxId) {
		AVMTransaction trx;
		try {
			trx = avmEngineHcms.getAVMTransactionById(avmTrxId);
			Object serializedData = Serialization.deserializedObject(trx.getSerializedData());
			return serializedData;
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=false)
	public AVMReturnCode doMultipleApproveTransactionBySystem(
			List<AVMOutgoingReportDTO> listApproval, UUID actualApproverId, String remarks) throws FifException, Exception {
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		for (AVMOutgoingReportDTO outgoing : listApproval) {
			try {
				returnCode = doApproveTransactionBySystem(outgoing.getAvmTrxId(), actualApproverId, remarks);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return returnCode;
	}

	@Override
	@Transactional(readOnly=false)
	public AVMReturnCode doApproveTransactionBySystem(UUID avmTrxId,
			UUID approverId, String remarks) throws FifException {
		AVMReturnCode returnCode = null;
		try {
			AVMTransaction currentAVMTransaction = avmTransactionManager.getAVMTransactionById(avmTrxId);
			UUID submitterId = getTransactionSubmitter(avmTrxId);
			Object application = Serialization.deserializedObject(currentAVMTransaction.getSerializedData());
			returnCode = avmEngineHcms.approveTransactionBySystem(avmTrxId, approverId, remarks);
			doSendMessage(currentAVMTransaction.getAvmId(), avmTrxId, returnCode, submitterId, application, TrxType.fromCode((long)currentAVMTransaction.getTrxType()), currentAVMTransaction.getCompanyId(), currentAVMTransaction.getAvmTimeStamp());
			workflowResolverServiceImpl.doAfterApprovalProcess(returnCode, (long)currentAVMTransaction.getTrxType(), currentAVMTransaction, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), AVMReturnCode.ERROR_MANAGER.getMessage());
		}
		return returnCode;
	}
	
	@Override
	@Transactional(readOnly=false)
	public AVMReturnCode doMultipleReassignBySystem(List<AVMOutgoingReport> dataList) {
		for (AVMOutgoingReport outgoing : dataList) {
			try {
				AVMTransaction currentAVMTransaction = avmTransactionManager.getAVMTransactionById(outgoing.getAvmTrxId());
				List<AVMApprover> approverList = avmSetupManager.getAVMApproversByLevelAndPriority(currentAVMTransaction.getAvmId(), currentAVMTransaction.getLevelSequence(), 0, currentAVMTransaction.getAvmVersionId());
				UUID submitterId = getTransactionSubmitter(outgoing.getAvmTrxId());
				String forcedApprovalRemarks = "Employee has change assignment";
				avmApprovalManagerHcms.alterOtherApproversAction(outgoing.getAvmTrxId(), AVMActionType.RE_ASSIGN_BY_ADMIN.getCode(), 
												forcedApprovalRemarks);
				AVMReturnCode returnCode = avmApprovalManagerHcms.notifyApproversNew(approverList, currentAVMTransaction, 0);
				Object application = Serialization.deserializedObject(currentAVMTransaction.getSerializedData());
				doSendMessage(currentAVMTransaction.getAvmId(), currentAVMTransaction.getAvmTrxId(), returnCode, submitterId, application, TrxType.fromCode((long)currentAVMTransaction.getTrxType()), currentAVMTransaction.getCompanyId(), currentAVMTransaction.getAvmTimeStamp());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return null;
	}
	
	@Override
	public int getApproverLevel(AVMApplicationData applicationData)
			throws FifException {
		AVMTransaction avmTransaction = avmEngineHcms.getAVMTransactionById(applicationData.getAvmTrxId());
		List<AVMApprover> listApprovers = avmEngineHcms.getApplicableLevelApproverList(avmTransaction);
		for (int i = 0; i < listApprovers.size(); i++) {
			if (listApprovers.get(i).getLevelSequence() == avmTransaction.getLevelSequence()) {
				return i;
			}
		}	
		return 0;
	}

	@Override
	public List<AVMOutgoingReport> getPendingTransaction(List<KeyValue> listTrxId) throws FifException {
		List<AVMOutgoingReport> avmOutgoingReports = new ArrayList<AVMOutgoingReport>();
		List<UUID> listUUIDtrx = new ArrayList<UUID>();
		if (listTrxId != null) {
			for (KeyValue trx : listTrxId) {
				listUUIDtrx.add((UUID) trx.getKey());
			}
			avmOutgoingReports = avmTransactionManager.getPendingTransaction(listUUIDtrx);
		}
		return avmOutgoingReports;
	}
	
	@Override
	public int updateApplicationData(UUID avmTrxId, byte[] serializedData) throws FifException {
		return avmTransactionManager.updateApplicationData(avmTrxId, serializedData);
	}

	//	(GAL - [15091510574968] Perbaikan NPK double setelah approve) 
	@Override
	public int checkStatusIsApproved(UUID avmTrxId) throws FifException {
		return avmApprovalManagerHcms.checkStatusIsApproved(avmTrxId);
	}
	//
}
