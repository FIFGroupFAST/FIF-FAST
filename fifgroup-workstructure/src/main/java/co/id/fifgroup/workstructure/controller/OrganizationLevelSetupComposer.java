package co.id.fifgroup.workstructure.controller;

import java.util.Date;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Slider;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxFrom;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;
import co.id.fifgroup.workstructure.validation.OrganizationLevelValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class OrganizationLevelSetupComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationLevelSetupComposer.class);

	@Wire private Textbox txtOrgLevelCode;
	@Wire private Textbox txtOrgLevelName;
	@Wire private Textbox txtOrgLevelDesc;
	@Wire private DateBoxFrom dtbFrom;
	@Wire private DateBoxMax dtbTo;
	@Wire private Button btnSave;
	@Wire private Slider sliderRed;
	@Wire private Slider sliderGreen;
	@Wire private Slider sliderBlue;
	@Wire private Div divColor;
	@Wire private Label lblColor;
	
	@WireVariable
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationLevelSetupService organizationLevelSetupServiceImpl;	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private OrganizationLevelDto selectedLevel;
	private String redColor = "00";
	private String greenColor = "00";
	private String blueColor = "00";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		dtbFrom.setFormat(DateUtil.DEFAULT_FORMAT);
		dtbTo.setFormat(DateUtil.DEFAULT_FORMAT);
		populateColorList();
		if (arg.get(OrganizationLevelDto.class.getName()) != null) {
			selectedLevel = (OrganizationLevelDto) arg.get(OrganizationLevelDto.class.getName());
			selectedLevel.setIsFuture(selectedLevel.getStartDate().compareTo(new Date()) > 0);
			render(selectedLevel);
			if(selectedLevel.getEndDate().compareTo(DateUtil.truncate(new Date())) < 0)
				disabledComponent();
			else
				disabledComponent();
			if(selectedLevel.getStartDate().compareTo(DateUtil.truncate(new Date())) > 0) {
				txtOrgLevelDesc.setDisabled(false);
				dtbFrom.setDisabled(false);
			}
			
		} else {
			selectedLevel = new OrganizationLevelDto();
			lblColor.setValue("#000000");
		}
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
						populate(selectedLevel);
						organizationLevelSetupServiceImpl.save(selectedLevel);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						doBack();
					} catch (ValidationException ve) {
						logger.error(ve.getAllMessages());
						showErrorMessage(ve.getConstraintViolations());
					} catch(Exception ex) {
						logger.error(ex.getMessage(), ex);
						Messagebox.show(ex.getMessage());
					}
				}
			}			
		});
	}
	
	private void showErrorMessage(Map<String, String> maps) {
		if(maps.get(OrganizationLevelValidator.LEVEL_CODE) != null) {
			ErrorMessageUtil.setErrorMessage(txtOrgLevelCode, 
				maps.get(OrganizationLevelValidator.LEVEL_CODE));
		}
		if(maps.get(OrganizationLevelValidator.LEVEL_NAME) != null) {
			ErrorMessageUtil.setErrorMessage(txtOrgLevelName, 
				maps.get(OrganizationLevelValidator.LEVEL_NAME));
		}
		if(maps.get(OrganizationLevelValidator.COLOR) != null) {
			ErrorMessageUtil.setErrorMessage(sliderBlue, 
				maps.get(OrganizationLevelValidator.COLOR));
		}
		if(maps.get(OrganizationLevelValidator.DATE_FROM) != null) {
			ErrorMessageUtil.setErrorMessage(dtbFrom, 
				maps.get(OrganizationLevelValidator.DATE_FROM));
		}
		if(maps.get(OrganizationLevelValidator.DATE_TO) != null) {
			ErrorMessageUtil.setErrorMessage(dtbTo, 
				maps.get(OrganizationLevelValidator.DATE_TO));
		}		
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtOrgLevelCode);
		ErrorMessageUtil.clearErrorMessage(txtOrgLevelName);
		ErrorMessageUtil.clearErrorMessage(sliderBlue);
		ErrorMessageUtil.clearErrorMessage(dtbFrom);
	}
	
	@Listen ("onClick = button#btnCancel")
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
		Executions.createComponents("~./hcms/workstructure/organization_level_setup_inquiry.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	private void render(OrganizationLevelDto subject) {
		txtOrgLevelCode.setValue(subject.getCode());
		txtOrgLevelName.setValue(subject.getName());
		txtOrgLevelDesc.setValue(subject.getDescription());
		divColor.setStyle("background-color:#".concat(subject.getColor()));
		lblColor.setValue("#".concat(subject.getColor()));
		dtbFrom.setValue(subject.getStartDate());
		dtbTo.setValue(subject.getEndDate());
	}
	
	private void populate(OrganizationLevelDto subject) {
		subject.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		subject.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		subject.setCreationDate(new Date());
		subject.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		subject.setLastUpdateDate(new Date());
		subject.setCode(txtOrgLevelCode.getValue().toUpperCase());
		subject.setName(txtOrgLevelName.getValue().toUpperCase());
		subject.setDescription(txtOrgLevelDesc.getValue());
		if(subject.getId() == null)
			subject.setColor(redColor.concat(greenColor).concat(blueColor));
		subject.setStartDate(dtbFrom.getValue());
		subject.setEndDate(dtbTo.getValue());
	}
	
	private void populateColorList() {
		divColor.setStyle("background-color:black");
		sliderRed.setCurpos(00);
		sliderGreen.setCurpos(00);
		sliderBlue.setCurpos(00);
	}
	
	private void disabledComponent() {
		txtOrgLevelName.setDisabled(true);
		txtOrgLevelCode.setDisabled(true);
		txtOrgLevelDesc.setDisabled(true);
		sliderRed.setVisible(false);
		sliderGreen.setVisible(false);
		sliderBlue.setVisible(false);
		dtbFrom.setDisabled(true);
	}
	
	@Listen("onScroll = Slider#sliderRed")
	public void onScrollRed(){
		changeColor();
	}
	
	@Listen("onScroll = Slider#sliderGreen")
	public void onScrollGreen(){
		changeColor();
	}
	
	@Listen("onScroll = Slider#sliderBlue")
	public void onScrollBlue(){
		changeColor();
	}
	
	private void changeColor() {
		String backgroundColor = "background-color:#";
		redColor = "0";
		if(String.valueOf(sliderRed.getCurpos()).length() == 1)
			redColor = redColor.concat(String.valueOf(sliderRed.getCurpos()));
		else 
			redColor = String.valueOf(sliderRed.getCurpos());
		
		greenColor = "0";
		if(String.valueOf(sliderGreen.getCurpos()).length() == 1)
			greenColor = greenColor.concat(String.valueOf(sliderGreen.getCurpos()));
		else
			greenColor = String.valueOf(sliderGreen.getCurpos());
		
		blueColor = "0";
		if(String.valueOf(sliderBlue.getCurpos()).length() == 1)
			blueColor = blueColor.concat(String.valueOf(sliderBlue.getCurpos()));
		else
			blueColor = String.valueOf(sliderBlue.getCurpos());
		
		backgroundColor = backgroundColor.concat(redColor);
		backgroundColor = backgroundColor.concat(greenColor);
		backgroundColor = backgroundColor.concat(blueColor);
		divColor.setStyle(backgroundColor);
		lblColor.setValue("#".concat(redColor).concat(greenColor).concat(blueColor));
	}
}
