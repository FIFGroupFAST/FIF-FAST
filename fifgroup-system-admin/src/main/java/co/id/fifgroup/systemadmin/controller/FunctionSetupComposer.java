package co.id.fifgroup.systemadmin.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.poi.util.IOUtils;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.util.FileUtil;
import com.google.common.base.Strings;

import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.dto.FunctionDTO;
import co.id.fifgroup.systemadmin.service.FunctionService;
import co.id.fifgroup.systemadmin.validation.FunctionValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class FunctionSetupComposer extends SelectorComposer<Window> {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(FunctionSetupComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient FunctionService functionService;
	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient AuditPerformer auditPerformer;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Listbox lstModule;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Textbox txtFormLink;
	@Wire
	private Textbox txtUserManual;
	@Wire
	private A linkDelete;
	@Wire
	private Button btnBrowse;
	@Wire
	private Button btnSave;
	@Wire
	private Label lblfileName;
	
	private Function selectedFunction;
	private Function function;
	private Function prevObject;
	private Control control;
	private Media media;
	private InputStream executableFile;
	private DataOutputStream out;
	private String fileName;
	private String filePath;
	private String oldFileName;
	private FunctionPermission functionPermission;

	private static String USER_MANUAL_FOLDER = "Function User Manuals";
	private static String MODULE_ROOT_FOLDER = "SYSTEM_ADMINISTRATION";
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadParameter();
		populateModule();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			lstModule.setDisabled(true);
			txtName.setDisabled(true);
			txtDesc.setDisabled(true);
			txtFormLink.setDisabled(true);
			txtUserManual.setDisabled(true);
			btnBrowse.setDisabled(true);
			btnSave.setDisabled(true);
		}
	}
	
	private void loadParameter() {
		if(oldFileName == null) {
			oldFileName = new String();
		}
		
		selectedFunction = (Function) arg.get("function");
		if(selectedFunction != null) {
			function = selectedFunction;
			txtName.setDisabled(true);
			txtName.setValue(selectedFunction.getFunctionName());
			txtDesc.setValue(selectedFunction.getDescription());
			txtFormLink.setValue(selectedFunction.getActionUrl());
			if(!Strings.isNullOrEmpty(selectedFunction.getUserManual())) lblfileName.setValue(selectedFunction.getUserManual().substring(selectedFunction.getUserManual().lastIndexOf('\\')+1, selectedFunction.getUserManual().length()));
			oldFileName = txtUserManual.getValue();
			prevObject = (Function) DeepCopy.copy(selectedFunction);
			if(!lblfileName.getValue().equals("")) {
				linkDelete.setVisible(true);
			}
			
			linkDelete.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					lblfileName.setValue("");
					linkDelete.setVisible(false);
				}
			});
		}
	}
	
	private void populateModule() {
		Module module = new Module();
		module.setModuleName("-Select-");
		ModuleExample example = new ModuleExample();
		example.setOrderByClause("MODULE_NAME");
		List<Module> modules = moduleServiceImpl.getModuleByExample(example);
		modules.add(0, module);
		ListModelList<Module> model = new ListModelList<Module>(modules);
		lstModule.setModel(model);
		lstModule.renderAll();
		lstModule.setSelectedIndex(0);
		
		if (function != null) {
			for (int i=0; i<model.getSize(); i++) {
				if(function.getModuleId().equals(model.get(i).getModuleId()))					
					lstModule.setSelectedIndex(i);
			}
		}
	}
	
	@Listen("onClick = #txtUserManual")
	public void onClickUserManual() {
		Clients.evalJavaScript("jq('$btnBrowse').next().find('input').click();");
	}
	
	@Listen ("onUpload = button#btnBrowse")
	public void onBrowse(UploadEvent event) {
		if (FileUtil.doValidateDocumentSize(event.getMedia(), 2000)) {
			media = event.getMedia();
			txtUserManual.setText(media.getName());
			//executableFile = media.getStreamData();
		}
	}
	
	public boolean validateFileSize(){
		ErrorMessageUtil.clearErrorMessage(txtUserManual);
		boolean status = true;
		if(media!=null){
			String extension = media.getName().substring(media.getName().indexOf("."), media.getName().length());
			if(!extension.equals(".txt")){
				try {
					byte[] uploadFile = IOUtils.toByteArray( media.getStreamData());
					if(uploadFile.length/(1024*1024)>2){
						status = false;
						ErrorMessageUtil.setErrorMessage(txtUserManual, "Upload for User Manual file must not more than 2 MB");
					}else status = true;
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			
		}
		return status;
	}
	
	private void showErrorMessage(ValidationException e) {
		ErrorMessageUtil.setErrorMessage(lstModule, e.getMessage(FunctionValidator.MODULE_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtName, e.getMessage(FunctionValidator.NAME_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtName, e.getMessage(FunctionValidator.NAME_IS_NOT_UNIQUE));
		ErrorMessageUtil.setErrorMessage(txtDesc, e.getMessage(FunctionValidator.DESCRIPTION_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtFormLink, e.getMessage(FunctionValidator.FORM_LINK_NOT_EMPTY));
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lstModule);
		ErrorMessageUtil.clearErrorMessage(txtName);
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(txtFormLink);
	}
	
	private void uploadFile() {
		if (media != null) {
			File file = new File(fileName);
			String extension = media.getName().substring(media.getName().indexOf("."), media.getName().length());
			if(extension.equals(".txt")){
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write(media.getStringData());
					bw.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
				
				
			}else{
				try {
					InputStream input = media.getStreamData();
					int size = input.available();
					OutputStream f = new FileOutputStream(file);
					byte[] buf = new byte[size];
					input.read(buf);
					f.write(buf);
					f.close();
					input.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			
		}
	}
	
	private void deleteFile() {
		if(!oldFileName.equals(filePath + txtUserManual.getValue())) {
			File file = new File(oldFileName);
			file.delete();
		}
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {	
		if (function == null) {
			function = new FunctionDTO();
			control = Control.CREATE;
		} else {
			control = Control.UPDATE;
		}
		
		/*filePath = Labels.getLabel("root.folder") + Labels.getLabel("root.userManual");*/
		filePath = GlobalVariable.getAppsRootFolder()+File.separator+MODULE_ROOT_FOLDER+File.separator+USER_MANUAL_FOLDER+File.separator;
		if(media != null) {
			String extension = media.getName().substring(media.getName().indexOf("."), media.getName().length());
			fileName = filePath + txtName.getValue() + extension;
		} else {
			fileName = "";
		}
		
		File path = new File(filePath);
		if(!path.isDirectory())
			path.mkdirs();
		
		Module module = lstModule.getSelectedItem().getValue();
		function.setModuleId(module.getModuleId());
		function.setFunctionName(txtName.getValue());
		function.setDescription(txtDesc.getValue());
		function.setActionUrl(txtFormLink.getValue());
		function.setUserManual(fileName);
		if(selectedFunction == null) {
			function.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			function.setCreationDate(new Date());
		} else {
			function.setCreatedBy(selectedFunction.getCreatedBy());
			function.setCreationDate(selectedFunction.getCreationDate());
		}
		function.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		function.setLastUpdateDate(new Date());
				
		Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
				
				/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
				
				@Override
				public void onEvent(Event event) throws Exception {	
					int status = (int) event.getData();
					if(status == 16) {
						if(validateFileSize()){
							try {
								clearErrorMessage();
								deleteFile();
								uploadFile();
								functionService.save(function);
								auditPerformer.doAudit(UUID.randomUUID(), UUID.randomUUID(), prevObject, function, 1, control, Action.SUBMIT, securityServiceImpl.getSecurityProfile().getUserId(), TrxType.FUNCTION_SETUP.getValue(), function.getId());
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/systemadmin/function_inquiry.zul", getSelf().getParent(), param);
								getSelf().detach();
							} catch (ValidationException e) {
								showErrorMessage(e);
							} catch (Exception e) {
								log.error(e.getMessage());
								Messagebox.show(Labels.getLabel("notif.accessDataError"), "ERROR", Messagebox.OK, Messagebox.ERROR);
							}
						}
					} else {
						return;
					}
				}
		});
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
					Executions.createComponents("~./hcms/systemadmin/function_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});	
	}
}
