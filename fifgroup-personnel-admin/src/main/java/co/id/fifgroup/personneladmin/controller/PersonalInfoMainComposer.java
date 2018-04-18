package co.id.fifgroup.personneladmin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.constant.ActionControl;
import co.id.fifgroup.personneladmin.constant.DocumentType;
import co.id.fifgroup.personneladmin.constant.FieldPermissionsPersonnelAdmin;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.constant.PersonnelAdminPage;
import co.id.fifgroup.personneladmin.constant.ReferencePersonnelAdministration;
import co.id.fifgroup.personneladmin.dto.MediaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.dto.PersonMainDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.service.PersonalInformationService;
import co.id.fifgroup.personneladmin.util.FileUtil;

import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.NavigationTransactionForm;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class PersonalInfoMainComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PersonalInfoMainComposer.class);
	
	private PersonalInfoMainComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonalInformationService personalInformationService;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	
	@Wire
	private Grid gridHeaderInformation;
	@Wire
	private Listbox cmbPageSelector;
	@Wire
	private Div personalInfoContent;
	@Wire
	private Button btnEditPhoto;
	@Wire
	private Image imgPhoto;
	@Wire
	private Label lblNumberFullName;
	@Wire
	private Label lblPeopleType;
	@Wire
	private Label lblOrganization;
	@Wire
	private Label lblLocation;
	@Wire
	private Label lblJob;
	@Wire
	private Label lblJobGroup;
	@Wire
	private Label lblPersonalGrade;	
	@Wire
	private Button btnCancel;
	
	private ActionControl control;	
	private PersonMainDTO personMainDTO;
	private PersonMainDTO prevObject;
	private ListModelList<PersonnelAdminPage> listModel;
	private AImage fullImage;
			
	public PersonMainDTO getPersonMainDTO() {
		return personMainDTO;
	}

	public void setPersonMainDTO(PersonMainDTO personMainDTO) {
		this.personMainDTO = personMainDTO;
	}
	
	public PersonMainDTO getPrevObject() {
		return prevObject;
	}

	public void setPrevObject(PersonMainDTO prevObject) {
		this.prevObject = prevObject;
	}
	
	public Date getEffectiveOnDate(){
		Date effectiveOnDate = (Date) arg.get("effectiveOnDate");
		return effectiveOnDate;
	}

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		params.putAll(arg);
		control = (ActionControl) arg.get("actionControl");
		
		setPersonMainDTO(new PersonMainDTO());
		populateData();
		getPageSelector();
		changePage();
	}
		
	@Listen ("onUpload = #btnEditPhoto")
	public void onBrowse(UploadEvent event) {
		if(FileUtil.doValidateImageFile(event.getMedia())) {
			if (FileUtil.doValidateDocumentSize(event.getMedia(), 120)) {
				try {
					MediaDTO mediaDTO = new MediaDTO();
					mediaDTO.setMedia(event.getMedia());
					mediaDTO.setDocumentType(DocumentType.PHOTO.name());
					fullImage = new AImage(mediaDTO.getMedia().getName(), mediaDTO.getMedia().getStreamData());
					imgPhoto.setContent(fullImage);
					getPersonMainDTO().getPersonDTO().setMediaPhoto(mediaDTO);
					getPersonMainDTO().getPersonDTO().setPhotoFilePath(mediaDTO.getMedia().getName());
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}				
			}
		}
	}
	
	public void populateData() {
		if (arg.containsKey("person") 
				&& personMainDTO.getPersonDTO() == null) {
			PersonDTO personDTO = (PersonDTO) arg.get("person");
			Date effectiveOnDate = (Date) arg.get("effectiveOnDate");
			personDTO = personService.getPersonalData(personDTO.getPersonId(), personDTO.getCompanyId(), DateUtil.truncate(effectiveOnDate));
			if (control.equals(ActionControl.HIRE_INTERNSHIP)) {
				personDTO.setHireDate(effectiveOnDate);
			}
			personMainDTO.setPersonDTO(personDTO);
			prevObject = (PersonMainDTO) DeepCopy.copy(personMainDTO);
		}
		
		if (control.equals(ActionControl.VIEW)
				|| control.equals(ActionControl.NEW_HISTORY)) {
			try {
				cmbPageSelector.setDisabled(false);
				lblNumberFullName.setValue(personMainDTO.getPersonDTO().getEmployeeNumber() + " - " + personMainDTO.getPersonDTO().getFullName());
				lblPeopleType.setValue(PeopleType.getPeopleTypeDescription(personMainDTO.getPersonDTO().getPeopleType()));
				lblOrganization.setValue(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationName());
				lblLocation.setValue(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getLocationName());
				lblJob.setValue(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getJobName());
				if (personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getJobGroupCode() != null) {
					KeyValue jobGroup = lookupServiceImpl.getLookup(ReferencePersonnelAdministration.LOOKUP_JOB_GROUP.getReference(), 
							personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getJobGroupCode(), personMainDTO.getPersonDTO().getGroupId(),
							personMainDTO.getPersonDTO().getCompanyId());
					lblJobGroup.setValue(jobGroup != null ? jobGroup.getDescription().toString() : "");					
				}
				if (personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getPersonalGradeCode() != null) {
					KeyValue personalGrade = lookupServiceImpl.getLookup(ReferencePersonnelAdministration.LOOKUP_PERSONAL_GRADE.getReference(), 
							personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getPersonalGradeCode(),
							personMainDTO.getPersonDTO().getGroupId(),
							personMainDTO.getPersonDTO().getCompanyId());
					lblPersonalGrade.setValue(personalGrade != null ? personalGrade.getDescription().toString() : "");
				}
				if (personMainDTO.getPersonDTO().getPhotoFilePath() != null && !personMainDTO.getPersonDTO().getPhotoFilePath().equals("")) {
					File fPhoto = new File(GlobalVariable.getAppsRootFolder() + personMainDTO.getPersonDTO().getPhotoFilePath());
					InputStream is = new FileInputStream(fPhoto);
					fullImage = new AImage(personMainDTO.getPersonDTO().getPhotoFilePath(), is);
					imgPhoto.setContent(fullImage);
				}				
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		} else if (control.equals(ActionControl.NEW_PEOPLE)
				|| control.equals(ActionControl.HIRE_INTERNSHIP)
				|| control.equals(ActionControl.NEW_AFFCO)) {
			lblNumberFullName.setValue("Employee Number - Full Name");
			cmbPageSelector.setDisabled(true);
			gridHeaderInformation.setVisible(false);
			btnCancel.setVisible(false);
		}
	}
	
	@Listen("onClick = #imgPhoto")
	public void popupPhoto() {
		if (fullImage != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", fullImage);
			Window window = (Window) Executions.createComponents(
					"~./hcms/personneladmin/container_image.zul", getSelf().getParent(), map);
			window.setClosable(true);
			window.setMaximized(false);
			window.setPosition("nocenter");
			window.setWidth(String.valueOf(fullImage.getWidth()));
			window.setHeight(String.valueOf(fullImage.getHeight()));
			window.doModal();
		}
	}
	
	public void getPageSelector() {
		listModel = new ListModelList<>(PersonnelAdminPage.values());
		if (control.equals(ActionControl.VIEW)
				|| control.equals(ActionControl.NEW_HISTORY)) {
			listModel.remove(PersonnelAdminPage.ASSIGNMENT);
			listModel.remove(PersonnelAdminPage.ASSIGNMENT_INTERSHIP);
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.SALARY_HISTORY_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.SALARY_HISTORY);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.DOCUMENT_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.DOCUMENTS);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.COMPETENCY_MANAGEMENT_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.COMPETENCY_MANAGEMENT);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.PERFORMACE_REVIEW_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.PERFORMANCE_REVIEW);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.CAREER_MANAGEMENT_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.CAREER_MANAGEMENT);
			}

			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.BANK_INFORMATION_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.BANK_INFORMATION);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.BENEFIT_ENTITLEMENT_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.BENEFIT_ENTITLEMENT);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.DISIPLINARY_LETTER_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.DICIPLINARY_LETTER);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.LEAVE_ENTITLEMENT_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.LEAVE_ENTITLEMENT);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.OCOP_TRANSACTION_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.OCOP);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.PEOPLE_HISTORY_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.PEOPLE_HISTORY);
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.SPP_HISTORY_PAGE_HIDE)) {
				listModel.remove(PersonnelAdminPage.SPP_HISTORY);
			}
			
				
		} else if (control.equals(ActionControl.NEW_PEOPLE)
				|| control.equals(ActionControl.HIRE_INTERNSHIP)
				|| control.equals(ActionControl.NEW_AFFCO)){
			listModel.remove(PersonnelAdminPage.ASSIGNMENT_INQUIRY);
			PeopleType peopleType = (PeopleType) arg.get("peopleType");
			if (peopleType.name().equalsIgnoreCase(PeopleType.INTERNSHIP.name())) {
				listModel.remove(PersonnelAdminPage.ASSIGNMENT);
			} else {
				listModel.remove(PersonnelAdminPage.ASSIGNMENT_INTERSHIP);
			}			
		}
		listModel.addToSelection(PersonnelAdminPage.PEOPLE_INFORMATION);
		cmbPageSelector.setModel(listModel);
		cmbPageSelector.renderAll();
	}
	
	@Listen("onSelect = #cmbPageSelector")
	public void changePage(){
		PersonnelAdminPage pageSelector = (PersonnelAdminPage) cmbPageSelector.getSelectedItem().getValue();
		if (pageSelector.equals(PersonnelAdminPage.PEOPLE_INFORMATION)) { 
			btnEditPhoto.setDisabled(true);
			checkHistorical();
		} else {
			btnEditPhoto.setDisabled(true);
		}
		String url = pageSelector.getUrl();
		
		if(personalInfoContent.getChildren().size() == 0)
			createForm(url);
		else{
			personalInfoContent.getFirstChild().detach();
			createForm(url);
		}
	}
	
	private void createForm(String url){
		try {
			params.put("parentPage", thisComposer);			
			Executions.createComponents(url, personalInfoContent, params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
		
	@Listen("onClick=#btnCancel")
	public void doCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = -1635957824322347977L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					doClose();
				}
			}
		});		
	}
	
	public void doClose() {
		if (control.equals(ActionControl.NEW_AFFCO)) {
			Executions.createComponents("~./hcms/personneladmin/affco_mutation.zul", getSelf().getParent(), null);
			getSelf().detach();
		} else{
			if(arg.containsKey("composer")){
				Map<String, Object> map = new HashMap<String, Object>();
				if(arg.containsKey("data")){
					map.put("param", arg.get("data"));
					Executions.createComponents("~./hcms/career-management/career_icps_request.zul", getSelf().getParent(), map);
					getSelf().detach();	
				}
				else if(arg.containsKey("dataPeopleRev")){
					map.put("param", arg.get("dataPeopleRev"));
					Executions.createComponents("~./hcms/career-management/career_people_review.zul", getSelf().getParent(), map);
					getSelf().detach();	
				}
				
			}else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("workspaceMain", (NavigationTransactionForm) arg.get("workspaceMain"));
				Executions.createComponents("~./hcms/personneladmin/people_search.zul", getSelf().getParent(), map);
				getSelf().detach();	
			}
		}
	}
	
	public void getNextPage(PersonnelAdminPage page) {
		listModel.addToSelection(page);
		changePage();
	}
	
	private void checkHistorical() {
		try {
			Date effectiveOnDate = (Date) arg.get("effectiveOnDate");
			if (control.equals(ActionControl.NEW_HISTORY)) {
				if (personMainDTO.getPersonDTO() != null) {				
					if(personalInformationService.isCurrent(personMainDTO.getPersonDTO())) {
						btnEditPhoto.setDisabled(false);
						if(personalInformationService.hasFuture(personMainDTO.getPersonDTO()) 
								|| personMainDTO.getPersonDTO().getEffectiveStartDate().equals(DateUtil.truncate(effectiveOnDate))) {
							btnEditPhoto.setDisabled(true);
						} else {
							if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.PERSONAL_INFO_MAIN_BTN_EDIT_PHOTO_DISABLED)) {
								btnEditPhoto.setDisabled(true);
							}			
						}
					} else if(personalInformationService.isFuture(personMainDTO.getPersonDTO())) {
						btnEditPhoto.setDisabled(true);
					} else if(personalInformationService.isPastHistory(personMainDTO.getPersonDTO())) {
						btnEditPhoto.setDisabled(true);
					}
				}				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
