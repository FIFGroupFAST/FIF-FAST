package co.id.fifgroup.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.domain.Information;
import co.id.fifgroup.service.InformationService;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;;

@VariableResolver(DelegatingVariableResolver.class)
public class InformationInquiryComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 4002060528056349541L;

	@Wire
	private Window winSBInformationInquiry;
	@Wire
	private Hbox hboxRequestor;
	@Wire
	private Datebox dtbFrom, dtbTo;
	@Wire
	private Label lblErrorMessageDatebox;
	@Wire
	private Listbox lstInformationInquiry, lstInformationInvisible;
	@Wire
	private Listhead lstheadInformationInquiry, lstheadInformationInquiryInvisible;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient InformationService informationServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	
	private CommonPopupDoubleBandbox bnbRequestor;
	private Information information = null;
	private boolean isManagerandSuperUser = false;
	private boolean isHCSelfService = false;
	private boolean isSuperUser = false;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		loadListInformation();
	}
	
	public void init() {
		//doAfterBack
		if ((Information) arg.get("param") != null) {			
			List<Information> informations = informationServiceImpl.getInformationByFilter((Information) arg.get("param"));
			ListModelList<Information> modelList = new ListModelList<>(informations);
			modelList.setMultiple(true);
			lstInformationInquiry.setModel(modelList);
		}
		
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
			} // ROLE HC Self Service
		}
		//-----------------------------------------------------------------
		
		dtbFrom.setFormat(FormatDateUI.getDateFormat());
		dtbTo.setFormat(FormatDateUI.getDateFormat());
		
		bnbRequestor = new CommonPopupDoubleBandbox();
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

			private static final long serialVersionUID = -6642997747510609038L;

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

	public void loadListInformation() {
		Listheader listheader = new Listheader("Action");
		listheader.setHflex("1");
		listheader.setAlign("center");
		listheader.setParent(lstheadInformationInquiry);
		
		Listheader listheader2 = new Listheader("Requestor");
		listheader2.setHflex("1");
		listheader2.setAlign("center");
		listheader2.setSort("auto(requestorNumber)");
		listheader2.setParent(lstheadInformationInquiry);
		
		Listheader listheader3 = new Listheader("Request Date");
		listheader3.setHflex("1");
		listheader3.setAlign("center");
		listheader3.setSort("auto(requestDate)");
		listheader3.setParent(lstheadInformationInquiry);
		
		Listheader listheader4 = new Listheader("Approval Status");
		listheader4.setHflex("1");
		listheader4.setAlign("center");
		listheader4.setSort("auto(approvalStatus)");
		listheader4.setParent(lstheadInformationInquiry);
		
		Listheader listheader5 = new Listheader("Information");
		listheader5.setHflex("1");
		listheader5.setAlign("center");
		listheader5.setSort("auto(informationData)");
		listheader5.setParent(lstheadInformationInquiry);
		
		if (isHCSelfService == false) {			
			Listheader listheader6 = new Listheader("Secret Info");
			listheader6.setHflex("1");
			listheader6.setAlign("center");
			listheader6.setSort("auto(secretInfo)");
			listheader6.setParent(lstheadInformationInquiry);
		}
		
		Listheader listheader7 = new Listheader("Last Updated Date");
		listheader7.setHflex("1");
		listheader7.setAlign("center");
		listheader7.setSort("auto(lastUpdatedDate)");
		listheader7.setParent(lstheadInformationInquiry);
		
		Listheader listheader8 = new Listheader("Last Updated By");
		listheader8.setHflex("1");
		listheader8.setAlign("center");
		listheader8.setSort("auto(lastUpdatedBy)");
		listheader8.setParent(lstheadInformationInquiry);
		
		lstInformationInquiry.setItemRenderer(new SerializableListItemRenderer<Information>() {

			private static final long serialVersionUID = 4053568480781555328L;

			@Override
			public void render(Listitem item, final Information information, int index) throws Exception {
				Listcell listcell = new Listcell();
				A link = new A();
				link.setLabel("Detail");
				link.setStyle("text-decoration:underline; color:blue;");
				link.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {
					private static final long serialVersionUID = 2200838976331263788L;
					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("data", information);
						params.put("param", InformationInquiryComposer.this.information);
						Executions.createComponents("~./hcms/sandbox/sb_information_entry.zul", 
							winSBInformationInquiry.getParent(), params);
						winSBInformationInquiry.detach();
					}
				});
				link.setParent(listcell);
				listcell.setParent(item);
				
				Listcell listcell2 = new Listcell(information.getRequestorNumber()+" - "+information.getRequestorName());
				listcell2.setParent(item);
				
				Listcell listcell3 = new Listcell(DateUtil.format(information.getRequestDate()));
				listcell3.setParent(item);
				
				Listcell listcell4 = new Listcell();
				A linkStatus = new A();
				linkStatus.setLabel(information.getApprovalStatus());
				linkStatus.setStyle("text-decoration:underline; color:blue;");
				linkStatus.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {
					private static final long serialVersionUID = 1851439543112442421L;
					@Override
					public void onEvent(Event event) throws Exception {
						onRequestWorkFlowApprovalHistory(information);
					}
				});
				linkStatus.setParent(listcell4);
				listcell4.setParent(item);
				
				Listcell listcell5 = new Listcell(information.getInformationData());
				listcell5.setParent(item);
				
				if (isHCSelfService == false) {					
					Listcell listcell6 = new Listcell(information.getSecretInfo());
					listcell6.setParent(item);
				}
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
				Listcell listcell7 = new Listcell(dateFormat.format(information.getLastUpdatedDate()));
				listcell7.setParent(item);
				
				Listcell listcell8 = new Listcell(information.getLastUpdatedBy());
				listcell8.setParent(item);
			}
		});
		
		Listheader listheader2Invisible = new Listheader("Requestor");
		listheader2Invisible.setHflex("1");
		listheader2Invisible.setAlign("center");
		listheader2Invisible.setParent(lstheadInformationInquiryInvisible);
		
		Listheader listheader3Invisible = new Listheader("Request Date");
		listheader3Invisible.setHflex("1");
		listheader3Invisible.setAlign("center");
		listheader3Invisible.setParent(lstheadInformationInquiryInvisible);
		
		Listheader listheader4Invisible = new Listheader("Approval Status");
		listheader4Invisible.setHflex("1");
		listheader4Invisible.setAlign("center");
		listheader4Invisible.setParent(lstheadInformationInquiryInvisible);
		
		Listheader listheader5Invisible = new Listheader("Information");
		listheader5Invisible.setHflex("1");
		listheader5Invisible.setAlign("center");
		listheader5Invisible.setParent(lstheadInformationInquiryInvisible);
		
		if (isHCSelfService == false) {			
			Listheader listheader6Invisible = new Listheader("Secret Info");
			listheader6Invisible.setHflex("1");
			listheader6Invisible.setAlign("center");
			listheader6Invisible.setParent(lstheadInformationInquiryInvisible);
		}
		
		Listheader listheader7Invisible = new Listheader("Last Updated Date");
		listheader7Invisible.setHflex("1");
		listheader7Invisible.setAlign("center");
		listheader7Invisible.setParent(lstheadInformationInquiryInvisible);
		
		Listheader listheader8Invisible = new Listheader("Last Updated By");
		listheader8Invisible.setHflex("1");
		listheader8Invisible.setAlign("center");
		listheader8Invisible.setParent(lstheadInformationInquiryInvisible);
		
		lstInformationInvisible.setItemRenderer(new SerializableListItemRenderer<Information>() {

			private static final long serialVersionUID = 4053568480781555328L;

			@Override
			public void render(Listitem item, final Information information, int index) throws Exception {
				
				Listcell listcell2 = new Listcell(information.getRequestorNumber()+" - "+information.getRequestorName());
				listcell2.setParent(item);
				
				Listcell listcell3 = new Listcell(DateUtil.format(information.getRequestDate()));
				listcell3.setParent(item);
				
				Listcell listcell4 = new Listcell(information.getApprovalStatus());
				listcell4.setParent(item);
				
				Listcell listcell5 = new Listcell(information.getInformationData());
				listcell5.setParent(item);
				
				if (isHCSelfService == false) {					
					Listcell listcell6 = new Listcell(information.getSecretInfo());
					listcell6.setParent(item);
				}
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
				Listcell listcell7 = new Listcell(dateFormat.format(information.getLastUpdatedDate()));
				listcell7.setParent(item);
				
				Listcell listcell8 = new Listcell(information.getLastUpdatedBy());
				listcell8.setParent(item);
			}
		});
	}
	
	@Listen("onClick = button#btnFind")
	public void onClickFind() {
		lblErrorMessageDatebox.setVisible(false);
		if (validate()) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
				private static final long serialVersionUID = 1L;
				@Override
				public void onEvent(Event event) throws Exception {
					int status = (int) event.getData();
					if (status == Messagebox.YES)
						doFind();
				}
			});
		} else if (dtbFrom.getValue() != null && dtbTo.getValue() != null && dtbTo.getValue().before(dtbFrom.getValue())) {			
			lblErrorMessageDatebox.setVisible(true); 
			lblErrorMessageDatebox.setValue("Date To must be after Date From");
		} else {
			doFind();
		}
	}
	
	public boolean validate() {
		boolean isValidate = true;
		if (!bnbRequestor.getValue().equalsIgnoreCase(""))
			isValidate = false;
		if (dtbFrom.getValue() != null)
			isValidate = false;
		if (dtbTo.getValue() != null)
			isValidate = false;
		return isValidate;
	}
	
	public void doFind() {
		information = new Information();
		if (!bnbRequestor.getValue().equalsIgnoreCase("")) {			
			String[] parts = bnbRequestor.getValue().split(" - ");
			String employeeName = parts[1];
			information.setRequestorName(employeeName);
		}
		if (dtbFrom.getValue() != null)
			information.setCreatedDate(dtbFrom.getValue());
		if (dtbTo.getValue() != null)
			information.setLastUpdatedDate(dtbTo.getValue());
		
		if (isManagerandSuperUser || isHCSelfService) {
			information.setInOrganizationId(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations());
			information.setNotInOrganizationId(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations());
			if (isManagerandSuperUser)
				information.setManagerNumber(Integer.parseInt(securityServiceImpl.getSecurityProfile().getEmployeeNumber()));
		}
		if (isSuperUser) {
			information.setInOrganizationId(null);
			information.setNotInOrganizationId(null);
		}
		
		List<Information> informations = informationServiceImpl.getInformationByFilter(information);
		ListModelList<Information> modelList = new ListModelList<>(informations);
		modelList.setMultiple(true);
		lstInformationInquiry.setModel(modelList);
		lstInformationInvisible.setModel(modelList);
	}
	
	@Listen("onClick = button#btnNew")
	public void onClickNew() {
		Map<String, Object> params = new HashMap<String, Object>();
		Executions.createComponents("~./hcms/sandbox/sb_information_entry.zul", winSBInformationInquiry.getParent(), params);
		winSBInformationInquiry.detach();
	}
	
	public void onRequestWorkFlowApprovalHistory(Information information) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("avmTrxId", information.getAvmTrxId());
		params.put("companyId", information.getCompanyId());
		Window win = (Window) Executions.createComponents("~./hcms/workflow/workflow_approval_history.zul", getSelf().getParent(), params);
		win.doModal();
	}
}