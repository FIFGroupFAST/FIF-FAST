package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;

import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.tabularentry.DateboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.systemadmin.domain.JobResponsibility;
import co.id.fifgroup.systemadmin.domain.JobResponsibilityExample;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDTO;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDetailDTO;
import co.id.fifgroup.systemadmin.service.JobResponsibilityService;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;
import co.id.fifgroup.workstructure.dao.JobMapper;

@VariableResolver(DelegatingVariableResolver.class)
public class JobResponsibilityDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(JobResponsibilityDetailComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private JobMapper jobMapper;
	@WireVariable(rewireOnActivate = true)
	private transient ResponsibilityService responsibilityService;
	@WireVariable(rewireOnActivate = true)
	private transient JobResponsibilityService jobResponsibilityService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AuditPerformer auditPerformer;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private CommonPopupBandbox txtJobName;
	@Wire
	private TabularEntry<JobResponsibilityDetailDTO> tbnJobResponsibilityDetail;
	@Wire
	private Label lblError;
	@Wire
	private Button btnAddJobResponsibility;
	@Wire
	private Button btnDeleteJobResponsibility;
	@Wire
	private Button btnSave;
	
	private List<JobResponsibilityDetailDTO> jobResponsibilityDtoList;
	private JobResponsibilityDTO jobResponsibilityDTO;
	private JobResponsibilityDTO selectedMenu;
	private String Status = "";
	private JobResponsibilityDTO previousObject;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadParameter();
		renderJobPopUp();
		buildJobTabular();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			txtJobName.setDisabled(true);
			btnAddJobResponsibility.setDisabled(true);
			btnDeleteJobResponsibility.setDisabled(true);
			btnSave.setDisabled(true);
		}
	}
	
	private void loadParameter() {
		selectedMenu = (JobResponsibilityDTO) arg.get("jobResponsibilityDTO");
		if(selectedMenu != null) {
			jobResponsibilityDTO = selectedMenu;
			Job job = jobMapper.selectByPrimaryKey(selectedMenu.getJobId());
			txtJobName.setValue(job.getJobName());
			txtJobName.setDisabled(true);
			
			JobResponsibility jobResponsibility = new JobResponsibility();
			jobResponsibility.setJobId(selectedMenu.getJobId());
			jobResponsibilityDtoList = jobResponsibilityService.getJobResponsibilityByJobId(jobResponsibility);
			jobResponsibilityDTO.setJobResponsibilityDetailDTO(jobResponsibilityDtoList);
			previousObject = (JobResponsibilityDTO) DeepCopy.copy(jobResponsibilityDTO);
			Status = arg.get("ACTION").toString();
		}
	}

	private void renderJobPopUp() {
		txtJobName.setReadonly(true);
		txtJobName.setTitle("List of Job");
		txtJobName.setSearchText("Job Name");
		txtJobName.setHeaderLabel("Job");
		txtJobName.setSearchDelegate(new SerializableSearchDelegate<Job>() {

			private static final long serialVersionUID = -9008913765332633929L;

			@Override
			public List<Job> findBySearchCriteria(String searchCriteria, int limit, int offset) {		
				JobExample jobExample = new JobExample();
				jobExample.createCriteria().andJobNameLikeInsensitive(searchCriteria);	
				jobExample.setOrderByClause("UPPER(JOB_NAME) ASC");
				return jobMapper.selectByExampleWithRowbounds(jobExample, new RowBounds(offset, limit));
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {			
				JobExample jobExample = new JobExample();
				jobExample.createCriteria().andJobNameLikeInsensitive(searchCriteria);
				return jobMapper.countByExample(jobExample);
			}

			@Override
			public void mapper(KeyValue keyValue, Job data) {
				if (null != data) {
					CompanyDTO company = companyServiceImpl.findCompanyById(data.getCompanyId(), new Date());
					keyValue.setKey(data.getId());
					keyValue.setValue(data.getJobName());
					String companyName = ((null != company) ? company.getCompanyName() : "");
					keyValue.setDescription(data.getJobName() + " (" + companyName + ")");
				}
			}
		});
	}

	private void buildJobTabular() {
		tbnJobResponsibilityDetail.setDataType(JobResponsibilityDetailDTO.class);
		tbnJobResponsibilityDetail.setModel(getJobResponsibilityModel());
		tbnJobResponsibilityDetail.setItemRenderer(getJobResponsibilityRenderer());
		tbnJobResponsibilityDetail.setValidationRule(JobResponsibilityValidationRule());
	}
	
	private ListModelList<JobResponsibilityDetailDTO> getJobResponsibilityModel() {
		if(jobResponsibilityDtoList == null) {
			jobResponsibilityDtoList = new ArrayList<JobResponsibilityDetailDTO>();
			jobResponsibilityDtoList.add(new JobResponsibilityDetailDTO());
		}
		
		ListModelList<JobResponsibilityDetailDTO> model = new ListModelList<JobResponsibilityDetailDTO>(jobResponsibilityDtoList);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<JobResponsibilityDetailDTO> getJobResponsibilityRenderer() {
		return new SerializableListItemRenderer<JobResponsibilityDetailDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final JobResponsibilityDetailDTO data, int index) throws Exception {		
				new Listcell("").setParent(item);
											
				Listcell typeCellResponsibilityName = new Listcell();
				CommonPopupBandbox commonPopupBandboxResponsibilityName = new CommonPopupBandbox();
				commonPopupBandboxResponsibilityName.setWidth("75%");
				commonPopupBandboxResponsibilityName.setTitle("List of Responsibility");
				commonPopupBandboxResponsibilityName.setSearchText("Responsibility Name");
				commonPopupBandboxResponsibilityName.setHeaderLabel("Responsibility");
				commonPopupBandboxResponsibilityName.setSearchDelegate(new SerializableSearchDelegate<Responsibility>() {

					private static final long serialVersionUID = 1L;
	
					@Override
					public List<Responsibility> findBySearchCriteria(String searchCriteria, int limit, int offset) {
						ResponsibilityExample example = new ResponsibilityExample();
						example.createCriteria().andResponsibilityNameLikeInsensitive(searchCriteria);
						example.setOrderByClause("UPPER(RESPONSIBILITY_NAME) ASC");
						return responsibilityService.selectByExampleWithRowbounds(example, limit, offset);
					}
	
					@Override
					public int countBySearchCriteria(String searchCriteria) {
						ResponsibilityExample example = new ResponsibilityExample();
						example.createCriteria().andResponsibilityNameLikeInsensitive(searchCriteria);
						return responsibilityService.countResponsibilityByExample(example);
					}
	
					@Override
					public void mapper(KeyValue keyValue, Responsibility data) {
						keyValue.setKey(data.getId());
						keyValue.setValue(data.getResponsibilityName());
						keyValue.setDescription(data.getResponsibilityName());
					}
				});
				
				commonPopupBandboxResponsibilityName.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;
	
					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						if(keyValue != null) {
							data.setResponsibilityId((long) keyValue.getKey());
							data.setResponsibilityName(keyValue.getValue().toString());
						} else {
							data.setResponsibilityId(null);
							data.setResponsibilityName(null);
						}
					}
				});

				KeyValue setKeyValueResponsibilityName = new KeyValue(data.getResponsibilityId(), data.getResponsibilityName(), data.getResponsibilityName());
				commonPopupBandboxResponsibilityName.setRawValue(setKeyValueResponsibilityName);
				commonPopupBandboxResponsibilityName.setReadonly(true);
				typeCellResponsibilityName.appendChild(commonPopupBandboxResponsibilityName);
				typeCellResponsibilityName.setParent(item);
				if(data.getResponsibilityId() != null)
					commonPopupBandboxResponsibilityName.setDisabled(true);
				if(data.getResponsibilityId() != null)
					new DateboxListcell<JobResponsibilityDetailDTO>(data, data.getDateFrom(), "dateFrom", true).setParent(item);
				else
					new DateboxListcell<JobResponsibilityDetailDTO>(data, data.getDateFrom(), "dateFrom", false).setParent(item);
				new DateboxListcell<JobResponsibilityDetailDTO>(data, data.getDateTo(), "dateTo", false).setParent(item);
				
				item.setValue(data);
			}
		};
	}
	
	private TabularValidationRule<JobResponsibilityDetailDTO> JobResponsibilityValidationRule() {
		return new TabularValidationRule<JobResponsibilityDetailDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(JobResponsibilityDetailDTO data, List<String> errorRow) {
				if(data.getResponsibilityName() == null)
					errorRow.add("Please fill Responsibility Name");
				if(data.getDateFrom() == null)
					errorRow.add("Please fill Date From");
				if(data.getDateTo() != null) {
					if(DateUtil.compareDate(data.getDateFrom(), data.getDateTo())) {
						errorRow.add("Date To less than Date From");
					}
				}
				
				if(errorRow.size() > 0)	{
					return false;
				}
				return true;
			}			
		};
	}
	
	private boolean validate() {
		if(txtJobName.getValue().equalsIgnoreCase("")) {
			return false;
		}				
		return true;
	}
	
	private void validation() {
		ErrorMessageUtil.clearErrorMessage(txtJobName);
		if(txtJobName.getValue().equalsIgnoreCase("")) {
			ErrorMessageUtil.setErrorMessage(txtJobName, "Please fill Job name");
		}
	}
	
	private boolean validationTabular() {
		int i, j;
		if(tbnJobResponsibilityDetail.getItemCount() <= 0) {
			lblError.setValue("Responsibility name must selected minimal 1 item");					
			return false;
		}
		
		for(i = 0; i < tbnJobResponsibilityDetail.getItemCount(); i++) {
			for(j = 0; j < tbnJobResponsibilityDetail.getItemCount(); j++) {
				if(i == j) {
					continue;
				}
				if(tbnJobResponsibilityDetail.getValue() != null && tbnJobResponsibilityDetail.getValue().get(i).getResponsibilityName() != null && tbnJobResponsibilityDetail.getValue().get(j).getResponsibilityName() != null){
					if(tbnJobResponsibilityDetail.getValue().get(i).getResponsibilityName().trim().equalsIgnoreCase(tbnJobResponsibilityDetail.getValue().get(j).getResponsibilityName().trim())) {
						lblError.setValue("Responsibility name can't double at row " + (i+1) + " and row " + (j+1));					
						return false;
					}
				}
				
			}
		}
		return true;
	}
