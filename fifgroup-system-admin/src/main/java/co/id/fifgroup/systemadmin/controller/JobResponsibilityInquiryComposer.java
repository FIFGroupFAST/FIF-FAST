package co.id.fifgroup.systemadmin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;

import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.systemadmin.domain.JobResponsibility;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.JobResponsibilityService;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;
import co.id.fifgroup.workstructure.dao.JobMapper;

@VariableResolver(DelegatingVariableResolver.class)
public class JobResponsibilityInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(JobResponsibilityInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	
	@WireVariable(rewireOnActivate = true)
	private transient JobResponsibilityService jobResponsibilityService;
	@WireVariable(rewireOnActivate = true)
	private transient ResponsibilityService responsibilityService;
	@WireVariable(rewireOnActivate = true)
	private transient JobMapper jobMapper;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	
	@Wire
	private CommonPopupBandbox txtJobName;
	@Wire
	private CommonPopupBandbox txtResponsibilityName;
	@Wire
	private Listbox lstJobResponsibility;
	@Wire
	private Paging pgJobResponsibility;
	@Wire
	private Paging pgJobResponsibilityTop;
	@Wire
	private Button btnSetResp;
		
	private ListModelList<JobResponsibilityDTO> listModelResponsibilityDto;
	private List<JobResponsibilityDTO> listJobResponsibilityDTO;
	private int totalSize;
	private JobResponsibilityDTO jobResponsibilityDTO;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		renderJobPopUp();
		renderResponsibilityPopUp();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnSetResp.setDisabled(true);
	}
	
	@Listen("onDownloadUserManual = #winJobRespInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void init() {
		listModelResponsibilityDto = new ListModelList<JobResponsibilityDTO>();
		lstJobResponsibility.setModel(listModelResponsibilityDto);
	}
	
	private void renderJobPopUp() {
		txtJobName.setTitle("List of Job");
		txtJobName.setSearchText("Job Name");
		txtJobName.setHeaderLabel("Job");
		txtJobName.setReadonly(true);
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
	
	private void renderResponsibilityPopUp() {
		txtResponsibilityName.setReadonly(true);
		txtResponsibilityName.setTitle("List of Responsibility");
		txtResponsibilityName.setSearchText("Responsibility Name");
		txtResponsibilityName.setHeaderLabel("Responsibility");
		txtResponsibilityName.setSearchDelegate(new SerializableSearchDelegate<Responsibility>() {

			/**
			 * 
			 */
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
	}
	
	@Listen ("onClick = button#btnFind")
	public void find() {
		if(txtJobName.getKeyValue() == null && txtResponsibilityName.getKeyValue() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = -8756250972566999901L;
				
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
		try {
//			KeyValue keyValueJob = txtJobName.getKeyValue();
//			KeyValue keyValueResponsibility = txtResponsibilityName.getKeyValue();
//			
//			JobResponsibility jobResponsibility = new JobResponsibility();
//			if(keyValueJob != null) {
//				jobResponsibility.setJobId((long) keyValueJob.getKey());
//			}
//			
//			if(keyValueResponsibility != null) {
//				jobResponsibility.setResponsibilityId((long) keyValueResponsibility.getKey());
//			}
//			totalSize = jobResponsibilityService.countJobResponsibilityByJobIdAndResponsibilityId(jobResponsibility);
//			pgJobResponsibility.setTotalSize(totalSize);
//			pgJobResponsibility.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgJobResponsibility.setActivePage(0);
//			pgJobResponsibilityTop.setTotalSize(totalSize);
//			pgJobResponsibilityTop.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgJobResponsibilityTop.setActivePage(0);
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
		
	@Listen ("onClick = button#btnSetResp")
	public void setResp() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/job_responsibility_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen("onPaging = #winJobRespInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}
	
	private void generateDataAndPaging(ForwardEvent evt) {
//		if (evt != null && evt.getOrigin().getTarget().getId().equals("pgJobResponsibility")) {
//			pgJobResponsibilityTop.setActivePage(pgJobResponsibility.getActivePage());
//		} else {
//			pgJobResponsibility.setActivePage(pgJobResponsibilityTop.getActivePage());
//		}
		KeyValue keyValueJob = txtJobName.getKeyValue();
		KeyValue keyValueResponsibility = txtResponsibilityName.getKeyValue();
		
		JobResponsibility jobResponsibility = new JobResponsibility();
		if(keyValueJob != null) {
			jobResponsibility.setJobId((long) keyValueJob.getKey());
		}
		
		if(keyValueResponsibility != null) {
			jobResponsibility.setResponsibilityId((long) keyValueResponsibility.getKey());
		}
		listJobResponsibilityDTO = jobResponsibilityService.getJobResponsibilityByJobIdAndResponsibilityId(jobResponsibility, Integer.MAX_VALUE, 0);
		listModelResponsibilityDto.clear();
		listModelResponsibilityDto.addAll(listJobResponsibilityDTO);
	}
	
	@Listen("onDetail= #winJobRespInquiry")
	public void onDetail(ForwardEvent event) {
		jobResponsibilityDTO = (JobResponsibilityDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("jobResponsibilityDTO", jobResponsibilityDTO);
		param.put("ACTION", "EDIT");
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/job_responsibility_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
}
