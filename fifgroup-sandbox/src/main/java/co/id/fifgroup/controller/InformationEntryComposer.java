package co.id.fifgroup.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.domain.Information;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.service.InformationService;
import co.id.fifgroup.systemworkflow.constants.TrxStatus;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

@VariableResolver(DelegatingVariableResolver.class)
public class InformationEntryComposer extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6348425248886749381L;

	@Wire
	private Window winSBInformationEntry;
	@Wire
	private Caption cptInfoEntry;
	@Wire
	private Hbox hboxRequestor;
	@Wire
	private Textbox txtInformation, txtSecretInfo;
	@Wire
	private Datebox dtbRequestDate;
	@Wire
	private Button btnSubmit;
	@Wire
	private Row rowSecretInfo;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient InformationService informationServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient NotificationManager notificationManager;
	
	private CommonPopupDoubleBandbox bnbRequestor;
	private Information information = null;
	boolean isDetail = false;
	private boolean isManagerandSuperUser = false;
	private boolean isHCSelfService = false;
	private boolean isSuperUser = false;
	private Date effectiveOnDate = DateUtils.truncate(new Date(), Calendar.DATE);
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		information = (Information) arg.get("data");
		if (information != null)
			isDetail = true;
		//----ROLE---------------------------------------------------------
		if (securityServiceImpl.getSecurityProfile().getSecurity() != null) {			
			List<String> fieldPermissions = (List<String>) securityServiceImpl
				.getSecurityProfile().getSecurity().getFieldPermissions();
			for (String permission : fieldPermissions) {
				if (permission.equalsIgnoreCase("sam.showSecretInfo"))
					isManagerandSuperUser = true;
				if (permission.equalsIgnoreCase("sam.showSecretInfoHC"))
					isHCSelfService = true;
				if (permission.equalsIgnoreCase("sam.showSecretInfoSuper"))
					isSuperUser = true;
			}
		}
		//-----------------------------------------------------------------
		init();
		initRequestor();
	}
	
	public void init() {
		bnbRequestor = new CommonPopupDoubleBandbox();
		dtbRequestDate.setFormat(FormatDateUI.getDateFormat());
		if (isDetail) {
			btnSubmit.setVisible(false);
			bnbRequestor.setValue(information.getRequestorNumber()+" - "+information.getRequestorName());
			dtbRequestDate.setValue(information.getRequestDate());
			txtInformation.setValue(information.getInformationData());
			txtSecretInfo.setValue(information.getSecretInfo());
			enableOrDisable(true);
			if (isHCSelfService) {
				rowSecretInfo.setVisible(false);
			}
		}
	}
	
	public void initRequestor() {
		if (isManagerandSuperUser == false && isHCSelfService == false && isSuperUser == false) {
			bnbRequestor.setDisabled(true);
			bnbRequestor.setValue(securityServiceImpl.getSecurityProfile().getEmployeeNumber()
					+" - "+securityServiceImpl.getSecurityProfile().getFullName());
		}
		bnbRequestor.setConcatValueDescription(true);
		bnbRequestor.setId("bnbRequestor");
		bnbRequestor.setTitle(Labels.getLabel("common.listOfEmployee"));
		bnbRequestor.setSearchText1(Labels.getLabel("common.lovEmployeeNumber"));
		bnbRequestor.setSearchText2(Labels.getLabel("common.lovEmployeeName"));
		bnbRequestor.setHeaderLabel1(Labels.getLabel("common.lovEmployeeNumber"));
		bnbRequestor.setHeaderLabel2(Labels.getLabel("common.lovEmployeeName"));
		bnbRequestor.setWidth("300px");
		bnbRequestor.setParent(hboxRequestor);
		bnbRequestor.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<PersonDTO>() {

			@Override
			public List<PersonDTO> findBySearchCriteria(String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
				PersonCriteriaDTO criteria = new PersonCriteriaDTO();
				criteria.setInOrganizationId(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations());
				criteria.setNotInOrganizationId(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations());
				criteria.setGradeExclusions(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getGradeExclusions());
				criteria.setEmployeeNumber(searchCriteria1);
				criteria.setFullName(searchCriteria2);
				criteria.setEffectiveOnDate(DateUtils.truncate(new Date(), Calendar.DATE));
				if(isManagerandSuperUser)
					criteria.setSupervisorId(securityServiceImpl.getSecurityProfile().getPersonId());
				if (isHCSelfService)
					criteria.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());
				criteria.setPeopleType(PeopleType.EMPLOYEE.toString());
				return personService.getPersonByCompanyTemp(criteria, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), offset, limit);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1, String searchCriteria2) {
				PersonCriteriaDTO criteria = new PersonCriteriaDTO();
				criteria.setInOrganizationId(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations());
				criteria.setNotInOrganizationId(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations());
				criteria.setGradeExclusions(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getGradeExclusions());
				criteria.setEmployeeNumber(searchCriteria1);
				criteria.setFullName(searchCriteria2);
				criteria.setEffectiveOnDate(DateUtils.truncate(new Date(), Calendar.DATE));
				if(isManagerandSuperUser)
					criteria.setSupervisorId(securityServiceImpl.getSecurityProfile().getPersonId());
				if (isHCSelfService)
					criteria.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());
				criteria.setPeopleType(PeopleType.EMPLOYEE.toString());
				return personService.countPersonByCompanyTemp(criteria, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			}

			@Override
			public void mapper(KeyValue keyValue, PersonDTO data) {
				keyValue.setKey(data.getPersonId());
				keyValue.setValue(data.getEmployeeNumber());
				keyValue.setDescription(data.getFullName());
			}
			
		});
		bnbRequestor.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				KeyValue keyval = (KeyValue) arg0.getData();
				try{
					bnbRequestor.setText(keyval.getValue()+" - "+keyval.getDescription());
				}catch (Exception e) {
					e.printStackTrace();
					bnbRequestor.setText(null);
				}
			}
		});
	}
	
	@Listen("onClick = button#btnSubmit")
	public void onClickSubmit() {
		if (isDetail) {
			backToInquiryPage();
		} else {
			if (validate()) {			
				Messagebox.show("Would you like to submit this setup?", "Confirm", Messagebox.YES | Messagebox.NO,
						Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						switch (((Integer) event.getData()).intValue()) {
						case Messagebox.YES:
							doSave();
							Messagebox.show(Labels.getLabel("common.dataHasBeenSubmitted"), "Information", 
									Messagebox.YES, Messagebox.INFORMATION);
							backToInquiryPage();
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
			}
		}
	}
	
	@Listen("onClick = button#btnBack")
	public void onClickBack() {
		backToInquiryPage();
	}
	
	public void enableOrDisable(boolean component) {
		bnbRequestor.setDisabled(component);
		dtbRequestDate.setDisabled(component);
		txtInformation.setDisabled(component);
		txtSecretInfo.setDisabled(component);
	}
	
	public boolean validate() {
		boolean isValidate = true;
		if (bnbRequestor.getValue().equalsIgnoreCase("")) {
			isValidate = false;
			bnbRequestor.setErrorMessage("Please Choose Requestor");
		}
		if (dtbRequestDate.getValue() == null) {
			isValidate = false;
			dtbRequestDate.setErrorMessage("Please Choose Request Date");
		}
		return isValidate;
	}
	
	public void doSave() {
		try {
			Information information = new Information();
			String[] parts = bnbRequestor.getValue().split(" - ");
			String requestorNumber = parts[0];
			String requestorName = parts[1];
			information.setRequestorNumber(Integer.parseInt(requestorNumber));
			information.setRequestorName(requestorName);
			information.setRequestDate(dtbRequestDate.getValue());
			if (txtInformation.getValue() != null)
				information.setInformationData(txtInformation.getValue());
			else information.setInformationData(" ");
			if (txtSecretInfo.getValue() != null)
				information.setSecretInfo(txtSecretInfo.getValue());
			else information.setSecretInfo(" ");
			information.setOrganizationIDEmployee(securityServiceImpl.getSecurityProfile().getOrganizationId());
			information.setCreatedDate(new Date());
			information.setCreatedBy(securityServiceImpl.getSecurityProfile().getEmployeeNumber());
			information.setLastUpdatedDate(new Date());
			information.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getEmployeeNumber());
			informationServiceImpl.insertInformation(information);
		
			//Approval
			PersonDTO personDTO = personService.getActiveEmployee(requestorNumber, effectiveOnDate);
			UUID uuid = UUID.randomUUID();
			PersonDTO dto = personService.getPersonById(personDTO.getPersonId(), effectiveOnDate);
			information.setObjectEmployeeUUID(dto.getPersonUUID());
			information.setApprovalStatus(TrxStatus.IN_PROGRESS.toString());//clean first
			information.setAvmTrxId(null);
			//PersonDTO personSender = personService.getActiveEmployee(information.getCreatedBy(), effectiveOnDate);
			String SPV = informationServiceImpl.getSupervisor(Integer.toString(information.getRequestorNumber()));
			String[] SPVparts = SPV.split("-");
			String SPVNumber = SPVparts[0];
			String SPVName = SPVparts[1];
			PersonDTO personSender = personService.getActiveEmployee(
					SPVNumber, effectiveOnDate);
			information.setSenderName(personSender.getFullName());
			avmAdapterServiceImpl.submitAvmTransaction(uuid, securityServiceImpl.getSecurityProfile().getPersonUUID(),
					information, TrxType.SB_INFORMATION, personDTO.getCompanyId());
			information.setAvmTrxId(uuid);
			
			informationServiceImpl.updateInformation(information);
			
			sendNotification(information);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void backToInquiryPage() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", arg.get("param"));
		Executions.createComponents("~./hcms/sandbox/sb_information_inquiry.zul", 
				winSBInformationEntry.getParent(), params);
		winSBInformationEntry.detach();
	}
	
	public void sendNotification(Information information) throws Exception {
		PersonDTO personSender = personService.getActiveEmployee(information.getCreatedBy(), effectiveOnDate);
		personSender = personService.getPersonById(personSender.getPersonId(), effectiveOnDate);
		//supervisor 1
		PersonDTO personDestination = personService.getActiveEmployee(
				informationServiceImpl.getSupervisor(Integer.toString(information.getRequestorNumber())), effectiveOnDate);
		personDestination = personService.getPersonById(personDestination.getPersonId(), effectiveOnDate);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("object", information);
		map.put("senderName", personSender.getFullName());
		map.put("destinationName", personDestination.getFullName());
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTemplateSubject("Notification Information");
		templateMessage.setTemplateContent("<p>Dear ${destinationName}, </p>"
				+ "<p>Hereby I have submit your information. &nbsp; You can see the data below.</p>"
				+ "<p>Regards,</p>"
				+ "${senderName}"
				+ "&nbsp;"
				+ "<table border='1'>"
				+ "<tbody>"
				+ "<tr>"
				+ "<td>Employee Name</td>"
				+ "<td>${object.requestorName!}</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Information</td>"
				+ "<td>${object.informationData!}</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Secret Info</td>"
				+ "<td>${object.secretInfo!}</td>"
				+ "</tr>"
				+ "</tbody>"
				+ "</table>"
				+ "<p>&nbsp;</p>");
		Message message = notificationManager.templateMessageResolver(templateMessage, map);
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setSubject(message.getSubject());
		notificationMessage.setContentMessage(message.getContent());
		notificationMessage.setFromId(personSender.getPersonUUID());
		notificationMessage.setToId(personDestination.getPersonUUID());
		notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
		notificationMessage.setReceivedTime(new Date());
		notificationManager.insertNewMessage(notificationMessage);
	}
}