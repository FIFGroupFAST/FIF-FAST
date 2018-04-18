package co.id.fifgroup.systemworkflow.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.MessageMapping;
import co.id.fifgroup.systemworkflow.domain.ParameterMessage;
import co.id.fifgroup.systemworkflow.domain.TemplateMessageMapping;
import co.id.fifgroup.systemworkflow.dto.NotificationMessageDTO;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageDTO;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;

public interface TemplateMessageService {

	public List<ParameterMessage> getParameterByTransactionId(Long transactionId, Short listData);
	
	public List<TemplateMessageDTO> getTemplateMessageByCriteria(String templateName, TrxType trxType, ActionType actionType) throws Exception;
	
	public List<TemplateMessageDTO> getTemplateMessageByTransactionIdAndActionType(Long transactionId, ActionType actionType) throws Exception;
	
	public TemplateMessageDTO getTemplateMessageDtoById(Long templateMappingId) throws Exception;
	
	public void saveTemplateMessageDto(TemplateMessageDTO templateMessageDto) throws ValidationException;
	
	public int countTemplateMessageByName(String templateName) throws Exception;
	
	public TemplateMessageMapping getTemplateMessageMappingByCriteria(Long scope, UUID avmId, Long transactionId, Long levelApproval, ActionType actionType, Date transactionDate, Long priority);
	
	public TemplateMessage getTemplateMessageById(Long templateId) throws Exception;
	
	public Message templateMessageResolver(TemplateMessage templateMessage, Map<String, Object>  dataModel);
	
	public void insertNewMessage(NotificationMessage notificationMessage) throws Exception;
	
	public void insertMessageMapping(MessageMapping messageMapping);
	
	public List<NotificationMessage> getAllNotificationMessages() throws Exception;
	
	public NotificationMessage getNotificationMessageByHistorySeqNumber(Long historySeqNumber) throws Exception;
	
	public void sendNotificationMessage(UUID avmId, Object application, AVMApprovalHistory avmApprovalHistory, UUID submitterId, int actionType, TrxType trxType, Long companyId, Date transactionDate) throws Exception;
	
	public void sendMultipleNotificationMessage(UUID avmId, List<AVMApprovalHistory> approvalHistories, int actionType,TrxType trxType,UUID submitterId,Object application, Long companyId, Date transactionDate) throws Exception;
	
	public void updateMessageStatus(Long messageId, int messageStatus);
	
	public void updateMultipleMessageStatus(Set<NotificationMessageDTO> notificationMessages, int messageStatus);
	
	public void sendMultipleFYIMessage(UUID avmId, Object application, List<UUID> listReceiverMessage, UUID submitterId, int actionType, int trxType, Long companyId, int levelSequence, Date transactionDate, UUID avmTrxId) throws Exception;
	
	public List<NotificationMessageDTO> getNotificationMessageByPersonUUID(UUID personUUID) throws Exception;
	
	public void sendMultipleMessageNonTransactional(Long templateId, Object application, List<UUID> listReceiverMessage, UUID submitterId);
	
	public List<ParameterMessage> getParameterListByTransactionId(Long transactionId);
	
	public void deletePreviousNotification(UUID avmTrxId) throws FifException, Exception;
}
