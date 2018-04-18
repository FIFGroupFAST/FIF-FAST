package co.id.fifgroup.basicsetup.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.dto.PrinterDTO;
import co.id.fifgroup.basicsetup.service.PrinterService;
import co.id.fifgroup.basicsetup.validation.PrinterValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;


@VariableResolver(DelegatingVariableResolver.class)
public class PrinterSetupComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(PrinterSetupComposer.class);
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@Wire
	private Textbox txtPrinterCode;
	@Wire
	private Textbox txtPrinterName;
	@Wire
	private Textbox txtPrinterArgument;
	@Wire
	private Textbox txtPrinterDesc;
	@Wire
	private Button btnSave;
	
	@WireVariable
	private Map<String, Object> arg;
	
	private Printer printer;
	
	@WireVariable(rewireOnActivate=true)
	private transient PrinterService printerServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if(arg.containsKey(PrinterDTO.class.getName())) {
			printer = ((PrinterDTO) arg.get(PrinterDTO.class.getName())).getPrinter();
			txtPrinterCode.setValue(printer.getPrinterCode());
			txtPrinterName.setValue(printer.getPrinterName());
			txtPrinterDesc.setValue(printer.getDescription());
			txtPrinterArgument.setValue(printer.getArgument());
			txtPrinterCode.setDisabled(true);
		}
		
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtPrinterCode.setDisabled(true);
			txtPrinterName.setDisabled(true);
			txtPrinterDesc.setDisabled(true);
			txtPrinterArgument.setDisabled(true);
		}
			
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7116163631695281460L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						setPrinter();
						printerServiceImpl.save(printer);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Executions.createComponents("~./hcms/basic-setup/printer_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();
					} catch (ValidationException vex) {
						showErrorMessage(vex);
						log.error(vex.getMessage());
					} catch (Exception e) {
						Messagebox.show("System Error");
						log.error(e.getMessage(), e);
					}
				}
			}
		});
	}
	
	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		ErrorMessageUtil.clearErrorMessage(txtPrinterCode);
		ErrorMessageUtil.clearErrorMessage(txtPrinterName);
		ErrorMessageUtil.clearErrorMessage(txtPrinterArgument);
		if(errors.containsKey(PrinterValidator.PRINTER_CODE)) {
			ErrorMessageUtil.setErrorMessage(txtPrinterCode, vex.getMessage(PrinterValidator.PRINTER_CODE));				
		}
		if(errors.containsKey(PrinterValidator.PRINTER_NAME)) {
			ErrorMessageUtil.setErrorMessage(txtPrinterName, vex.getMessage(PrinterValidator.PRINTER_NAME));
		}
		if(errors.containsKey(PrinterValidator.PRINTER_ARGUMENT)) {
			ErrorMessageUtil.setErrorMessage(txtPrinterArgument, vex.getMessage(PrinterValidator.PRINTER_ARGUMENT));
		}
		
	}

	private void setPrinter() {
		if(printer == null) {
			printer = new Printer();
		}
		printer.setPrinterCode(txtPrinterCode.getValue().trim());
		printer.setPrinterName(txtPrinterName.getValue().trim());
		printer.setDescription(txtPrinterDesc.getValue().trim());
		printer.setArgument(txtPrinterArgument.getValue().trim());
		if(printer.getPrinterId() == null) {
			printer.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			printer.setCreationDate(new Date());
		}
		printer.setLastUpdateDate(new Date());
		printer.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		
	}

	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5799520384125775408L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/basic-setup/printer_inquiry.zul", getSelf().getParent(), params);
					getSelf().detach();
				}
			}
		});
	}

}
