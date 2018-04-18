package co.id.fifgroup.systemadmin.controller;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.UploadEvent;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.service.TaskService;
import co.id.fifgroup.systemadmin.validation.TaskFileUploadValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskFileUploadComposer extends SelectorComposer<Window> {
	
	private static Logger log = LoggerFactory.getLogger(TaskFileUploadComposer.class);
	
	private static final long serialVersionUID = 1L;
	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient TaskService taskServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Listbox lsModule;
	@Wire
	private Radiogroup rdgType;
	@Wire
	private Textbox txtExecutableFile;
	@Wire
	private Button btnBrowse;
	@Wire
	private Button btnUpload;

	private InputStream executableFile;
	private ExecutableFile selectedExecutableFile;

	private Media media;
	private String fileName="";
	private String filePath="";
	private List<Module> modules;
	private DataOutputStream out;
	private FunctionPermission functionPermission;
	
	private static String MODULE_ROOT_FOLDER = "SYSTEM_ADMINISTRATION";
	private static String TASK_ROOT_FOLDER = "Task";
	private static String EXECUTABLE_FILE_ROOT_FOLDER = "Executable Files";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateModule();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable()){
			lsModule.setDisabled(true);
			for (Component comp : rdgType.getChildren()) {
				Radio rd = (Radio)comp;
				rd.setDisabled(true);
			}
			txtExecutableFile.setDisabled(true);
			btnBrowse.setDisabled(true);
			btnUpload.setDisabled(true);
		}
		
	}
	
	@Listen("onDownloadUserManual = #winUploadFile")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onUpload = button#btnBrowse")
	public void onBrowse(Event evt) {
		media = ((UploadEvent) evt).getMedia();
		if (media == null) {
			return;
		}
		
		String extension = media.getName().substring(media.getName().lastIndexOf(".") + 1, media.getName().length());
		if (rdgType.getSelectedIndex() == 0 && extension.toLowerCase().equals("jar")) {
			executableFile = media.getStreamData();
			txtExecutableFile.setValue(media.getName());
		} else if (rdgType.getSelectedIndex() == 1 && extension.toLowerCase().equals("jasper")) {
			executableFile = media.getStreamData();
			txtExecutableFile.setValue(media.getName());
		} else {
			txtExecutableFile.setValue(media.getName());
		}
	}
	
	private void createRootPath() {
		File root = new File(GlobalVariable.getAppsRootFolder());

		if (!root.isDirectory()) {
			root.mkdir();
		}
		
		File outputLogPath = new File(root.getPath()+File.separator+MODULE_ROOT_FOLDER+File.separator+TASK_ROOT_FOLDER+File.separator+EXECUTABLE_FILE_ROOT_FOLDER+File.separator);
		if (!outputLogPath.isDirectory()) {
			outputLogPath.mkdirs();
		}
		filePath += outputLogPath.getPath();
	}

	@Listen("onClick = button#btnUpload")
	public void onUpload() {
		try{
			createRootPath();
			clearErrorMessage();
			filePath += File.separator + lsModule.getSelectedItem().getLabel() + File.separator;
			if(media != null) {
				fileName = filePath + media.getName();				
			} else {
				fileName = null;
			}
			setExecutableFile();
			taskServiceImpl.validateExecutableFile(selectedExecutableFile);
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {	
					int status = (int) event.getData();
					if(status == 16) {
						
						File file = new File(selectedExecutableFile.getExecutableFileName());
						if (file.exists()) {
							Messagebox.show(Labels.getLabel("sam.err.fileIsAlreadyExist"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									int status = (int) arg0.getData();
									if(status == 16) {
										uploadFile();
										updateData();
										Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"), "Information", Messagebox.YES, Messagebox.INFORMATION);		
										refreshPage();
										
									}else{
										return;
									}
								}

								
								
							});
						}else{
							uploadFile();
							saveData();
							Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"), "Information", Messagebox.YES, Messagebox.INFORMATION);		
							refreshPage();							
						}
						
					} else {
						return;
					}
				}
			});	
		} catch (ValidationException e) {
			showErrorMessage(e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private void refreshPage() {
		fileName="";
		filePath="";
		txtExecutableFile.setValue("");
//		selectedExecutableFile = new ExecutableFile();
		rdgType.setSelectedIndex(0);
		populateModule();
		
//		Executions.createComponents(
//				"~./hcms/systemadmin/task_file_upload.zul", getSelf()
//						.getParent(), null);
//		getSelf().getParent().detach();
	}
	
	private void populateModule() {
		ModuleExample example = new ModuleExample();
		example.setOrderByClause("MODULE_NAME");
		modules = moduleServiceImpl.getModuleByExample(example);
		Module module = new Module();
		module.setModuleName(Labels.getLabel("common.select"));
		modules.add(0, module);
		ListModelList<Module> model = new ListModelList<Module>(modules);
		lsModule.setModel(model);
		lsModule.renderAll();
		lsModule.setSelectedIndex(0);
	}

	private void saveData() {
		taskServiceImpl.uploadExecutableFile(selectedExecutableFile);
	}
	
	private void updateData(){
		taskServiceImpl.updateExecutableFile(selectedExecutableFile);
	}

	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(btnBrowse);
		ErrorMessageUtil.clearErrorMessage(lsModule);
		ErrorMessageUtil.clearErrorMessage(rdgType);
	}
	
	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		if(errors.containsKey(TaskFileUploadValidator.EXECUTABLE_FILE_NAME)) {
			ErrorMessageUtil.setErrorMessage(btnBrowse, vex.getMessage(TaskFileUploadValidator.EXECUTABLE_FILE_NAME));
		}
		if(errors.containsKey(TaskFileUploadValidator.MODULE_ID)) {
			ErrorMessageUtil.setErrorMessage(lsModule, vex.getMessage(TaskFileUploadValidator.MODULE_ID));
		}
		if(errors.containsKey(TaskFileUploadValidator.TASK_TYPE)) {
			ErrorMessageUtil.setErrorMessage(rdgType, vex.getMessage(TaskFileUploadValidator.TASK_TYPE));
		}
	}

	private void setExecutableFile() {
		selectedExecutableFile = new ExecutableFile();
		selectedExecutableFile.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedExecutableFile.setCreationDate(new Date());
		selectedExecutableFile.setLastUpdateDate(new Date());
		selectedExecutableFile.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedExecutableFile.setModuleId(lsModule.getSelectedItem().getLabel().equalsIgnoreCase(Labels.getLabel("common.select")) ? null : (long) lsModule.getSelectedItem().getValue());
		selectedExecutableFile.setTaskType(TaskType.getValue(rdgType.getSelectedIndex()));
		selectedExecutableFile.setExecutableFileName(fileName);
	}
	
	private void uploadFile() {
		if (media != null) {
			out = null;
			try {
				File fileDir = new File(filePath);
				File file = new File(fileName);
				if (fileDir.isDirectory()) {
					out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				} else {
					fileDir.mkdir();
					out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				}
				
				int ch = 0;
				while ((ch = executableFile.read()) != -1) {
					out.write(ch);
				}
				out.flush();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
	}
}
