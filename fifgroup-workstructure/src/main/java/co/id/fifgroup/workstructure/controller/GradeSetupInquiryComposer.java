package co.id.fifgroup.workstructure.controller;

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
import co.id.fifgroup.workstructure.service.GradeSetupService;
import com.google.common.base.Strings;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.GradeDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class GradeSetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(GradeSetupInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	private GradeDTO example;
	private final String grade = "grade";
	
	@Wire private CommonPopupDoubleBandbox bnbGrade;
	@Wire private Datebox dtbEffectiveOnDate;
	@Wire private Listbox lstGrade;
	
	@WireVariable(rewireOnActivate=true)
	private transient GradeSetupService gradeSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
		
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		lstGrade.setMold("paging");
		lstGrade.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setValue(new Date());
		populateGrade();
	}
	
	@Listen("onDownloadUserManual = #winGradeSetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void populateGrade() {
		bnbGrade.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<GradeDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<GradeDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				GradeDTO example = new GradeDTO();
				example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setCode(searchCriteria1);
				example.setName(searchCriteria2);
				return gradeSetupServiceImpl.findActiveGradeForLov(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				GradeDTO example = new GradeDTO();
				example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setCode(searchCriteria1);
				example.setName(searchCriteria2);
				return gradeSetupServiceImpl.countActiveGradeForLov(example);
			}
			
			@Override
			public void mapper(KeyValue keyValue, GradeDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getCode());
				keyValue.setDescription(data.getGrade() + " - " + data.getSubGrade());
			}
		});
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/workstructure/grade_setup.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onClick = #btnFind")
	public void findGrade() {
		String code = new String();
		example = new GradeDTO();
		example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(!Strings.isNullOrEmpty(bnbGrade.getValue())) {
			code = bnbGrade.getKeyValue().getDescription().toString();
		}
		
		example.setGrade(bnbGrade.getKeyValue() != null? code.substring(0,code.indexOf(" ")) : null);
		example.setSubGrade(bnbGrade.getKeyValue() != null? code.substring(code.indexOf("-")+2, code.length()) : null);
//		example.setName(bnbGrade.getKeyValue() != null ? bnbGrade.getKeyValue().getDescription().toString() : null);//pisah jadi 2
		example.setEffectiveOnDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtil.truncate(new Date()));
		
		if(example.getName() == null && example.getEffectiveOnDate() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						List<GradeDTO> result = gradeSetupServiceImpl.findByInquiry(example);
						populateGradeDto(result);
					}
				}				
			});
		} else {
			List<GradeDTO> result = gradeSetupServiceImpl.findByInquiry(example);
			populateGradeDto(result);
		}
	}
	
	protected void populateGradeDto(List<GradeDTO> result) {
		ListModelList<GradeDTO> model = new ListModelList<>(result);
		lstGrade.setModel(model);
	}
	
	@Listen("onGradeDetail = #winGradeSetupInquiry")
	public void onGradeDetail(ForwardEvent event) {
		GradeDTO detail = (GradeDTO) event.getData();
		Map<String, Object> params = new HashMap<>();
		params.put(GradeDTO.class.getName(), detail);
		Executions.createComponents("~./hcms/workstructure/grade_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnUpload")
	public void onUpload() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(grade, true);
		Executions.createComponents("~./hcms/workstructure/upload_grade.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
}
