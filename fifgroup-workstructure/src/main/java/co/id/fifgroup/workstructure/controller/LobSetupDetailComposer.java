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
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.DecimalboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.LobDTO;
import co.id.fifgroup.workstructure.dto.LobElementDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.validation.LobValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class LobSetupDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(LobSetupDetailComposer.class);
	private LobDTO selectedLob;
	private List<LobElementDTO> lobElements;
	private Date dateFromBeforeCurrentEdit = null;
	private Date dateToBeforeCurrentEdit = null;
	private final String productLookup = "WSU_PRODUCT";
	private ListModelList<LobElementDTO> model;
	
	@WireVariable(rewireOnActivate=true)
	private transient LobSetupService lobSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable("arg")
	private transient Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient JobSetupService jobSetupServiceImpl;
	
	@Wire
	private CommonPopupDoubleBandbox bnbOrganization;
	@Wire
	private CommonPopupDoubleBandbox bnbJob;
	@Wire
	private Datebox dtbStartDate;
	@Wire
	private DateBoxMax dtbEndDate;
	@Wire
	private TabularEntry<LobElementDTO> lstLob;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnAddRowLob;
	@Wire
	private Button btnDeleteLob;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		buildTabularLob();
		populateOrganization();
		populateJobName();
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
	}
	
	private void populateJobName() {
		bnbJob.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<JobDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<JobDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				
				JobDTO example = new JobDTO();
				example.setJobCode(searchCriteria1);
				example.setJobName(searchCriteria2);
				example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setEffectiveDate(DateUtils.truncate(new Date(), Calendar.DATE));
				return jobSetupServiceImpl.findByExample(example);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				JobDTO example = new JobDTO();
				example.setJobCode(searchCriteria1);
				example.setJobName(searchCriteria2);
				example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setEffectiveDate(DateUtils.truncate(new Date(), Calendar.DATE));
				return jobSetupServiceImpl.countByExample(example);
			}
			
			@Override
			public void mapper(KeyValue keyValue, JobDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getJobCode());
				keyValue.setDescription(data.getJobName());
			}
		});
	}
	
	private void readParameter() {
		selectedLob = (LobDTO) arg.get(LobDTO.class.getName());
		
		if(selectedLob != null) {
			if(selectedLob.getId() != null)
				selectedLob = lobSetupServiceImpl.findById(selectedLob.getId());
			dtbStartDate.setDisabled(true);
			dtbStartDate.setValue(selectedLob.getEffectiveStartDate());
			dtbEndDate.setValue(selectedLob.getEffectiveEndDate());
			OrganizationDTO organization = organizationSetupServiceImpl.findById(selectedLob.getOrganizationId(), null, selectedLob.getEffectiveStartDate());
			bnbOrganization.setValue(organization.getCode() + " - " + organization.getName());
			/*JobDTO job = jobSetupServiceImpl.findById(selectedLob.getJobId(), selectedLob.getEffectiveStartDate());*/
			
			Job job = jobSetupServiceImpl.findByPrimaryKey(selectedLob.getJobId());
			
			bnbJob.setValue(job.getJobCode() + " - " + job.getJobName());
			
			if(selectedLob.getLobElements() != null) {
				lobElements = selectedLob.getLobElements();
				lstLob.setModel(getModel());
			}
			
			if(selectedLob.getId() != null) {
				dateFromBeforeCurrentEdit = selectedLob.getEffectiveStartDate();
				dateToBeforeCurrentEdit = selectedLob.getEffectiveEndDate();
				setDisableComponent(true);
				lstLob.setDisabled(true);
				btnSave.setDisabled(true);
				if (selectedLob.getEffectiveStartDate().compareTo(DateUtil.truncate(new Date())) <= 0){
					btnDelete.setDisabled(true);
				} else if(selectedLob.getEffectiveStartDate().after(DateUtil.truncate(new Date()))){
					btnDelete.setDisabled(false);
				}
			}
		}else{
			btnDelete.setDisabled(true);
			dtbStartDate.setValue(DateUtil.add(new Date(), Calendar.DATE, 1));
		}
	}
	
	private void setDisableComponent(boolean disable) {
		bnbJob.setDisabled(disable);
		bnbOrganization.setDisabled(disable);
		btnDelete.setDisabled(disable);
		btnSave.setDisabled(disable);
		dtbEndDate.setDisabled(disable);
		dtbStartDate.setDisabled(disable);
		btnAddRowLob.setDisabled(disable);
		btnDeleteLob.setDisabled(disable);
	}

	private void buildTabularLob() {
		lstLob.setDataType(LobElementDTO.class);
		lstLob.setModel(getModel());
		lstLob.setItemRenderer(getListItemRenderer());
		lstLob.setValidationRule(getValidationRule());
	}
	
	private ListModelList<LobElementDTO> getModel() {
		if(lobElements == null)
			lobElements = new ArrayList<LobElementDTO>();
		
		model = new ListModelList<LobElementDTO>(lobElements);
		model.setMultiple(true);
		logger.info(model.toString());
		return model;
	}
	
	private SerializableListItemRenderer<LobElementDTO> getListItemRenderer() {
		return new SerializableListItemRenderer<LobElementDTO>() {

			@Override
			public void render(Listitem item, final LobElementDTO data, int index) throws Exception {
				new Listcell().setParent(item);
				
				Listcell listcellProduct = new Listcell();
				final LookupWindow bnbProduct = new LookupWindow();
				bnbProduct.setWidth("300px");
				bnbProduct.setTitle("List of Product");
				bnbProduct.setHeaderLabel("Product");
				bnbProduct.setSearchText("Product");
				bnbProduct.setLookupCriteria(new LookupCriteria() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public String getParentDetailCode() {
						return null;
					}
					
					@Override
					public String getLookupName() {
						return productLookup;
					}
				});
				bnbProduct.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -5788144121529892249L;

					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						if (keyValue != null) {
							bnbProduct.setValueByCode(productLookup, (String) keyValue.getKey());
							data.setProductCode((String) keyValue.getKey());
							data.setProduct((String) keyValue.getDescription());
						} else {
							data.setProductCode(null);
							data.setProduct(null);
						}
					}
				});
				listcellProduct.appendChild(bnbProduct);
				listcellProduct.setParent(item);
				bnbProduct.setValueByCode(data.getProductCode());
				
				DecimalboxListcell<LobElementDTO> percentCell = new DecimalboxListcell<LobElementDTO>(data, data.getPercentage(), "percentage");
				percentCell.getComponent().setFormat("###.##");
				percentCell.setParent(item);
				percentCell.setValue(data.getPercentage());
			}
		};
	}
	
	private TabularValidationRule<LobElementDTO> getValidationRule() {
		return new TabularValidationRule<LobElementDTO>() {
			@Override
			public boolean validate(LobElementDTO data, List<String> errorRow) {
				if(data.getProductCode() == null || data.getProductCode().equals(""))
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.product") }));
				if(data.getPercentage() == null )
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.percentage") }));
					
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
	
	@Listen("onClick = #btnAddRowLob")
	public void addRowProduct() {
		lstLob.addRow();
	}
	
	@Listen("onClick = #btnDeleteLob")
	public void deleteRowProduct() {
		lstLob.deleteRow();
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
					if(selectedLob == null)
						selectedLob = new LobDTO();
					selectedLob.setValidLobElements(lstLob.validate());
					selectedLob.setEffectiveEndDate(dtbEndDate.getValue());
					selectedLob.setEffectiveStartDate(dtbStartDate.getValue());
					selectedLob.setEffectiveEndDate(dtbEndDate.getValue());
					selectedLob.setOrganizationId(bnbOrganization.getKeyValue() != null ? (Long)bnbOrganization.getKeyValue().getKey() : null);
					selectedLob.setJobId(bnbJob.getKeyValue() != null ? (Long)bnbJob.getKeyValue().getKey() : null);
					selectedLob.setLobElements(lstLob.getValue());
					selectedLob.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
					selectedLob.setCreationDate(new Date());
					selectedLob.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
					selectedLob.setLastUpdateDate(new Date());
					lobSetupServiceImpl.validate(selectedLob);
					List<Long> histories = lobSetupServiceImpl.findHistoriesById(selectedLob.getJobId(), selectedLob.getOrganizationId());
					if (histories.size() > 0) {
						Long lastHistoryId = histories.get(histories.size()-1);
						LobDTO lastLob = lobSetupServiceImpl.findByIdAndHistoryId(lastHistoryId, selectedLob.getJobId(), selectedLob.getOrganizationId());
						if(lastLob != null) {
							dateFromBeforeCurrentEdit = lastLob.getEffectiveStartDate();
							dateToBeforeCurrentEdit = lastLob.getEffectiveEndDate();
							selectedLob.setId(lastLob.getId());
						}
					}
					lobSetupServiceImpl.save(selectedLob, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
					Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
					doBack();
				} catch (ValidationException ve) {
					showErrorMessage(ve.getConstraintViolations());
					logger.error(ve.getAllMessages());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					Messagebox.show(e.getMessage());
				}
			}					
			}			
		});
	}	
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(dtbStartDate);
		ErrorMessageUtil.clearErrorMessage(dtbEndDate);
		ErrorMessageUtil.clearErrorMessage(bnbOrganization);
		ErrorMessageUtil.clearErrorMessage(bnbJob);
		if(lstLob.getPreviousSibling() instanceof Hlayout) 
			lstLob.getParent().removeChild(lstLob.getPreviousSibling());
	}
	
	private void showErrorMessage(Map<String, String> violations) {
		if(violations.containsKey(LobValidator.EFFECTIVE_START_DATE))
			ErrorMessageUtil.setErrorMessage(dtbStartDate, violations.get(LobValidator.EFFECTIVE_START_DATE));
		if(violations.containsKey(LobValidator.EFFECTIVE_END_DATE))
			ErrorMessageUtil.setErrorMessage(dtbEndDate, violations.get(LobValidator.EFFECTIVE_END_DATE));
		if(violations.containsKey(LobValidator.PRODUCT_CODE)) {
			Label lblErrorMsg = new Label(violations.get(LobValidator.PRODUCT_CODE));
			lblErrorMsg.setStyle("color:red");
			Hlayout hlayout = new Hlayout();
			hlayout.appendChild(lblErrorMsg);
			hlayout.appendChild(new Separator());
			lstLob.getParent().insertBefore(hlayout, lstLob);
		}
		if(violations.containsKey(LobValidator.PERCENTAGE)) {
			Label lblErrorMsg = new Label(violations.get(LobValidator.PERCENTAGE));
			lblErrorMsg.setStyle("color:red");
			Hlayout hlayout = new Hlayout();
			hlayout.appendChild(lblErrorMsg);
			hlayout.appendChild(new Separator());
			lstLob.getParent().insertBefore(hlayout, lstLob);
		}
		if(violations.containsKey(LobValidator.ORGANIZATION))
			ErrorMessageUtil.setErrorMessage(bnbOrganization, violations.get(LobValidator.ORGANIZATION));
		if(violations.containsKey(LobValidator.JOB))
			ErrorMessageUtil.setErrorMessage(bnbJob, violations.get(LobValidator.JOB));
		if(violations.containsKey(LobValidator.HISTORY))
			ErrorMessageUtil.setErrorMessage(dtbEndDate, violations.get(LobValidator.HISTORY));
	}
	
	@Listen("onClick = #btnDelete")
	public void onDelete() {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					lobSetupServiceImpl.delete(selectedLob);
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
		Executions.createComponents("~./hcms/workstructure/lob_setup_inquiry.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
}
