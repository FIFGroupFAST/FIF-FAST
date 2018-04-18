package co.id.fifgroup.systemworkflow.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.Employee;
import co.id.fifgroup.systemworkflow.dto.AVMApplicationDataDTO;
import co.id.fifgroup.systemworkflow.dto.AVMOutgoingReportDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelDTO;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface AvmAdapterService {

	public List<AVM> getAVMByName(String avmName) throws FifException;
	
	public AVM getAVMById(UUID avmId) throws FifException;
	
	public long getCountAVM(String avmName) throws FifException;
	
	public List<AVMVersions> getAllAVMVersion(AVM avm) throws FifException;
	
	public int getLastVersionNumber(UUID avmId) throws FifException;
	
	public AVMVersions getAVMVersionsByNumberVersion(UUID avmId, int numberVersion) throws FifException;
	
	public List<AVMLevel> getAVMLevels(AVM avm, AVMVersions avmVersions) throws FifException;
	
	public List<AVMApprover> getAVMApproversByLevel(AVMLevel avmLevel, AVMVersions avmVersions) throws FifException;
	
	public void saveAVMTemplate(ApprovalModelDTO approvalModelDto, Calendar dateFromBefore) throws FifException, ValidationException;
	
	public void createAVMTemplate(ApprovalModelDTO approvalModelDto, Calendar dateFromBefore) throws FifException, ValidationException;
	
	public void updateAVMTemplate(ApprovalModelDTO approvalModelDto, Calendar dateFromBefore) throws FifException, ValidationException;
	
	public void deleteAVMTemplate(AVMVersions avmVersions) throws FifException;
	
	public boolean isCurrent(AVMVersions avmVersions);
	
	public boolean isFuture(AVMVersions avmVersions);
	
	public int getCountLevelApprovalModel(UUID avmId) throws FifException;
	
	public AVMVersions getCurrentAVMVersions(UUID avmId, Date currentDate) throws FifException;
	
	public AVMReturnCode submitAvmTransaction(UUID avmTrxId, UUID submitterId, Object application, TrxType trxType, Long companyId) throws FifException, Exception;
	
	public List<AVMOutgoingReportDTO> getAVMOutgoingReport(UUID submitterId, int trxType, String subject, int scopeId, int trxStatus, Date submitedDateFrom, Date submitedDateTo) throws FifException;
	
	public List<AVMApprovalHistory> getApprovalHistoryByAVMTrxId(UUID avmTrxId) throws FifException;
	
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(UUID approverId, String subject, int trxType, int companyId, Date submitedDateFrom, Date submitedDateTo, KeyValue person) throws FifException;
	public List<AVMApplicationDataDTO> getPendingApproval(SecurityProfile profile, String subject, int trxType, Long companyId, Date submitedDateFrom, Date submitedDateTo) throws FifException;
	
	public List<AVMApprovalProcessData> getApprovalProcess(int companyId, int trxType, int actionType, Date receivedDateFrom, Date receivedDateTo, List<PersonDTO> listPerson) throws FifException;
	
	public List<AVMApprovalProcessData> getCompletedTaskByApproverId(UUID approverId, String subject, int trxType, int trxStatus, Date approvedDateFrom, Date approvedDateTo) throws FifException;
	
	public List<AVMApplicationDataDTO> getRejectedTransaction(UUID submitterId, int transactionId, String subject) throws FifException;
	
	public AVMApprovalHistory getLastApprovalHistory(UUID avmTrxId) throws FifException;
	
	public String getApprovalPath(AVMApplicationData applicationData) throws FifException;
	
	public AVMReturnCode doApproveTransaction(UUID avmTrxId, UUID approverId, String remarks, byte[] serializedNewData, byte[] serializedOldData, UUID actualApproverId) throws FifException, Exception;
	
	public AVMReturnCode doRejectTransaction(UUID avmTrxId, UUID approverId, String remarks, byte[] serializedOldData, UUID actualApproverId) throws FifException,  Exception;
	
	public void pullApplicationData(UUID avmTrxId) throws FifException;
	
	public AVMReturnCode doCancelTransaction(UUID avmTrxId, UUID approverId, String remarks, boolean overideMessage) throws FifException;
	
	public AVMReturnCode doMultipleCancelTransaction(Set<AVMOutgoingReport> listTransaction, UUID approverId, String remarks) throws FifException;
	
	public List<AVMApprovalHistory> doReassignApprover(UUID avmTrxId, int sequenceNumber, UUID newApproverId, String remarks) throws FifException;
	
	public AVMReturnCode doMultipleReassignApprover(Set<AVMApplicationData> listTransaction, UUID newApproverId, String remarks) throws FifException, Exception;
	
	public void closeMultipleTransaction(Set<AVMApplicationDataDTO> listTransaction) throws FifException;
	
	public AVMApprovalHistory getRecentApprovalHistory(UUID avmTrxId, int sequenceNumber) throws FifException;
	
	public UUID getTransactionSubmitter(UUID avmTrxId) throws FifException;
	
	public Date getLastCompletedActionApproval(UUID avmTrxId) throws FifException;
	
	public AVMReturnCode doMultipleApproveTransaction(Set<AVMApplicationData> listApproval, String remarks, UUID actualApproverId) throws FifException, Exception;
	
	public AVMReturnCode doMultipleRejectTransaction(Set<AVMApplicationData> listApproval, String remarks, UUID actualApproverId) throws FifException, Exception;
	
	public void sendMultipleFYIMessage(List<Employee> listReceiverMessage, int actionType, AVMApplicationData applicationData) throws Exception;
	
	public String getApproverName(AVMApprover avmApprover);
	
	public String getName(UUID personUUID, Long personId);
	
	public String getCompanyName(Long companyId);
	
	public String getActualName(AVMApprovalHistory avmApprovalHistory, Long companyId);
	
	public Object getDataTransactionByAvmTrxId(UUID avmTrxId);
	
	public AVMReturnCode doMultipleApproveTransactionBySystem(List<AVMOutgoingReportDTO> listApproval, UUID actualApproverId, String remarks) throws FifException, Exception;
	
	public AVMReturnCode doApproveTransactionBySystem(UUID avmTrxId, UUID approverId, String remarks) throws FifException;

	public AVMReturnCode doMultipleReassignBySystem(List<AVMOutgoingReport> dataList);
	
	public ApprovalModelMappingDetail getApplicableApprovalModelMapping(Long transactionId, Long companyId, Object application, Date transactionDate);
	
	public void sendNotificationErrorToAdmin(AVMReturnCode returnCode, String errorMessage) throws Exception;
	
	public int getApproverLevel(AVMApplicationData applicationData)	throws FifException;
	
	List<AVMOutgoingReport> getPendingTransaction(List<KeyValue> listTrxId) throws FifException;
	
	public int updateApplicationData(UUID avmTrxId, byte[] serializedData) throws FifException;
	
	public void doSendMessage(UUID avmId, UUID avmTrxId, AVMReturnCode returnCode, UUID submitterId, Object application, TrxType trxType, Long companyId, Date transactionDate) throws Exception;
	
	//cek status trxId (GAL - [15091510574968] Perbaikan NPK double setelah approve)
	public int checkStatusIsApproved(UUID avmTrxId) throws FifException;
}
