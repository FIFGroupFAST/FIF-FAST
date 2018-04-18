package co.id.fifgroup.systemworkflow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.MessageMapping;
import co.id.fifgroup.systemworkflow.domain.MessageMappingExample;
import co.id.fifgroup.systemworkflow.domain.ParameterMessage;
import co.id.fifgroup.systemworkflow.domain.ParameterMessageExample;
import co.id.fifgroup.systemworkflow.domain.TemplateMessageMapping;
import co.id.fifgroup.systemworkflow.domain.TemplateMessageMappingExample;
import co.id.fifgroup.systemworkflow.dto.NotificationMessageDTO;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageDTO;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageMappingDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;
import co.id.fifgroup.systemworkflow.validation.TemplateMessageValidator;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.systemworkflow.dao.MessageMappingMapper;
import co.id.fifgroup.systemworkflow.dao.ParameterMessageMapper;
import co.id.fifgroup.systemworkflow.dao.TemplateMessageFinderMapper;
import co.id.fifgroup.systemworkflow.dao.TemplateMessageMappingMapper;

@Transactional
@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {

	private static final Logger logger = LoggerFactory
			.getLogger(TemplateMessageServiceImpl.class);

	@Autowired
	private TemplateMessageMappingMapper templateMessageMappingMapper;
	@Autowired
	private NotificationManager notificationManager;
	@Autowired
	private ParameterMessageMapper parameterMessageMapper;
	@Autowired
	private TemplateMessageFinderMapper templateMessageFinderMapper;
	@Autowired
	private MessageMappingMapper messageMappingMapper;
	@Autowired
	private AvmAdapterService avmAdapterServiceImpl;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private AVMApprovalManager avmApprovalManagerHcms;

	@Autowired
	private TemplateMessageValidator templateMessageValidator;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ParameterMessage> getParameterByTransactionId(
			Long transactionId, Short listData) {
		List<Long> listTrxId = new ArrayList<Long>();
		listTrxId.add(transactionId);
		listTrxId.add(0L);
		ParameterMessageExample parameterMessageExample = new ParameterMessageExample();
		parameterMessageExample.createCriteria().andTransactionIdIn(listTrxId)
				.andListDataLessThan(listData);
		return parameterMessageMapper.selectByExample(parameterMessageExample);
	}

	@Override
	public List<TemplateMessageDTO> getTemplateMessageByCriteria(
			String templateName, TrxType trxType, ActionType actionType)
			throws Exception {
		List<TemplateMessageDTO> listTemplateMessageDto = new ArrayList<TemplateMessageDTO>();
		List<TemplateMessageMappingDTO> listMessageMappings = new ArrayList<TemplateMessageMappingDTO>();
		Long transactionId = null;
		String action = null;
		if (trxType != null && !trxType.equals(TrxType.SELECT)) {
			transactionId = trxType.getCode();
		}
		if (actionType != null && !actionType.equals(ActionType.SELECT)) {
			action = actionType.name();
		}
		listMessageMappings = templateMessageFinderMapper
				.getTemplateMessageMapping(transactionId, action);
		for (TemplateMessageMappingDTO mapping : listMessageMappings) {
			TemplateMessageDTO templateMessageDto = new TemplateMessageDTO();
			TemplateMessage templateMessage = notificationManager
					.getTemplateByNameAndId(templateName, mapping
							.getTemplateId().longValue());
			if (templateMessage != null) {
				templateMessageDto.setTemplateMessage(templateMessage);
				templateMessageDto.setTemplateMessageMappingDTO(mapping);
				listTemplateMessageDto.add(templateMessageDto);
			}
		}

		return listTemplateMessageDto;
	}

	@Override
	public List<TemplateMessageDTO> getTemplateMessageByTransactionIdAndActionType(
			Long transactionId, ActionType actionType) throws Exception {
		List<TemplateMessageDTO> listTemplateMessageDto = new ArrayList<TemplateMessageDTO>();
		List<TemplateMessageMapping> listMessageMappings = new ArrayList<TemplateMessageMapping>();
		TemplateMessageMappingExample example = new TemplateMessageMappingExample();
		List<Long> transactions = new ArrayList<Long>();
		transactions.add(transactionId);
		transactions.add((long) 0);
		example.createCriteria().andTransactionIdIn(transactions)
				.andActionTypeEqualTo(actionType.toString());
		listMessageMappings = templateMessageMappingMapper
				.selectByExample(example);
		for (TemplateMessageMapping mapping : listMessageMappings) {
			TemplateMessageDTO templateMessageDto = new TemplateMessageDTO();
			TemplateMessage templateMessage = notificationManager
					.getTemplateById(mapping.getTemplateId().longValue());
			templateMessageDto.setTemplateMessage(templateMessage);
			TemplateMessageMappingDTO templateMessageMappingDTO = modelMapper
					.map(mapping, TemplateMessageMappingDTO.class);
			templateMessageDto
					.setTemplateMessageMappingDTO(templateMessageMappingDTO);
			listTemplateMessageDto.add(templateMessageDto);
		}
		Collections.sort(listTemplateMessageDto);
		return listTemplateMessageDto;
	}

	@Override
	public TemplateMessageDTO getTemplateMessageDtoById(Long templateMappingId)
			throws Exception {
		TemplateMessageDTO templateMessageDto = new TemplateMessageDTO();
		TemplateMessageMapping templateMessageMapping = templateMessageMappingMapper
				.selectByPrimaryKey(templateMappingId);
		TemplateMessage templateMessage = notificationManager
				.getTemplateById(templateMessageMapping.getTemplateId()
						.longValue());
		templateMessageDto.setTemplateMessage(templateMessage);
		TemplateMessageMappingDTO templateMessageMappingDTO = modelMapper.map(
				templateMessageMapping, TemplateMessageMappingDTO.class);
		templateMessageDto
				.setTemplateMessageMappingDTO(templateMessageMappingDTO);
		return templateMessageDto;
	}

	@Override
	public void saveTemplateMessageDto(TemplateMessageDTO templateMessageDto)
			throws ValidationException {
		templateMessageValidator.validate(templateMessageDto);

		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date newDate = new Date();

		try {
			if (templateMessageDto.getTemplateMessage().getTemplateId() == null) {
				templateMessageDto.getTemplateMessage().setCreatedBy(
						createdBy.longValue());
				templateMessageDto.getTemplateMessage().setCreatedDate(newDate);
				templateMessageDto.getTemplateMessage().setLastUpdatedBy(
						createdBy.longValue());
				templateMessageDto.getTemplateMessage().setLastUpdatedDate(
						newDate);
				notificationManager.insertNewTemplate(templateMessageDto
						.getTemplateMessage());
				templateMessageDto.getTemplateMessageMappingDTO()
						.setTemplateId(
								templateMessageDto.getTemplateMessage()
										.getTemplateId());
				templateMessageDto.getTemplateMessageMappingDTO().setCreatedBy(
						createdBy);
				templateMessageDto.getTemplateMessageMappingDTO()
						.setCreatedDate(newDate);
				templateMessageDto.getTemplateMessageMappingDTO()
						.setLastUpdatedBy(createdBy);
				templateMessageDto.getTemplateMessageMappingDTO()
						.setLastUpdatedDate(newDate);
				templateMessageMappingMapper.insert(templateMessageDto
						.getTemplateMessageMappingDTO());
			} else {
				templateMessageDto.getTemplateMessage().setLastUpdatedBy(
						createdBy.longValue());
				templateMessageDto.getTemplateMessage().setLastUpdatedDate(
						newDate);
				notificationManager.updateTemplate(templateMessageDto
						.getTemplateMessage());
				templateMessageDto.getTemplateMessageMappingDTO()
						.setLastUpdatedBy(createdBy);
				templateMessageDto.getTemplateMessageMappingDTO()
						.setLastUpdatedDate(newDate);
				templateMessageMappingMapper
						.updateByPrimaryKey(templateMessageDto
								.getTemplateMessageMappingDTO());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public int countTemplateMessageByName(String templateName) throws Exception {
		return notificationManager.countTemplateMessage(templateName);
	}

	@Override
	public TemplateMessageMapping getTemplateMessageMappingByCriteria(
			Long scope, UUID avmId, Long transactionId, Long levelApproval,
			ActionType actionType, Date transactionDate, Long priority) {
		TemplateMessageMapping templateMessageMapping = templateMessageFinderMapper
				.getTemplateMessageByCriteria(scope, avmId, transactionId,
						levelApproval, actionType.toString(), transactionDate,
						priority);
		if (templateMessageMapping == null) {
			templateMessageMapping = templateMessageFinderMapper
					.getTemplateMessageByCriteria(ScopeType.COMMON.getValue(),
							avmId, transactionId, levelApproval,
							actionType.toString(), transactionDate, priority);
		}
		return templateMessageMapping;
	}

	@Override
	public TemplateMessage getTemplateMessageById(Long templateId)
			throws Exception {
		return notificationManager.getTemplateById(templateId);
	}

	@Override
	public Message templateMessageResolver(TemplateMessage templateMessage,
			Map<String, Object> dataModel) {
		return notificationManager.templateMessageResolver(templateMessage,
				dataModel);
	}

	@Override
	public void insertNewMessage(NotificationMessage notificationMessage)
			throws Exception {
		notificationManager.insertNewMessage(notificationMessage);
	}

	@Override
	public void insertMessageMapping(MessageMapping messageMapping) {
		messageMappingMapper.insert(messageMapping);
	}

	@Override
	public List<NotificationMessage> getAllNotificationMessages()
			throws Exception {
		return notificationManager.getAllNotificationMessage();
	}

	@Override
	public NotificationMessage getNotificationMessageByHistorySeqNumber(
			Long historySeqNumber) throws Exception {
		MessageMappingExample example = new MessageMappingExample();
		example.createCriteria().andApprovalHistorySeqEqualTo(historySeqNumber);
		List<MessageMapping> messageMappings = messageMappingMapper
				.selectByExample(example);
		MessageMapping mapping = messageMappings.size() > 0 ? messageMappings
				.get(0) : null;
		return mapping != null ? notificationManager
				.getNotificationMessageByMessageId(mapping.getMessageId())
				: null;
	}

	@Override
	public void sendNotificationMessage(UUID avmId, Object application,
			AVMApprovalHistory avmApprovalHistory, UUID submitterId,
			int actionType, TrxType trxType, Long companyId,
			Date transactionDate) throws Exception {
		ActionType action = ActionType.fromCode(actionType);
		ApprovalModelMappingDetail approvalModelMappingDetail = avmAdapterServiceImpl
				.getApplicableApprovalModelMapping(trxType.getCode(),
						companyId, application, transactionDate);
		TemplateMessageMapping templateMessageMapping = getTemplateMessageMappingByCriteria(
				companyId, avmId, trxType.getCode(),
				(long) avmApprovalHistory.getLevelSequence(), action,
				DateUtil.truncate(transactionDate),
				approvalModelMappingDetail.getPriority());

		if (templateMessageMapping == null) {
			templateMessageMapping = getTemplateMessageMappingByCriteria(
					companyId, avmId, trxType.getCode(), (long) 0, action,
					DateUtil.truncate(transactionDate),
					approvalModelMappingDetail.getPriority());
		}

		if (templateMessageMapping != null) {
			TemplateMessage templateMessage = getTemplateMessageById(templateMessageMapping
					.getTemplateId());
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setReceivedTime(new Date());
			notificationMessage.setToId(submitterId);
			notificationMessage.setFromId(avmApprovalHistory.getApproverId());
			Map<String, Object> map = new HashMap<String, Object>();
			String approverName = avmAdapterServiceImpl.getName(
					avmApprovalHistory.getApproverId(), companyId);
			map.put("approverName", approverName);
			String requestorName = avmAdapterServiceImpl.getName(submitterId,
					companyId);
			map.put("requestorName", requestorName);

			// Rachmad Agus D. 20140522 Ticket 14040808565710 CR HCMS Transfer
			if (trxType != null && trxType.equals(TrxType.TRF_SINGLE)
					&& action != null && action.equals(ActionType.APPROVE)) {
				application.getClass().getMethod("setSrcSalary", String.class)
						.invoke(application, (Object) null);
				application.getClass().getMethod("setDstSalary", String.class)
						.invoke(application, (Object) null);
			}

			map.put("object", application);
			List<ParameterMessage> parameterMessages = getParameterListByTransactionId(trxType
					.getCode());
			String contentMessage = templateMessage.getTemplateContent();

			if (parameterMessages.size() > 0) {
				for (ParameterMessage parameter : parameterMessages) {
					contentMessage = contentMessage.replaceAll(Pattern
							.quote(parameter.getAliasName()), Matcher
							.quoteReplacement(parameter.getParameterKey()));
				}

				templateMessage.setTemplateContent(contentMessage);
			}

			Message message = templateMessageResolver(templateMessage, map);
			notificationMessage.setSubject(message.getSubject());
			notificationMessage.setContentMessage(message.getContent());
			notificationMessage.setUrlDetail(templateMessageMapping
					.getUrlDetail() != null ? templateMessageMapping
					.getUrlDetail()
					+ "?avmTrxId="
					+ avmApprovalHistory.getAvmTrxId() : null);
			notificationMessage.setMessageType(MessageType.FYI_MESSAGE
					.getCode());
			insertNewMessage(notificationMessage);
			
			// Bug 8792
			// Bug 9609
			if ((trxType != null && (trxType.equals(TrxType.HOU_CONTRACT_REQUEST) 
							|| trxType.equals(TrxType.HOU_PLACEMENT_REQUEST))
							&& action != null && action.equals(ActionType.REJECT))
					|| (trxType != null && (trxType.equals(TrxType.CWK_TRANSFER_REQUEST) 
							|| trxType.equals(TrxType.CWK_TRANSFER_WITHIN_GROUP))
							&& (action != null && ((action.equals(ActionType.REJECT))
							||action.equals(ActionType.APPROVE))))) {
				String methodGetUUID = "getEmployeeUUID";
				String methodGetEmpNumber = "getEmployeeNumber";
				if (trxType.equals(TrxType.CWK_TRANSFER_REQUEST) 
						|| trxType.equals(TrxType.CWK_TRANSFER_WITHIN_GROUP)){
					methodGetUUID = "getObjectEmployeeUUID";
					methodGetEmpNumber = "getCwkNumber";
				}
				UUID employeeId = (UUID) application.getClass()
						.getMethod(methodGetUUID).invoke(application, null);
				if (!submitterId.equals(employeeId)) {
					String requestorEmployeeNumber = (String) application
							.getClass().getMethod(methodGetEmpNumber)
							.invoke(application, null);
					String requestorFullName = (String) application.getClass()
							.getMethod("getFullName").invoke(application, null);
					String receiver = requestorEmployeeNumber + "-"
							+ requestorFullName;
					map.put("requestorName", receiver);
					message = templateMessageResolver(templateMessage, map);
					notificationMessage.setContentMessage(message.getContent());
					notificationMessage.setToId(employeeId);
					insertNewMessage(notificationMessage);
				}
			}
		}

		// MessageMapping messageMapping = new MessageMapping();
		// messageMapping.setMessageId(notificationMessage.getMessageId());
		// messageMapping.setApprovalHistorySeq((long)
		// avmApprovalHistory.getSequenceNumber());
		// messageMapping.setActionType(action.toString());
		// insertMessageMapping(messageMapping);
	}

	@SuppressWarnings({ "null", "null" })
	@Override
	public void sendMultipleNotificationMessage(UUID avmId,
			List<AVMApprovalHistory> approvalHistories, int actionType,
			TrxType trxType, UUID submitterId, Object application,
			Long companyId, Date transactionDate) throws Exception {
		ActionType action = ActionType.fromCode(actionType);
		ApprovalModelMappingDetail approvalModelMappingDetail = avmAdapterServiceImpl
				.getApplicableApprovalModelMapping(trxType.getCode(),
						companyId, application,
						DateUtil.truncate(transactionDate));
		TemplateMessageMapping templateMessageMapping = getTemplateMessageMappingByCriteria(
				companyId, avmId, trxType.getCode(), (long) approvalHistories
						.get(0).getLevelSequence(), action,
				DateUtil.truncate(transactionDate),
				approvalModelMappingDetail.getPriority());
		if (templateMessageMapping == null) {
			templateMessageMapping = getTemplateMessageMappingByCriteria(
					companyId, avmId, trxType.getCode(), (long) 0, action,
					transactionDate, approvalModelMappingDetail.getPriority());
		}
		if (templateMessageMapping != null) {
			TemplateMessage templateMessage = getTemplateMessageById(templateMessageMapping
					.getTemplateId());
			for (AVMApprovalHistory avmApprovalHistory : approvalHistories) {
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setReceivedTime(new Date());
				notificationMessage.setToId(avmApprovalHistory.getApproverId());
				notificationMessage.setFromId(submitterId);
				Map<String, Object> map = new HashMap<String, Object>();
				String approverName = avmAdapterServiceImpl.getName(
						avmApprovalHistory.getApproverId(), companyId);
				map.put("approverName", approverName);
				String requestorName = avmAdapterServiceImpl.getName(
						submitterId, companyId);
				map.put("requestorName", requestorName);
				map.put("object", application);
				List<ParameterMessage> parameterMessages = getParameterListByTransactionId(trxType
						.getCode());
				String contentMessage = templateMessage.getTemplateContent();
				if (parameterMessages.size() > 0) {
					for (ParameterMessage parameter : parameterMessages) {
						contentMessage = contentMessage.replaceAll(Pattern
								.quote(parameter.getAliasName()), Matcher
								.quoteReplacement(parameter.getParameterKey()));
					}
					templateMessage.setTemplateContent(contentMessage);
				}
				Message message = templateMessageResolver(templateMessage, map);
				notificationMessage.setSubject(message.getSubject());
				notificationMessage.setContentMessage(message.getContent());
				notificationMessage.setUrlDetail(templateMessageMapping
						.getUrlDetail());
				notificationMessage.setMessageType(MessageType.APPROVAL_MESSAGE
						.getCode());
				insertNewMessage(notificationMessage);

				MessageMapping messageMapping = new MessageMapping();
				messageMapping.setMessageId(notificationMessage.getMessageId());
				messageMapping.setApprovalHistorySeq((long) avmApprovalHistory
						.getSequenceNumber());
				messageMapping.setActionType(action.toString());
				insertMessageMapping(messageMapping);
			}

		}
		else
		{
						templateMessageMapping = getTemplateMessageMappingByCriteria(
					companyId, avmId, trxType.getCode(), 
					(long) approvalHistories
							.get(0).getLevelSequence(), action,
					DateUtil.truncate(DateUtil.getToday()),
					approvalModelMappingDetail.getPriority());
			
			if (templateMessageMapping !=null)
			{
			TemplateMessage templateMessage = getTemplateMessageById(templateMessageMapping
					.getTemplateId());
			        for (AVMApprovalHistory avmApprovalHistory : approvalHistories) {
					NotificationMessage notificationMessage = new NotificationMessage();
					notificationMessage.setReceivedTime(new Date());
					notificationMessage.setToId(avmApprovalHistory.getApproverId());
					notificationMessage.setFromId(submitterId);
					Map<String, Object> map = new HashMap<String, Object>();
					String approverName = avmAdapterServiceImpl.getName(
							avmApprovalHistory.getApproverId(), companyId);
					map.put("approverName", approverName);
					String requestorName = avmAdapterServiceImpl.getName(
							submitterId, companyId);
					map.put("requestorName", requestorName);
					map.put("object", application);
					List<ParameterMessage> parameterMessages = getParameterListByTransactionId(trxType
							.getCode());
					String contentMessage = templateMessage.getTemplateContent();
					if (parameterMessages.size() > 0) {
						for (ParameterMessage parameter : parameterMessages) {
							contentMessage = contentMessage.replaceAll(Pattern
									.quote(parameter.getAliasName()), Matcher
									.quoteReplacement(parameter.getParameterKey()));
						}
						templateMessage.setTemplateContent(contentMessage);
					}
					Message message = templateMessageResolver(templateMessage, map);
					notificationMessage.setSubject(message.getSubject());
					notificationMessage.setContentMessage(message.getContent());
					notificationMessage.setUrlDetail(templateMessageMapping
							.getUrlDetail());
					notificationMessage.setMessageType(MessageType.APPROVAL_MESSAGE
							.getCode());
					insertNewMessage(notificationMessage);
	
					MessageMapping messageMapping = new MessageMapping();
					messageMapping.setMessageId(notificationMessage.getMessageId());
					messageMapping.setApprovalHistorySeq((long) avmApprovalHistory
							.getSequenceNumber());
					messageMapping.setActionType(action.toString());
					insertMessageMapping(messageMapping);
				}
			}
		}
	}

	@Override
	public void updateMessageStatus(Long messageId, int messageStatus) {
		try {
			notificationManager.updateMessageStatus(messageId, messageStatus);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void updateMultipleMessageStatus(
			Set<NotificationMessageDTO> notificationMessages, int messageStatus) {
		Iterator<NotificationMessageDTO> iterator = new LinkedHashSet<NotificationMessageDTO>(
				notificationMessages).iterator();
		while (iterator.hasNext()) {
			updateMessageStatus(iterator.next().getMessageId(), messageStatus);
		}
	}

	@Override
	public void sendMultipleFYIMessage(UUID avmId, Object application,
			List<UUID> listReceiverMessage, UUID submitterId, int actionType,
			int trxType, Long companyId, int levelSequence,
			Date transactionDate, UUID avmTrxId) throws Exception {
		ActionType action = ActionType.fromCode(actionType);
		ApprovalModelMappingDetail approvalModelMappingDetail = avmAdapterServiceImpl
				.getApplicableApprovalModelMapping((long) trxType, companyId,
						application, transactionDate);
		TemplateMessageMapping templateMessageMapping = getTemplateMessageMappingByCriteria(
				companyId, avmId, (long) trxType, (long) levelSequence, action,
				transactionDate, approvalModelMappingDetail.getPriority());
		if (templateMessageMapping == null) {
			templateMessageMapping = getTemplateMessageMappingByCriteria(
					companyId, avmId, (long) trxType, (long) 0, action,
					transactionDate, approvalModelMappingDetail.getPriority());
		}
		if (templateMessageMapping != null) {
			TemplateMessage templateMessage = getTemplateMessageById(templateMessageMapping
					.getTemplateId());
			for (UUID toUUID : listReceiverMessage) {
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setReceivedTime(new Date());
				notificationMessage.setToId(toUUID);
				notificationMessage.setFromId(submitterId);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("object", application);
				String approverName = avmAdapterServiceImpl.getName(
						submitterId, companyId);
				map.put("approverName", approverName);
				String requestorName = avmAdapterServiceImpl.getName(toUUID,
						companyId);
				map.put("requestorName", requestorName);

				List<ParameterMessage> parameterMessages = getParameterListByTransactionId((long) trxType);
				String contentMessage = templateMessage.getTemplateContent();
				if (parameterMessages.size() > 0) {
					for (ParameterMessage parameter : parameterMessages) {
						contentMessage = contentMessage.replaceAll(Pattern
								.quote(parameter.getAliasName()), Matcher
								.quoteReplacement(parameter.getParameterKey()));
					}
					templateMessage.setTemplateContent(contentMessage);
				}

				Message message = templateMessageResolver(templateMessage, map);
				notificationMessage.setSubject(message.getSubject());
				notificationMessage.setContentMessage(message.getContent());
				notificationMessage.setUrlDetail(templateMessageMapping
						.getUrlDetail() != null ? templateMessageMapping
						.getUrlDetail() + "?avmTrxId=" + avmTrxId : null);
				notificationMessage.setMessageType(MessageType.FYI_MESSAGE
						.getCode());
				insertNewMessage(notificationMessage);
			}
		}
	}

	@Override
	public List<NotificationMessageDTO> getNotificationMessageByPersonUUID(
			UUID personUUID) throws Exception {
		List<NotificationMessage> notificationMessages = notificationManager
				.getMessageByToId(personUUID);
		List<NotificationMessageDTO> notificationMessageDtos = new ArrayList<NotificationMessageDTO>();
		for (NotificationMessage notificationMessage : notificationMessages) {
			NotificationMessageDTO notificationMessageDTO = modelMapper.map(
					notificationMessage, NotificationMessageDTO.class);
			notificationMessageDTO.setName(avmAdapterServiceImpl.getName(
					notificationMessageDTO.getFromId(), null));
			notificationMessageDtos.add(notificationMessageDTO);
		}
		return notificationMessageDtos;
	}

	@Override
	public void sendMultipleMessageNonTransactional(Long templateId,
			Object application, List<UUID> listReceiverMessage, UUID submitterId) {
		try {
			TemplateMessage templateMessage = notificationManager
					.getTemplateById(templateId);
			for (UUID toUUID : listReceiverMessage) {
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setReceivedTime(new Date());
				notificationMessage.setToId(toUUID);
				notificationMessage.setFromId(submitterId);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("object", application);
				String approverName = avmAdapterServiceImpl.getName(
						submitterId, null);
				map.put("approverName", approverName);
				String requestorName = avmAdapterServiceImpl.getName(toUUID,
						null);
				map.put("requestorName", requestorName);

				Message message = templateMessageResolver(templateMessage, map);
				notificationMessage.setSubject(message.getSubject());
				notificationMessage.setContentMessage(message.getContent());
				notificationMessage.setUrlDetail(null);
				insertNewMessage(notificationMessage);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public List<ParameterMessage> getParameterListByTransactionId(
			Long transactionId) {
		ParameterMessageExample example = new ParameterMessageExample();
		example.createCriteria().andTransactionIdEqualTo(transactionId)
				.andListDataEqualTo((short) 1);
		return parameterMessageMapper.selectByExample(example);
	}

	@Override
	public void deletePreviousNotification(UUID avmTrxId) throws FifException, Exception {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		approvalHistories = avmApprovalManagerHcms.getApproverReadyByAVMTrxId(avmTrxId);
		for (AVMApprovalHistory avmApprovalHistory : approvalHistories) {
			MessageMappingExample example = new MessageMappingExample();
			example.createCriteria().andApprovalHistorySeqEqualTo((long) avmApprovalHistory.getSequenceNumber());
			List<MessageMapping> messageMappings = messageMappingMapper.selectByExample(example);
			for (MessageMapping messageMapping : messageMappings) {
				notificationManager.deleteMessage(messageMapping.getMessageId());
				messageMappingMapper.deleteByPrimaryKey(messageMapping.getMessageMappingId());
			}
		}
		
	}
}
