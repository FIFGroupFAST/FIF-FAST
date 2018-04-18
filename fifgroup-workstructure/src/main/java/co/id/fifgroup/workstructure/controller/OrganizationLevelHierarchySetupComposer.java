package co.id.fifgroup.workstructure.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.core.validation.EndDateValidator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.VersionValidator;
import co.id.fifgroup.workstructure.service.OrgLevelHierSetupService;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxFrom;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.workstructure.dto.OrgLevelHierDTO;
import co.id.fifgroup.workstructure.dto.OrgLevelHierElementDTO;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;
import co.id.fifgroup.workstructure.validation.OrgLevelHierValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class OrganizationLevelHierarchySetupComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(OrganizationLevelHierarchySetupComposer.class);

	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient OrgLevelHierSetupService orgLevelHierSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationLevelSetupService organizationLevelSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@Wire private Textbox txtLevelHierName;
	@Wire private Textbox txtOrgLevelHierDesc;
	@Wire private Listbox lstVersion;
	@Wire private DateBoxFrom dtbFrom;
	@Wire private DateBoxMax dtbTo;
	@Wire private TabularEntry<OrgLevelHierElementDTO> lstLevelHier;
	@Wire private Button btnDelete;
	@Wire private Button btnSave;
	@Wire private Button btnAddRowHier;
	@Wire private Button btnDeleteHier;
	
	private OrgLevelHierDTO selectedLevelHier;
	private List<OrgLevelHierElementDTO> levelHierElements;
	private ListModelList<OrganizationLevelDto> modelLevel;
	private ListModelList<String> modelVersion;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		selectedLevelHier = orgLevelHierSetupServiceImpl.find();
		if(selectedLevelHier != null) {
			readParameter(selectedLevelHier);
			populateVersion();
		} else {
			List<String> versions = new ArrayList<>();
			versions.add(Labels.getLabel("common.create"));
			ListModelList<String> model = new ListModelList<>(versions);
			lstVersion.setModel(model);
			lstVersion.setSelectedIndex(model.indexOf(Labels.getLabel("common.create")));
			lstVersion.setDisabled(true);
			btnSave.setDisabled(false);
			btnDelete.setDisabled(true);
		}
		buildHierTabular();		
	}
	
	@Listen("onDownloadUserManual = #winOrgLevelHierSetup")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void readParameter(OrgLevelHierDTO selectedLevelHier) {
		if(selectedLevelHier != null) {
			renderForm();
			checkDisabledComponent(selectedLevelHier);
		}
	}
	
	private void renderForm() {
		txtLevelHierName.setValue(selectedLevelHier.getLevelHierName());
		txtOrgLevelHierDesc.setValue(selectedLevelHier.getDescription());
		dtbFrom.setValue(selectedLevelHier.getDateFrom());
		dtbTo.setValue(selectedLevelHier.getDateTo());
		txtLevelHierName.setDisabled(true);
		txtOrgLevelHierDesc.setDisabled(true);
		dtbFrom.setDisabled(true);
		levelHierElements = selectedLevelHier.getHierElements();
		lstLevelHier.setModel(getModel());
	}
	
	private void checkDisabledComponent(OrgLevelHierDTO selectedLevelHier) {
		if(selectedLevelHier.isFuture()) {
			enabledAllComponent();
		} else if(selectedLevelHier.isCurrent()) {
			if(orgLevelHierSetupServiceImpl.isHaveFuture(selectedLevelHier.getId())) {
				disabledAllComponent();
			}
			else {
				disabledAllComponent();
				btnSave.setDisabled(false);
				dtbTo.setDisabled(false);
			}
		} else if(DateUtils.truncate(selectedLevelHier.getDateTo(), Calendar.DATE).compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) < 0) {
			disabledAllComponent();
		}
	}
	
	private void buildHierTabular() {
		lstLevelHier.setDataType(OrgLevelHierElementDTO.class);
		lstLevelHier.setModel(getModel());
		lstLevelHier.setItemRenderer(getListItemRenderer());
		lstLevelHier.setValidationRule(getValidationRule());
	}
	
	private ListModelList<OrgLevelHierElementDTO> getModel() {
		if(levelHierElements == null)
			levelHierElements = new ArrayList<OrgLevelHierElementDTO>();
		
		ListModelList<OrgLevelHierElementDTO> model = new ListModelList<OrgLevelHierElementDTO>(levelHierElements);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<OrgLevelHierElementDTO> getListItemRenderer() {
		return new SerializableListItemRenderer<OrgLevelHierElementDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final OrgLevelHierElementDTO data, int index) throws Exception {
				new Listcell().setParent(item);
				data.setElementId(Long.valueOf(String.valueOf(index)));
//				LongboxListcell<OrgLevelHierElementDTO> sequence = new LongboxListcell<OrgLevelHierElementDTO>(data, data.getSequence(), "sequence");
//				sequence.getComponent().setMaxlength(4);
//				sequence.setParent(item);
				Listcell lcSequence = new Listcell();
				final Longbox lbSequence = new Longbox();
				lbSequence.setMaxlength(4);
				lbSequence.setValue(data.getSequence() == null ? null : data.getSequence());
				lbSequence.setParent(lcSequence);
				lcSequence.setParent(item);
				lbSequence.addEventListener(Events.ON_CHANGE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						data.setSequence(lbSequence.getValue());
					}
				});
				
				OrganizationLevelDto levelDto = new OrganizationLevelDto();
				levelDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				levelDto.setEffectiveOnDate(DateUtil.truncate(dtbFrom.getValue()));
				List<OrganizationLevelDto> levels = organizationLevelSetupServiceImpl.findByExample(levelDto);
				
				Listcell lcLevel = new Listcell();
				final Listbox lstLevel = new Listbox();
				lstLevel.setMold("select");
				lstLevel.setItemRenderer(new SerializableListItemRenderer<OrganizationLevelDto>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void render(Listitem item, OrganizationLevelDto data, int index)
							throws Exception {
						item.setValue(data);
						item.appendChild(new Listcell(data.getName().toString()));
					}
				});
				
				OrganizationLevelDto select = new OrganizationLevelDto();
				select.setName(Labels.getLabel("common.select"));
				
				modelLevel = new ListModelList<OrganizationLevelDto>();
				modelLevel.add(select);
				modelLevel.addAll(levels);
				lstLevel.setModel(modelLevel);
				lstLevel.renderAll();
				modelLevel.addToSelection(select);

				lstLevel.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -5788144121529892249L;

					@Override
					public void onEvent(Event event) throws Exception {
						OrganizationLevelDto orgLevel = (OrganizationLevelDto) lstLevel.getSelectedItem().getValue();
						data.setLevelId(orgLevel.getId());
						data.setLevelName(orgLevel.getName());
					}
				});
				if(data.getLevelId() != null) {
					for(OrganizationLevelDto orgLevel : modelLevel) {
						if(orgLevel.getId() != null && orgLevel.getId().equals(data.getLevelId())) {
							modelLevel.addToSelection(orgLevel);
							break;
						}
					}
				}
				lcLevel.appendChild(lstLevel);
				lcLevel.setParent(item);				
			}
		};
	}
	
	private TabularValidationRule<OrgLevelHierElementDTO> getValidationRule() {
		return new TabularValidationRule<OrgLevelHierElementDTO>() {
			@Override
			public boolean validate(OrgLevelHierElementDTO data, List<String> errorRow) {
				if(data.getSequence() == null )
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.hierarchySequence") }));
				else {
					for(OrgLevelHierElementDTO elementDTO : lstLevelHier.getValue()) {
						if(!elementDTO.getElementId().equals(data.getElementId())) {
							if(elementDTO.getSequence() != null && data.getSequence() != null) {
								if(elementDTO.getSequence().equals(data.getSequence())) {
									errorRow.add(Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.hierarchySequence") }));
								}
							}
							if(elementDTO.getLevelId() != null && data.getLevelId() != null) {
								if(elementDTO.getLevelId().equals(data.getLevelId())) {
									errorRow.add(Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.organizationLevelInOrganizationLevelHierarchy") }));
								}
							}
						}
					}
				}
				if(data.getLevelId() == null)
					errorRow.add(Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("wos.organizationLevel") }));
				
				
				
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
	
	@Listen("onClick = #btnAddRowHier")
	public void addRowProduct() {
		lstLevelHier.addRow();
	}
	
	@Listen("onClick = #btnDeleteHier")
	public void deleteRowProduct() {
		lstLevelHier.deleteRow();
	}
	
	private void populateVersion() {
		List<Integer> versionList = orgLevelHierSetupServiceImpl.findVersionsById(selectedLevelHier.getId());
		List<String> versions = new ArrayList<>();
		for(Integer version : versionList)
			versions.add(String.valueOf(version));
		versions.add(Labels.getLabel("common.create"));
		
		modelVersion = new ListModelList<>(versions);
		lstVersion.setModel(modelVersion);
		lstVersion.setSelectedIndex(versionList.indexOf(selectedLevelHier.getVersionNumber()));
		
		if(versionList.size() <= selectedLevelHier.getVersionNumber()) {
			dtbTo.setDisabled(false);
			btnSave.setDisabled(false);
		}
	}
	
	@Listen ("onSelect = Listbox#lstVersion")
	public void onChangeVersion() {
		String selectedVersionLabel = lstVersion.getSelectedItem().getLabel();
		int selectedVersion = 0;
		clearErrorMessage();
		if(!selectedVersionLabel.equals(Labels.getLabel("common.create"))) 
			selectedVersion = Integer.parseInt(selectedVersionLabel);
		else
			selectedVersion = selectedLevelHier.getVersionNumber();
		
		selectedLevelHier = getSelectedVersion(selectedVersion);
		checkDisabledComponent(selectedLevelHier);
		
		if(selectedVersionLabel.equals(Labels.getLabel("common.create"))) {
			if(!orgLevelHierSetupServiceImpl.isFutureExist(selectedLevelHier.getId())) {
				enabledAllComponent();
				btnDelete.setDisabled(true);
				btnSave.setDisabled(false);
				selectedLevelHier.setVersionId(null);
				dtbFrom.setValue(DateUtil.add(new Date(), Calendar.DATE, 1));
				dtbTo.setValue(DateUtil.MAX_DATE);
			} else {
				Messagebox.show(Labels.getLabel("common.cannotCreateFutureExist"));
				modelVersion.addToSelection(selectedLevelHier.getVersionNumber().toString());
			}
		}
	}	
	
	private OrgLevelHierDTO getSelectedVersion(int selectedVersion){
		selectedLevelHier.setVersionNumber(selectedVersion);
		selectedLevelHier = orgLevelHierSetupServiceImpl.findByIdAndVersionNumber(selectedLevelHier.getId(), selectedVersion);
		if(selectedLevelHier != null) {
			try {
				readParameter(selectedLevelHier);					
				return selectedLevelHier;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		}		
		return new OrgLevelHierDTO();
	}
	
	private void enabledAllComponent() {
		txtOrgLevelHierDesc.setDisabled(false);
		dtbFrom.setDisabled(false);
		dtbTo.setDisabled(false);
		lstLevelHier.setDisabled(false);
		btnAddRowHier.setDisabled(false);
		btnDeleteHier.setDisabled(false);
		btnSave.setDisabled(false);
		btnDelete.setDisabled(false);
	}
	
	private void disabledAllComponent() {
		txtOrgLevelHierDesc.setDisabled(true);
		dtbFrom.setDisabled(true);
		dtbTo.setDisabled(true);
		lstLevelHier.setDisabled(true);
		btnAddRowHier.setDisabled(true);
		btnDeleteHier.setDisabled(true);
		btnSave.setDisabled(true);
		btnDelete.setDisabled(true);
	}
	
	@Listen("onClick = button#btnSave")
	public void onSave() {
		populateLevelHierarchy();
		String confirmationMsg = "";
		if(selectedLevelHier.getId() == null)
			confirmationMsg = Labels.getLabel("common.confirmationMessage");
		else 
			confirmationMsg = Labels.getLabel("common.confirmationSaveVersion");

		Messagebox.show(confirmationMsg, Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					if(lstLevelHier.validate()) {
						try {
							clearErrorMessage();
							orgLevelHierSetupServiceImpl.save(selectedLevelHier);
							Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
							selectedLevelHier = orgLevelHierSetupServiceImpl.find();
							if(selectedLevelHier != null) {
								readParameter(selectedLevelHier);
								populateVersion();
							}
						} catch (ValidationException ve) {
							showErrorMessages(ve.getConstraintViolations());
							logger.error(ve.getAllMessages());
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}			
					}
				}
			}
		});
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(dtbFrom);
		ErrorMessageUtil.clearErrorMessage(dtbTo);
		ErrorMessageUtil.clearErrorMessage(txtLevelHierName);
		if(lstLevelHier.getPreviousSibling() instanceof Hlayout) 
			lstLevelHier.getParent().removeChild(lstLevelHier.getPreviousSibling());
		ErrorMessageUtil.clearErrorMessage(txtOrgLevelHierDesc);
	}
	
	private void showErrorMessages(Map<String, String> vex) {
		ErrorMessageUtil.setErrorMessage(dtbFrom, vex.get(VersionValidator.DATE_FROM));
		ErrorMessageUtil.setErrorMessage(dtbTo, vex.get(EndDateValidator.DATE_TO));
		ErrorMessageUtil.setErrorMessage(txtLevelHierName, vex.get(OrgLevelHierValidator.LEVEL_HIER_NAME));
		if(vex.containsKey(OrgLevelHierValidator.LEVEL_ID)) {
			elementValidator(vex, OrgLevelHierValidator.LEVEL_ID);
		}
		if(vex.containsKey(OrgLevelHierValidator.ELEMENTS)){
			elementValidator(vex, OrgLevelHierValidator.ELEMENTS);
		}
		ErrorMessageUtil.setErrorMessage(txtOrgLevelHierDesc, vex.get(OrgLevelHierValidator.DESCRIPTION));
	}
	
	private void elementValidator(Map<String, String> vex, String b){
		Label lblErrorMsg = new Label(vex.get(b));
		lblErrorMsg.setStyle("color:red");
		Hlayout hlayout = new Hlayout();
		hlayout.appendChild(lblErrorMsg);
		hlayout.appendChild(new Separator());
		lstLevelHier.getParent().insertBefore(hlayout, lstLevelHier);
	}
	
	private void populateLevelHierarchy() {
		if(selectedLevelHier == null)
			selectedLevelHier = new OrgLevelHierDTO();
			
		selectedLevelHier.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedLevelHier.setCreationDate(new Date());
		selectedLevelHier.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedLevelHier.setLastUpdateDate(new Date());
		selectedLevelHier.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		selectedLevelHier.setDateFrom(dtbFrom.getValue());
		selectedLevelHier.setDateTo(dtbTo.getValue());
		selectedLevelHier.setLevelHierName(txtLevelHierName.getValue().toUpperCase());
		selectedLevelHier.setDescription(txtOrgLevelHierDesc.getValue());
		selectedLevelHier.setHierElements(lstLevelHier.getValue());

	}
	
	@Listen("onClick = button#btnDelete")
	public void onDelete() {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					orgLevelHierSetupServiceImpl.delete(selectedLevelHier);
					Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
					selectedLevelHier = orgLevelHierSetupServiceImpl.find();
					if(selectedLevelHier != null) {
						readParameter(selectedLevelHier);
						populateVersion();
					}
				}
			}
		});
	}

}
