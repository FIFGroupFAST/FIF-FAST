package co.id.fifgroup.ssoa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.notification.manager.SendEmailManager;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.NotificationSOInputService;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ParameterMessage;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;


@Transactional
@Service("notificationSOInputService")
public class NotificationSOInputServiceImpl implements NotificationSOInputService {

	

	@Autowired
	private StockOpnameMapper stockOpnameMapper;
	
	@Autowired
	private GlobalSettingFinderMapper globalsettingMapper;
	
	@Autowired
	private BranchMapper branchMapper;
	
	//@Autowired
	//private AvmAdapterService avmAdapterServiceImpl;
	public static AvmAdapterService getAvmAdapterService() {
		return (AvmAdapterService) ApplicationContextUtil.getApplicationContext().getBean("avmAdapterServiceImpl");
	}
	
	//@Autowired
	//private TemplateMessageService templateMessageServiceImpl;
	public static TemplateMessageService getTemplateMessageService() {
		return (TemplateMessageService) ApplicationContextUtil.getApplicationContext().getBean("templateMessageServiceImpl");
	}
	
	@Autowired
	private NotificationManager notificationManager;
	
	@Autowired
	private SendEmailManager sendEmailManager;

	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
	
	

	@Override
	@Transactional(readOnly = true)
	public String sendMessage() {
		List<StockOpnameDTO> listSO = stockOpnameMapper.getSOForNotification();
		for(int i=0;i<listSO.size();i++){
			StockOpnameDTO stockOpnameDTO = (StockOpnameDTO)listSO.get(i);
			List<PersonAssignmentDTO> personApproverBranchHead = new ArrayList<PersonAssignmentDTO>();
			personApproverBranchHead = getPersonService().selectPersonByAssignment(null, null,
					stockOpnameDTO.getBranchId(), null, null, stockOpnameDTO.getCompanyId(), "19");
			
			for(int x=0;x<personApproverBranchHead.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverBranchHead.get(x);
				try {
					sendNotification(stockOpnameMapper.getNotificationMessageByName(SSOAConstants.templateNameForNotifSOInput),UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM) , personAssignmentDTO.getPersonUUID(), stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.NOTIFICATION_SO_INPUT,stockOpnameDTO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			List<PersonAssignmentDTO> personApproverAreaHead = new ArrayList<PersonAssignmentDTO>();
			personApproverBranchHead = getPersonService().selectPersonByAssignment(null, null,
					stockOpnameDTO.getBranchId(), null, null, stockOpnameDTO.getCompanyId(), "16");
			for(int x=0;x<personApproverAreaHead.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverAreaHead.get(x);
				try {
					sendNotification(stockOpnameMapper.getNotificationMessageByName(SSOAConstants.templateNameForNotifSOInput),UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM) , personAssignmentDTO.getPersonUUID(), stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.NOTIFICATION_SO_INPUT,stockOpnameDTO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			List<GlobalSettingDTO> dataPICGS = globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(SSOAConstants.picGSPersonId, null); 
			String picGSPersonId = null;
			if(dataPICGS!=null){
				picGSPersonId = dataPICGS.get(0).getGlobalSetting().getValue().toString();
			}
			
			//PersonDTO personDTO = getPersonService().getPersonById(new Long(picGSPersonId), new Date());
			StockOpnameDTO personDTO = stockOpnameMapper.getPersonByPersonId(new Long(picGSPersonId));
			if(personDTO!=null){
				try {
					sendNotification(stockOpnameMapper.getNotificationMessageByName(SSOAConstants.templateNameForNotifSOInput),UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM) , personDTO.getPersonUUID(), stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.NOTIFICATION_SO_INPUT,stockOpnameDTO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return null;
	}
	
	public void insertNewMessage(NotificationMessage notificationMessage)
			throws Exception {
		notificationManager.insertNewMessage(notificationMessage);
	}
	
	public void sendNotification(Long templateId, UUID submitterId,UUID approverId, Long companyId,TrxType trxType,StockOpnameDTO stockOpnameDTO) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		TemplateMessage templateMessage = getTemplateMessageService()
				.getTemplateMessageById(templateId);
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setReceivedTime(new Date());
		notificationMessage.setToId(approverId);
		notificationMessage.setFromId(submitterId);
		Map<String, Object> map = new HashMap<String, Object>();
		String approverName = getAvmAdapterService().getName(approverId, companyId);
		map.put("approverName", approverName);
		String requestorName = getAvmAdapterService().getName(submitterId, companyId);
		//String employeeNumber = personFinder.selectPersonByPersonUUID(submitterId, null).getEmployeeNumber();
		map.put("requestorName", requestorName);
		Branch branch = branchMapper.selectByPrimaryKey(stockOpnameDTO.getBranchId(), stockOpnameDTO.getCompanyId());
		map.put("branchName", branch.getBranchName());
		map.put("periode", sdf.format(stockOpnameDTO.getStartDate())+" - "+sdf.format(stockOpnameDTO.getEndDate()));
		List<ParameterMessage> parameterMessages = getTemplateMessageService()
				.getParameterListByTransactionId(trxType.getCode());
		String contentMessage = templateMessage.getTemplateContent();

		if (parameterMessages.size() > 0) {
			for (ParameterMessage parameter : parameterMessages) {
				contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
						Matcher.quoteReplacement(parameter.getParameterKey()));
			}

			templateMessage.setTemplateContent(contentMessage);
		}

		Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
		notificationMessage.setSubject(message.getSubject());
		notificationMessage.setContentMessage(message.getContent());
		/*notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail() != null
				? templateMessageMapping.getUrlDetail() + "?avmTrxId=" + avmApprovalHistory.getAvmTrxId() : null);*/
		notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
		insertNewMessage(notificationMessage);
		PersonAssignmentDTO personAssignmentDTO =getPersonService().getAssignmentByPersonUUID(approverId);
		sendEmailManager.sendEmail(notificationMessage, personAssignmentDTO.getEmployeeNumber());
		
}

}
