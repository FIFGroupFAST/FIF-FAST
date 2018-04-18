package co.id.fifgroup.workstructure.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.constant.BranchSizeType;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.BranchSizeSetupService;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.BranchSizeDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;
import co.id.fifgroup.workstructure.validation.BranchSizeValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class BranchSizeSetupComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(BranchSizeSetupComposer.class);
	
	@Wire
	private Datebox dtbStartDate;
	@Wire
	private DateBoxMax dtbEndDate;
	@Wire
	private Label lblOrgLevel;
	@Wire
	private Listbox lstBranchSize;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnDelete;
	@Wire
	private CommonPopupDoubleBandbox bnbOrganization;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient BranchSizeSetupService branchSizeSetupServiceImpl; 
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationLevelSetupService organizationLevelSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient BranchSizeValidator branchSizeValidator;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	
	private BranchSizeDTO selectedBranchSize;
	private ListModelList<BranchSizeType> modelBranchSize = new ListModelList<BranchSizeType>();
	private Date dateFromBeforeCurrentEdit = null;
	private Date dateToBeforeCurrentEdit = null;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateBranchSize();
		populateOrganization();
		readParameter();
		dtbEndDate.setDisabled(true);
	}
	
	private void populateOrganization() {
		bnbOrganization.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<OrganizationDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationDTO> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
				OrganizationDTO example = new OrganizationDTO();
				example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setEffectiveOnDate(dtbStartDate.getValue() != null ? dtbStartDate.getValue() : new Date());
				example.setCode(searchCriteria1);
				example.setName(searchCriteria2);
				return organizationSetupServiceImpl.findOrganizationByCompany(example, limit, offset);	
				//return organizationSetupServiceImpl.getOrganizationByBranch(null, searchCriteria1, searchCriteria2, dtbStartDate.getValue() != null ? dtbStartDate.getValue() : new Date(), limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
					//return organizationSetupServiceImpl.countOrganizationByBranch(null, searchCriteria1, searchCriteria2, dtbStartDate.getValue() != null ? dtbStartDate.getValue() : new Date());
				OrganizationDTO example = new OrganizationDTO();
				example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setEffectiveOnDate(dtbStartDate.getValue() != null ? dtbStartDate.getValue() : new Date());
				example.setCode(searchCriteria1);
				example.setName(searchCriteria2);
				return organizationSetupServiceImpl.countOrganizationByCompany(example);
			}

			@Override
			public void mapper(KeyValue keyValue, OrganizationDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getCode());
				keyValue.setDescription(data.getName());
			}
		});
		bnbOrganization.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				lblOrgLevel.setValue(null);
				if (bnbOrganization.getKeyValue() != null) {
					OrganizationDTO organization = organizationSetupServiceImpl.findById((Long)bnbOrganization.getKeyValue().getKey(), null, dtbStartDate.getValue() != null ? dtbStartDate.getValue() : new Date());
					OrganizationLevelDto example = new OrganizationLevelDto();
					example.setCode(organization.getLevelCode());
					List<OrganizationLevelDto> levels = organizationLevelSetupServiceImpl.findByExample(example);
					if(levels.size() > 0)
						lblOrgLevel.setValue(levels.get(0).getName());
				}
			}
			
		});
	}

	private void readParameter() {
		selectedBranchSize = (BranchSizeDTO) arg.get(BranchSizeDTO.class.getName());
		if(selectedBranchSize != null) {
			dtbStartDate.setValue(selectedBranchSize.getEffectiveStartDate());
			dtbStartDate.setDisabled(true);
			dtbEndDate.setValue(selectedBranchSize.getEffectiveEndDate());
			bnbOrganization.setValue(selectedBranchSize.getOrganizationCode() + " - " + selectedBranchSize.getOrganizationName());
			
			OrganizationLevelDto example = new OrganizationLevelDto();
			example.setCode(selectedBranchSize.getOrganizationLevel());
			List<OrganizationLevelDto> levels = organizationLevelSetupServiceImpl.findByExample(example);
			if(levels.size() > 0)
				lblOrgLevel.setValue(levels.get(0).getName());
			
			if(selectedBranchSize.getSizeCode() != null) {
				for(BranchSizeType branchSizeType : modelBranchSize) {
					if(selectedBranchSize.getSizeCode().equals(branchSizeType.toString())) {
						modelBranchSize.addToSelection(branchSizeType);
						break;
					}
				}
			}
			
			if(selectedBranchSize.getId() != null) {
				dateFromBeforeCurrentEdit = selectedBranchSize.getEffectiveStartDate();
				dateToBeforeCurrentEdit = selectedBranchSize.getEffectiveEndDate();
				setDisableComponent(true);
				if(selectedBranchSize.getEffectiveStartDate().before(new Date())){
					btnDelete.setDisabled(true);
				}else if(selectedBranchSize.getEffectiveStartDate().after(new Date())){
					btnSave.setDisabled(true);
					btnDelete.setDisabled(false);
				}
			}
		}
		else{
			btnDelete.setDisabled(true);
			dtbStartDate.setValue(DateUtil.add(new Date(), Calendar.DATE, 1));
		}
	}
	
	private void populateBranchSize() {
		modelBranchSize = new ListModelList<BranchSizeType>();
		for(BranchSizeType branchSizeType : BranchSizeType.values()) {
			modelBranchSize.add(branchSizeType);
		}
		lstBranchSize.setModel(modelBranchSize);
		lstBranchSize.renderAll();
		modelBranchSize.addToSelection(BranchSizeType.SELECT);
		
		lstBranchSize.setItemRenderer(new SerializableListItemRenderer<BranchSizeType>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, BranchSizeType data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getDesc()));
			}
		});
	}
	
	@Listen("onClick = #btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						clearErrorMessage();
						populateBranch();
						branchSizeValidator.validate(selectedBranchSize);
						branchSizeSetupServiceImpl.save(selectedBranchSize, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						doBack();			
					} catch (ValidationException ve) {
						logger.error(ve.getMessage());
						showErrorMessage(ve.getConstraintViolations());
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(e.getMessage());
					}
				}
			}
		});
	}	
	
	private void populateBranch() {
		selectedBranchSize = new BranchSizeDTO();
		selectedBranchSize.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedBranchSize.setCreationDate(new Date());
		if (bnbOrganization.getKeyValue() != null) {
			OrganizationDTO organization = organizationSetupServiceImpl.findById((Long)bnbOrganization.getKeyValue().getKey(), null, dtbStartDate.getValue() != null ? dtbStartDate.getValue() : new Date());
			selectedBranchSize.setOrganizationName(organization.getName());
			selectedBranchSize.setOrganizationCode(organization.getCode());
			selectedBranchSize.setOrganizationLevel(organization.getLevelCode());
			selectedBranchSize.setOrganizationId(organization.getId());
		}
		selectedBranchSize.setEffectiveOnDate(dtbStartDate.getValue());
		selectedBranchSize.setEffectiveStartDate(dtbStartDate.getValue());
		selectedBranchSize.setEffectiveEndDate(dtbEndDate.getValue());
		selectedBranchSize.setSizeCode(lstBranchSize.getSelectedItem().getValue().toString());
		selectedBranchSize.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedBranchSize.setLastUpdateDate(new Date());
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(dtbEndDate);
		ErrorMessageUtil.clearErrorMessage(lstBranchSize);
		ErrorMessageUtil.clearErrorMessage(bnbOrganization);
		ErrorMessageUtil.clearErrorMessage(dtbStartDate);
	}
	
	private void showErrorMessage(Map<String, String> violations) {
		if(violations.containsKey(BranchSizeValidator.EFFECTIVE_END_DATE))
			ErrorMessageUtil.setErrorMessage(dtbEndDate, violations.get(BranchSizeValidator.EFFECTIVE_END_DATE));
		if(violations.containsKey(BranchSizeValidator.BRANCH_SIZE))
			ErrorMessageUtil.setErrorMessage(lstBranchSize, violations.get(BranchSizeValidator.BRANCH_SIZE));
		if(violations.containsKey(BranchSizeValidator.ORGANIZATION))
			ErrorMessageUtil.setErrorMessage(bnbOrganization, violations.get(BranchSizeValidator.ORGANIZATION));
		if(violations.containsKey(BranchSizeValidator.EFFECTIVE_START_DATE))
			ErrorMessageUtil.setErrorMessage(dtbStartDate, violations.get(BranchSizeValidator.EFFECTIVE_START_DATE));
		if(violations.containsKey(BranchSizeValidator.HISTORY))
			ErrorMessageUtil.setErrorMessage(dtbStartDate, violations.get(BranchSizeValidator.HISTORY));
		
	}
	
	@Listen("onClick = #btnDelete")
	public void onDelete() {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					branchSizeSetupServiceImpl.delete(selectedBranchSize);
					Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
					doBack();
				}
			}
		});
	}
	
	@Listen("onClick = #btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					doBack();
				}
			}
		});
	}
	
	private void doBack() {
		Executions.createComponents("~./hcms/workstructure/branch_size_setup_inquiry.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	private void setDisableComponent(boolean disable){
		dtbStartDate.setDisabled(disable);
		dtbEndDate.setDisabled(disable);
		lstBranchSize.setDisabled(disable);
		btnSave.setDisabled(disable);
		btnDelete.setDisabled(disable);
		bnbOrganization.setDisabled(disable);
	}

}
