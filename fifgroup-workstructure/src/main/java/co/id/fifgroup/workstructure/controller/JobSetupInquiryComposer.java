package co.id.fifgroup.workstructure.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.constant.JobFor;
import co.id.fifgroup.workstructure.constant.JobType;
import co.id.fifgroup.workstructure.constant.KeyJobType;
import co.id.fifgroup.workstructure.constant.PeopleCategory;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;
import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.JobDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class JobSetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(JobSetupInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;

	@WireVariable(rewireOnActivate=true)
	private transient JobSetupService jobSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private JobDTO selectedJob;
	private ListModelList<KeyJobType> modelKeyJob;
	private ListModelList<JobFor> modelJobFor;
	private ListModelList<JobType> modelJobType;
	private ListModelList<PeopleCategory> modelPeopleCategory;
	private final String job = "job";
	
	@Wire
	private Listbox lstJob;
	@Wire
	private CommonPopupDoubleBandbox bnbJobName;
	@Wire
	private Listbox lstJobFor;
	@Wire
	private Listbox lstJobType;
	@Wire
	private Listbox lstPeopleCategory;
	@Wire
	private Listbox lstKeyJob;
	@Wire
	private Datebox dtbEffectiveOnDate;	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		lstJob.setMold("paging");
		lstJob.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setValue(new Date()); 
		init();
	}
	
	@Listen("onDownloadUserManual = #winJobSetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void init() {
		populateKeyJob();
		populateJobName();
		populateJobFor();
		populateJobTypeLookup();
		populatePeopleCategoryLookup();
	}
	
	private void populateKeyJob() {
		List<KeyJobType> keyJob = new ArrayList<>();
		keyJob.addAll(Arrays.asList(KeyJobType.values()));

		modelKeyJob = new ListModelList<KeyJobType>(keyJob);
		lstKeyJob.setModel(modelKeyJob);
		lstKeyJob.renderAll();
		modelKeyJob.addToSelection(KeyJobType.SELECT);
	}
	
	private void populateJobName() {
		bnbJobName.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Job>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Job> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				JobExample example = new JobExample();
				example.createCriteria().andJobCodeLikeInsensitive(searchCriteria1)
					.andJobNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return jobSetupServiceImpl.findByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				JobExample example = new JobExample();
				example.createCriteria().andJobCodeLikeInsensitive(searchCriteria1)
					.andJobNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return jobSetupServiceImpl.countByExample(example);
			}
			
			@Override
			public void mapper(KeyValue keyValue, Job data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getJobCode());
				keyValue.setDescription(data.getJobName());
			}
		});
	}
	
	private void populateJobFor() {
		List<JobFor> jobFor = new ArrayList<>();
		jobFor.addAll(Arrays.asList(JobFor.values()));

		modelJobFor = new ListModelList<JobFor>(jobFor);
		lstJobFor.setModel(modelJobFor);
		lstJobFor.renderAll();
		modelJobFor.addToSelection(JobFor.SELECT);
	}
	
	private void populateJobTypeLookup() {
		List<JobType> jobType = new ArrayList<>();
		jobType.addAll(Arrays.asList(JobType.values()));

		modelJobType = new ListModelList<JobType>(jobType);
		lstJobType.setModel(modelJobType);
		lstJobType.renderAll();
		modelJobType.addToSelection(JobType.SELECT);
	}
	
	private void populatePeopleCategoryLookup() {
		List<PeopleCategory> peopleCategories = new ArrayList<>();
		peopleCategories.addAll(Arrays.asList(PeopleCategory.values()));

		modelPeopleCategory = new ListModelList<PeopleCategory>(peopleCategories);
		lstPeopleCategory.setModel(modelPeopleCategory);
		lstPeopleCategory.renderAll();
		modelPeopleCategory.addToSelection(PeopleCategory.SELECT);
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		populateJob();		
		if(selectedJob.getJobName() == null
				&& selectedJob.getJobForCode() == null
				&& selectedJob.getJobTypeCode() == null
				&& selectedJob.getPeopleCategoryCode() == null
				&& selectedJob.getIsKeyJob() == null
				&& selectedJob.getEffectiveDate() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}				
			});
		} else {
			search();
		}
	}
	
	private void search() {
		List<JobDTO> jobs = jobSetupServiceImpl.findByInquiry(selectedJob);
		ListModelList<JobDTO> model = new ListModelList<>(jobs);
		lstJob.setModel(model);
	}
	
	private void populateJob() {
		selectedJob = new JobDTO();
		selectedJob.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		selectedJob.setJobName(bnbJobName.getKeyValue() != null ? (String)bnbJobName.getKeyValue().getDescription() : null);
		
		JobFor jobFor = lstJobFor.getSelectedItem().getValue();
		if(!jobFor.getDesc().equals(JobFor.SELECT.getDesc())) {
			selectedJob.setJobForCode(jobFor.toString());
			selectedJob.setJobFor(jobFor.getDesc());
		}
		JobType jobType = lstJobType.getSelectedItem().getValue();
		if(!jobType.getDesc().equals(JobType.SELECT.getDesc())) {
			selectedJob.setJobTypeCode(jobType.toString());
			selectedJob.setJobType(jobType.getDesc());
		}
		PeopleCategory pc = lstPeopleCategory.getSelectedItem().getValue();
		if(!pc.getDesc().equals(PeopleCategory.SELECT.getDesc())) {
			selectedJob.setPeopleCategoryCode(pc.toString());
			selectedJob.setPeopleCategory(pc.getDesc());
		}
		KeyJobType keyJob = lstKeyJob.getSelectedItem().getValue();
		if(!keyJob.getDesc().equals(KeyJobType.SELECT.getDesc())) {
			if(keyJob.getDesc().equals(KeyJobType.KEY_JOB.getDesc()))
				selectedJob.setIsKeyJob(true);
			else
				selectedJob.setIsKeyJob(false);
		}
		selectedJob.setEffectiveDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtil.truncate(new Date()));
	}
	
	@Listen("onClick = button#btnUpload")
	public void onUpload() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(job, true);
		Executions.createComponents("~./hcms/workstructure/upload_job.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		//start add by jts. itsm no 15102316505315 - HCMS ph 2 -COM-Setup Competency of job tdk bs save
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY));
		//end add by jts. itsm no 15102316505315 - HCMS ph 2 -COM-Setup Competency of job tdk bs save
		Executions.createComponents("~./hcms/workstructure/job_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winJobSetupInquiry")
	public void onDetail(ForwardEvent event){
		selectedJob = (JobDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JobDTO.class.getName(), selectedJob);
		//start add by jts. itsm no 15102316505315 - HCMS ph 2 -COM-Setup Competency of job tdk bs save
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY));
		//end add by jts. itsm no 15102316505315 - HCMS ph 2 -COM-Setup Competency of job tdk bs save
		Executions.createComponents("~./hcms/workstructure/job_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onCompetency= #winJobSetupInquiry")
	public void onCompetency(ForwardEvent event){
		selectedJob = (JobDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JobDTO.class.getName(), selectedJob);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY));
		Executions.createComponents("~./hcms/competency/competency_job.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
}