/*	
	private void showErrorMessage(ValidationException e) {	
		ErrorMessageUtil.setErrorMessage(txtJobName, e.getMessage(JobResponsibilityValidator.JOB_NAME_NOT_EMPTY));
	}
	
	private void clearErrorMessage(){
		ErrorMessageUtil.clearErrorMessage(txtJobName);
	}*/
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		ErrorMessageUtil.clearErrorMessage(txtJobName);
		lblError.setValue("");
		
		
			final Control control;
			if(jobResponsibilityDTO == null) {
				jobResponsibilityDTO = new JobResponsibilityDTO();
				control = Control.CREATE;
			}else
				control = Control.UPDATE;
			
			KeyValue keyValueMenu = txtJobName.getKeyValue();
			if(keyValueMenu != null) {
				jobResponsibilityDTO.setJobId((long) keyValueMenu.getKey());
			}
			
			jobResponsibilityDtoList = tbnJobResponsibilityDetail.getValue();
			jobResponsibilityDTO.setJobResponsibilityDetailDTO(jobResponsibilityDtoList);
			if(selectedMenu == null) {
				jobResponsibilityDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				jobResponsibilityDTO.setCreationDate(new Date());
			} else {
				jobResponsibilityDTO.setCreatedBy(selectedMenu.getCreatedBy());
				jobResponsibilityDTO.setCreationDate(selectedMenu.getCreationDate());
			}
			
			jobResponsibilityDTO.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			jobResponsibilityDTO.setLastUpdateDate(new Date());
			try {
				
				Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {	
						int status = (int) event.getData();
						if(status == 16) {
								if(validateJobResponsibility()){
									
									if(Status.equals("EDIT")) {
										jobResponsibilityService.deleteEdit(jobResponsibilityDTO);
									}
									
									jobResponsibilityService.save(jobResponsibilityDTO);
									auditPerformer.doAudit(UUID.randomUUID(), UUID.randomUUID(), previousObject, jobResponsibilityDTO, 1, control, Action.SUBMIT, securityServiceImpl.getSecurityProfile().getUserId(), TrxType.JOB_RESPONSIBILITY.getValue(), jobResponsibilityDTO.getJobId());
									Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
									Map<String, Object> param = new HashMap<String, Object>();
									param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
									Executions.createComponents("~./hcms/systemadmin/job_responsibility_inquiry.zul", getSelf().getParent(), param);
									getSelf().detach();
								}
						} else {
							return;
						}
					}
				});
			} catch (Exception e) {
				log.error("error", e);
			}

	}
	
	private boolean validateJobResponsibility(){
		boolean status = true;
		if(!validate()) {
			validation();
			status = false;
		}
		
		JobResponsibilityExample jobResponsibilityExample = new JobResponsibilityExample();
		if(jobResponsibilityDTO.getJobId() != null) {
			jobResponsibilityExample.createCriteria().andJobIdEqualTo(jobResponsibilityDTO.getJobId());			
			int count = jobResponsibilityService.countByExample(jobResponsibilityExample);
			
			if(count > 0 && !Status.equals("EDIT")) {
				ErrorMessageUtil.setErrorMessage(txtJobName, "Job Name is already existed");
				status = false;
			}
		}
		
		if(!tbnJobResponsibilityDetail.validate()){
			status = false;
			tbnJobResponsibilityDetail.clearSelection();
		}
		if(!validationTabular()) {
			status = false;
		}
		return status;
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("sam.cancelMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/systemadmin/job_responsibility_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}

	@Listen("onClick=#btnAddJobResponsibility")
	public void addRow() {
		tbnJobResponsibilityDetail.addRow();
	}
	
	@Listen("onClick=#btnDeleteJobResponsibility")
	public void deleteRow() {
		lblError.setValue("");
		tbnJobResponsibilityDetail.clearErrorMessage();
		Set<Listitem> itemToBeDeleted = tbnJobResponsibilityDetail.getSelectedItems();
		boolean hasError = false;
		ListModelList<Object> model = (ListModelList<Object>)tbnJobResponsibilityDetail.getModel();
		
		for (Listitem listitem : itemToBeDeleted) {
			JobResponsibilityDetailDTO jobResponsibilityDetailDTO = listitem.getValue();
			if (jobResponsibilityDetailDTO.getId() != null) {
				listitem.setSelected(false);
				model.removeFromSelection(listitem.getValue());
				lblError.setValue(Labels.getLabel("sam.existingDataCannotBeDeletd"));
				hasError = true;
				break;
			}
		}
		
		
		
		if(!hasError)
			tbnJobResponsibilityDetail.deleteRow();
	}
}
