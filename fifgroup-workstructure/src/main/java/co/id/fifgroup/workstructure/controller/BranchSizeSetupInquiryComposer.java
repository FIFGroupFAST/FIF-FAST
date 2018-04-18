package co.id.fifgroup.workstructure.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
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

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.constant.BranchSizeType;
import co.id.fifgroup.workstructure.service.BranchSizeSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import co.id.fifgroup.workstructure.ui.CommonBranchBandbox;
import co.id.fifgroup.workstructure.ui.CommonOrganizationBandbox;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dto.BranchSizeDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.finder.BranchSizeFinder;

@VariableResolver(DelegatingVariableResolver.class)
public class BranchSizeSetupInquiryComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(BranchSizeSetupInquiryComposer.class);
	
	@Wire
	private CommonBranchBandbox bnbBranch;
	@Wire
	private CommonOrganizationBandbox bnbOrganization;
	@Wire
	private Datebox dtbEffectiveOnDate;
	@Wire
	private Listbox lstBranch;
	
	@WireVariable
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient BranchSizeSetupService branchSizeSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private BranchSizeDTO selectedBranchSize;
	private OrganizationDTO selectedOrg;
	private List<BranchSizeDTO> sizes;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		bnbOrganization.setBnbBranch(bnbBranch);
		lstBranch.setMold("paging");
		lstBranch.setPageSize(GlobalVariable.getMaxRowPerPage());
		readParameter();
	}
	
	private void readParameter() {
		selectedOrg = (OrganizationDTO) arg.get(OrganizationDTO.class.getName());
		if(selectedOrg != null) {
			BranchSizeDTO branchSizeDto = new BranchSizeDTO();
			branchSizeDto.setOrganizationId(selectedOrg.getId());
			sizes = branchSizeSetupServiceImpl.findByExample(branchSizeDto);
			lstBranch.setModel(new ListModelList<BranchSizeDTO>(sizes));
			OrganizationDTO branch = organizationSetupServiceImpl.getBranch(selectedOrg.getId(), null, selectedOrg.getCompanyId());
			if(branch != null) {
				KeyValue keyValueBranch = new KeyValue();
				keyValueBranch.setKey(branch.getId());
				keyValueBranch.setValue(branch.getName());
				keyValueBranch.setDescription(branch.getName());
				bnbBranch.setRawValue(keyValueBranch);
			}
			bnbBranch.setDisabled(true);
			KeyValue keyValueOrg = new KeyValue();
			keyValueOrg.setKey(selectedOrg.getId());
			keyValueOrg.setValue(selectedOrg.getName());
			keyValueOrg.setDescription(selectedOrg.getName());
			bnbOrganization.setRawValue(keyValueOrg);
			bnbOrganization.setDisabled(true);
		} 
	}
	
	@Listen("onDownloadUserManual = #winBranchSizeSetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	
	
	@Listen("onClick = #btnNew")
	public void addNew() {
//		try {
//			clearErrorMessage();
//			populateBranchSize();
//			selectedBranchSize.setEffectiveStartDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : null);
//			branchSizeValidator.validate(selectedBranchSize);
			
//			OrganizationDTO organization = organizationSetupServiceImpl.findById((Long)bnbOrganization.getKeyValue().getKey());
//			selectedBranchSize.setOrganizationName(organization.getName());
//			selectedBranchSize.setOrganizationCode(organization.getCode());
//			selectedBranchSize.setOrganizationLevel(organization.getLevelCode());
				
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put(BranchSizeDTO.class.getName(), selectedBranchSize);
			Executions.createComponents("~./hcms/workstructure/branch_size_setup.zul", getSelf().getParent(), null);
			getSelf().detach();
//		} catch (ValidationException ve) {
//			showErrorMessage(ve.getConstraintViolations());
//		}			
	}
	
	private void populateBranchSize() {
		selectedBranchSize = new BranchSizeDTO();
		selectedBranchSize.setBranchId(bnbBranch.getKeyValue() != null ? (Long)bnbBranch.getKeyValue().getKey() : null);
		selectedBranchSize.setOrganizationId(bnbOrganization.getKeyValue() != null ? (Long)bnbOrganization.getKeyValue().getKey() : null);
		selectedBranchSize.setEffectiveStartDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtils.truncate(new Date(), Calendar.DATE));
		selectedBranchSize.setEffectiveOnDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtils.truncate(new Date(), Calendar.DATE));
		selectedBranchSize.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(bnbOrganization);
		ErrorMessageUtil.clearErrorMessage(dtbEffectiveOnDate);
	}
	
	@Listen("onClick = #btnFind")
	public void onFind(){
		clearErrorMessage();
		populateBranchSize();
		if(bnbBranch.getKeyValue() == null && selectedBranchSize.getOrganizationId() == null && selectedBranchSize.getEffectiveOnDate() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						List<BranchSizeDTO> branchs = branchSizeSetupServiceImpl.findByExample(selectedBranchSize);
						for(BranchSizeDTO branchSize : branchs) {
							if(branchSize.getSizeCode().equals(BranchSizeType.L.toString()))
								branchSize.setSize(BranchSizeType.L.getDesc());
							else if(branchSize.getSizeCode().equals(BranchSizeType.M.toString()))
								branchSize.setSize(BranchSizeType.M.getDesc());
							else if(branchSize.getSizeCode().equals(BranchSizeType.S.toString()))
								branchSize.setSize(BranchSizeType.S.getDesc());
						}
						lstBranch.setModel(new ListModelList<BranchSizeDTO>(branchs));
					}
				}				
			});
		} else {
			List<BranchSizeDTO> branchs = branchSizeSetupServiceImpl.findByExample(selectedBranchSize);
			for(BranchSizeDTO branchSize : branchs) {
				if(branchSize.getSizeCode().equals(BranchSizeType.L.toString()))
					branchSize.setSize(BranchSizeType.L.getDesc());
				else if(branchSize.getSizeCode().equals(BranchSizeType.M.toString()))
					branchSize.setSize(BranchSizeType.M.getDesc());
				else if(branchSize.getSizeCode().equals(BranchSizeType.S.toString()))
					branchSize.setSize(BranchSizeType.S.getDesc());
			}
			lstBranch.setModel(new ListModelList<BranchSizeDTO>(branchs));
		}
	}
	
	@Listen("onClick = #btnUpload")
	public void onUpload(){
		Executions.createComponents("~./hcms/workstructure/upload_branch_size.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winBranchSizeSetupInquiry")
	public void onDetail(ForwardEvent event){
		selectedBranchSize = (BranchSizeDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(BranchSizeDTO.class.getName(), selectedBranchSize);
		Executions.createComponents("~./hcms/workstructure/branch_size_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

}
