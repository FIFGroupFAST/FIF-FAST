package co.id.fifgroup.workstructure.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.dto.ContactDTO;
import co.id.fifgroup.core.dto.PersonAssignmentProfileDTO;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.PersonProfileService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dto.HeadOfOrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class EmployeeProfileComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(EmployeeProfileComposer.class);

	@WireVariable("arg") private Map<Object, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonProfileService personProfileServiceImpl;
	
	@Wire private Label lblEmployee;
	@Wire private Label lblPeopleType;
	@Wire private Label lblOrg;
	@Wire private Label lblLocation;
	@Wire private Label lblJob;
	@Wire private Label lblJobGroup;
	@Wire private Label lblPersonalGrade;
	@Wire private Listbox lstCommunication;
	@Wire private Image imgPhoto;
	
	private final String jobGroupLookup = "WSU_JOB_GROUP";
	private final String personalGradeLookup = "WSU_PERSONAL_GRADE";
	private final String contactLookup = "MST_COMMUNICATION_TYPE";
	private OrganizationDTO selectedOrg;
	private OrgHierarchyDTO selectedHier;
	private HeadOfOrganizationDTO selectedEmployee;
	private PersonAssignmentProfileDTO selectedPersonAssignment;
	private FunctionPermission functionPermission;
	private AImage fullImage;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		selectedHier = (OrgHierarchyDTO) arg.get(OrgHierarchyDTO.class.getName());
		selectedOrg = (OrganizationDTO) arg.get(OrganizationDTO.class.getName());
		selectedEmployee = (HeadOfOrganizationDTO) arg.get(HeadOfOrganizationDTO.class.getName());
		if(selectedEmployee != null) {
			if (selectedEmployee.getPhotoFilePath() != null && !selectedEmployee.getPhotoFilePath().equals("")) {
				try {
					File fPhoto = new File(GlobalVariable.getAppsRootFolder() + selectedEmployee.getPhotoFilePath());
					InputStream is = new FileInputStream(fPhoto);
					fullImage = new AImage(selectedEmployee.getPhotoFilePath(), is);
					imgPhoto.setContent(fullImage);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			selectedPersonAssignment = personProfileServiceImpl.selectPersonByAssignment(selectedEmployee.getPersonId(), 
					securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		}
		renderDetail();
	}
	
	private void renderDetail() {
		lblEmployee.setValue(selectedEmployee.getEmployeeNumber() + "-" + selectedEmployee.getFullName());
		if(selectedPersonAssignment != null) {
			lblPeopleType.setValue(selectedPersonAssignment.getPeopleType());
			lblOrg.setValue(selectedPersonAssignment.getOrganizationName());
			lblLocation.setValue(selectedPersonAssignment.getLocationName());
			lblJob.setValue(selectedPersonAssignment.getJobName());
			
			if(selectedPersonAssignment.getJobGroupCode() != null) {
				KeyValue jobGroupKeyValue = lookupServiceImpl.getLookup(jobGroupLookup, selectedPersonAssignment.getJobGroupCode(), 
						securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				lblJobGroup.setValue(jobGroupKeyValue != null ? (String)jobGroupKeyValue.getDescription() : null);
			}
			
			if(selectedPersonAssignment.getPersonalGradeCode() != null) {
				KeyValue personalGradeKeyValue = lookupServiceImpl.getLookup(personalGradeLookup, selectedPersonAssignment.getPersonalGradeCode(), 
						securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				lblPersonalGrade.setValue(personalGradeLookup != null ? (String)personalGradeKeyValue.getDescription() : null);
			}
			
			if(selectedPersonAssignment.getContacts() != null) {
				for(ContactDTO contact : selectedPersonAssignment.getContacts()) {
					KeyValue kvType = lookupServiceImpl.getLookupCaseInsensitive(contactLookup, contact.getTypeCode(), 
							securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(),
							securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					if(kvType != null)
						contact.setType(kvType.getDescription().toString());
				}
				lstCommunication.setModel(new ListModelList<ContactDTO>(selectedPersonAssignment.getContacts()));
			}
		}
	}
	
	@Listen ("onClick = button#btnBack")
	public void onBack() {
		Map<Object, Object> param = new HashMap<>();
		param.put(OrganizationDTO.class.getName(), selectedOrg);
		param.put(OrgHierarchyDTO.class.getName(), selectedHier);
		param.put(HeadOfOrganizationDTO.class.getName(), selectedEmployee);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/hoo_history.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
}
