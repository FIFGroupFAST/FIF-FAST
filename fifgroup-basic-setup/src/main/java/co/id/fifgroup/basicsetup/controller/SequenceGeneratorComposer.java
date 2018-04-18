package co.id.fifgroup.basicsetup.controller;

import java.util.ArrayList;
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
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.domain.SequenceGenerator;
import co.id.fifgroup.basicsetup.domain.SequenceGeneratorExample;
import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.domain.TransactionTypeExample;
import co.id.fifgroup.basicsetup.dto.SequenceGeneratorDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.SequenceGeneratorService;
import co.id.fifgroup.basicsetup.service.TransactionTypeService;
import co.id.fifgroup.basicsetup.validation.SequenceGeneratorValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class SequenceGeneratorComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(SequenceGeneratorComposer.class);
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Listbox lstScope;
	@Wire
	private Listbox lstTrxType;
	@Wire
	private Intbox intLength;
	@Wire
	private Checkbox chkPadding;
	@Wire
	private Textbox txtPaddingChar;
	@Wire
	private Textbox txtPrefix;
	@Wire
	private Checkbox chkResetPerYear;
	@Wire
	private Intbox intStartFrom;
	@Wire
	private Intbox intIncrement;
	@Wire
	private Button btnSave;
	
	@WireVariable(rewireOnActivate=true)
	private transient SequenceGeneratorService sequenceGeneratorServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient TransactionTypeService transactionTypeServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private SequenceGenerator sequenceGenerator;
	private SequenceGeneratorDTO sequenceGeneratorDto;
	
	private ListModelList<TransactionType> listModelTransactionType;
	private ListModelList<Company>	listModelCompany;
	private FunctionPermission functionPermission;
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
			if(arg.containsKey(SequenceGeneratorDTO.class.getName())){
				listModelTransactionType.clear();
				listModelTransactionType.addAll(transactionTypeServiceImpl.getTransactionTypeByExample(null));
				sequenceGeneratorDto = ((SequenceGeneratorDTO) arg.get(SequenceGeneratorDTO.class.getName()));
				intLength.setValue(sequenceGeneratorDto.getLength());
				chkPadding.setChecked(sequenceGeneratorDto.getIsPadding());
				txtPaddingChar.setValue(sequenceGeneratorDto.getPaddingChar());
				txtPrefix.setValue(sequenceGeneratorDto.getPrefix());
				intIncrement.setValue(sequenceGeneratorDto.getSeqIncrement());
				intStartFrom.setValue(sequenceGeneratorDto.getSequenceStartFrom());
				chkResetPerYear.setChecked(sequenceGeneratorDto.getIsResetPerYear());
				
				TransactionTypeExample trxTypeExample = new TransactionTypeExample();
				trxTypeExample.setOrderByClause("TRX_TYPE ASC");
				
				TransactionType sequenceGeneratorSelect = new TransactionType();
				sequenceGeneratorSelect.setTrxType(Labels.getLabel(sequenceGeneratorDto.getTrxType()));
				List<TransactionType> listTransactionType = new ArrayList<TransactionType>();
				listTransactionType.add(sequenceGeneratorSelect);
				listTransactionType.addAll(transactionTypeServiceImpl.getTransactionTypeByExample(trxTypeExample));
				
				for(TransactionType sequenceGenerator : listModelTransactionType) {
					if(sequenceGenerator.getTrxType().equals(sequenceGeneratorDto.getTrxType())) {
						listModelTransactionType.addToSelection(sequenceGenerator);
						break;
						}
					}
				
				for(Company company : listModelCompany) {
				if(company.getCompanyName().equals(sequenceGeneratorDto.getCompanyName())) {
					listModelCompany.addToSelection(company);
					break;
					}
				}
				
				btnSave.setDisabled(true);
				lstScope.setDisabled(true);
				lstTrxType.setDisabled(true);
				intLength.setDisabled(true);
				chkPadding.setDisabled(true);
				txtPaddingChar.setDisabled(true);
				txtPrefix.setDisabled(true);
				intIncrement.setDisabled(true);
				intStartFrom.setDisabled(true);
				chkResetPerYear.setDisabled(true);
		}

			functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
			initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			lstScope.setDisabled(true);
			lstTrxType.setDisabled(true);
			intIncrement.setDisabled(true);
			intLength.setDisabled(true);
			intStartFrom.setDisabled(true);
			chkPadding.setDisabled(true);
			chkResetPerYear.setDisabled(true);
			txtPaddingChar.setDisabled(true);
			txtPrefix.setDisabled(true);
		}
			
	}

	private void init() {
		initListModelTransactionType();
		initListCompanyName();
	}



	public void initListCompanyName(){
		Company companySelect = new Company();
		companySelect.setCompanyName(Labels.getLabel("bse.select"));
		Company companyCommon = new Company();
		companyCommon.setCompanyId(ScopeType.COMMON.getValue());
		companyCommon.setCompanyName(ScopeType.COMMON.toString());
		List<Company> listCompany = new ArrayList<Company>();
		listCompany.add(companySelect);
		listCompany.add(companyCommon);
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria().andGroupIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId())
		.andEffectiveStartDateLessThanOrEqualTo(DateUtils.truncate(new Date(), Calendar.DATE))
		.andEffectiveEndDateGreaterThanOrEqualTo(DateUtils.truncate(new Date(), Calendar.DATE));
		companyExample.setOrderByClause("UPPER(COMPANY_NAME) ASC");
		listCompany.addAll(companyServiceImpl.getCompanyByExample(companyExample));
		listModelCompany = new ListModelList<Company>(listCompany);
		lstScope.setModel(listModelCompany);
		lstScope.renderAll();
		listModelCompany.addToSelection(companySelect);
	}
	
	public void initListModelTransactionType(){
		TransactionType transactionTypeSelect = new TransactionType();
		transactionTypeSelect.setTrxType(Labels.getLabel("bse.select"));
		List<TransactionType> listTransactionType = new ArrayList<TransactionType>();
		listTransactionType.add(transactionTypeSelect);
		listModelTransactionType = new ListModelList<TransactionType>(listTransactionType);
		lstTrxType.setModel(listModelTransactionType);
		lstTrxType.renderAll();
		listModelTransactionType.addToSelection(transactionTypeSelect);

	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2175205901842222536L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try{
						setSequenceGenerator();
						sequenceGeneratorServiceImpl.save(sequenceGenerator);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Executions.createComponents("~./hcms/basic-setup/sequence_generator_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();
					} catch (ValidationException vex){
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
	
	
	private void setSequenceGenerator() {
		if(sequenceGenerator == null) {
			sequenceGenerator = new SequenceGenerator();
		}
		sequenceGenerator.setGroupId(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
		TransactionType selectedTransactionType = (TransactionType) lstTrxType.getSelectedItem().getValue();
		sequenceGenerator.setTrxCode(selectedTransactionType.getTrxCode());
		Company selectedCompany = (Company) lstScope.getSelectedItem().getValue();
		sequenceGenerator.setCompanyScope(selectedCompany.getCompanyId());
		sequenceGenerator.setLength(intLength.getValue());
		sequenceGenerator.setIsPadding(chkPadding.isChecked());
		sequenceGenerator.setPaddingChar(txtPaddingChar.getValue());
		sequenceGenerator.setPrefix(txtPrefix.getValue().trim());
		sequenceGenerator.setSequenceStartFrom(intStartFrom.getValue());
		if(intStartFrom.getValue()==null){
			sequenceGenerator.setSequenceStartFrom(1);
		}
		sequenceGenerator.setSeqIncrement(intIncrement.getValue());
		if(intIncrement.getValue()==null){
			sequenceGenerator.setSeqIncrement(1);
		}
		sequenceGenerator.setIsResetPerYear(chkResetPerYear.isChecked());
		if(sequenceGenerator.getSequenceGeneratorId() == null) {
			sequenceGenerator.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			sequenceGenerator.setCreationDate(new Date());
		}
		sequenceGenerator.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		sequenceGenerator.setLastUpdateDate(new Date());
	}



	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		ErrorMessageUtil.clearErrorMessage(lstScope);
		ErrorMessageUtil.clearErrorMessage(lstTrxType);
		ErrorMessageUtil.clearErrorMessage(intLength);
		ErrorMessageUtil.clearErrorMessage(intStartFrom);
		ErrorMessageUtil.clearErrorMessage(intIncrement);
		ErrorMessageUtil.clearErrorMessage(txtPaddingChar);
		ErrorMessageUtil.clearErrorMessage(txtPrefix);
		
		if(errors.containsKey(SequenceGeneratorValidator.SEQUENCE_GENERATOR_COMPANY_SCOPE)) {
			ErrorMessageUtil.setErrorMessage(lstScope, vex.getMessage(SequenceGeneratorValidator.SEQUENCE_GENERATOR_COMPANY_SCOPE));
		}
		if(errors.containsKey(SequenceGeneratorValidator.SEQUENCE_GENERATOR_TRX_CODE)) {
			ErrorMessageUtil.setErrorMessage(lstTrxType, vex.getMessage(SequenceGeneratorValidator.SEQUENCE_GENERATOR_TRX_CODE));				
		}
		if(errors.containsKey(SequenceGeneratorValidator.SEQUENCE_GENERATOR_LENGTH)) {
			ErrorMessageUtil.setErrorMessage(intLength, vex.getMessage(SequenceGeneratorValidator.SEQUENCE_GENERATOR_LENGTH));
		}
		if(errors.containsKey(SequenceGeneratorValidator.SEQUENCE_GENERATOR_START_FROM)) {
			ErrorMessageUtil.setErrorMessage(intStartFrom, vex.getMessage(SequenceGeneratorValidator.SEQUENCE_GENERATOR_START_FROM));
		}
		if(errors.containsKey(SequenceGeneratorValidator.SEQUENCE_GENERATOR_INCREMENT)) {
			ErrorMessageUtil.setErrorMessage(intIncrement, vex.getMessage(SequenceGeneratorValidator.SEQUENCE_GENERATOR_INCREMENT));
		}
		if(errors.containsKey(SequenceGeneratorValidator.SEQUENCE_GENERATOR_PADDING_CHAR)) {
			ErrorMessageUtil.setErrorMessage(txtPaddingChar, vex.getMessage(SequenceGeneratorValidator.SEQUENCE_GENERATOR_PADDING_CHAR));
		}
		if(errors.containsKey(SequenceGeneratorValidator.SEQUENCE_GENERATOR_PREFIX)) {
			ErrorMessageUtil.setErrorMessage(txtPrefix, vex.getMessage(SequenceGeneratorValidator.SEQUENCE_GENERATOR_PREFIX));
		}
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
					Executions.createComponents("~./hcms/basic-setup/sequence_generator_inquiry.zul", getSelf().getParent(), params);
					getSelf().detach();
				}
			}
		});
	}

	@Listen("onSelect = #lstScope")
	public void onSelectScope() {
		Company scope = (Company) lstScope.getSelectedItem().getValue();
		if(scope.getCompanyId() != null) {
			SequenceGeneratorExample sequenceGeneratorExample = new SequenceGeneratorExample();
			SequenceGeneratorExample.Criteria criteriaSequenceGenerator = sequenceGeneratorExample.createCriteria();
			
			List<Long> scopeSequenceGenerator = new ArrayList<Long>();
			scopeSequenceGenerator.add(ScopeType.COMMON.getValue());
			scopeSequenceGenerator.add(scope.getCompanyId());
			
			criteriaSequenceGenerator.andGroupIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
			if(scope.getCompanyId().longValue() != ScopeType.COMMON.getValue().longValue()) {
				criteriaSequenceGenerator.andCompanyScopeIn(scopeSequenceGenerator);
			}
			List<SequenceGenerator> sequenceGenerators = sequenceGeneratorServiceImpl.getSequenceGeneratorByExample(sequenceGeneratorExample);
			
			List<String> trxCode = new ArrayList<String>();
			for(SequenceGenerator sequenceGenerator : sequenceGenerators) {
				trxCode.add(sequenceGenerator.getTrxCode());
			}
			
			TransactionTypeExample transactionTypeExample = new TransactionTypeExample();
			TransactionTypeExample.Criteria criteriaTransactionType = transactionTypeExample.createCriteria();
			if(trxCode.size() > 0) {
				criteriaTransactionType.andTrxCodeNotIn(trxCode);				
			}
			transactionTypeExample.setOrderByClause("UPPER(TRX_TYPE) ASC");
			
			TransactionType transactionTypeSelect = new TransactionType();
			transactionTypeSelect.setTrxType(Labels.getLabel("bse.select"));
			
			List<TransactionType> transactionTypes = new ArrayList<TransactionType>();
			transactionTypes.add(transactionTypeSelect);
			transactionTypes.addAll(transactionTypeServiceImpl.getTransactionTypeByExample(transactionTypeExample));
			
			listModelTransactionType.clear();
			listModelTransactionType.addAll(transactionTypes);
			listModelTransactionType.addToSelection(transactionTypeSelect);
			
		}
	}
	
	@Listen("onCheck = #chkPadding")
	public void onCheckPadding() {
		txtPaddingChar.setValue(null);
		if(chkPadding.isChecked()) {
			txtPaddingChar.setDisabled(false);
		} else {
			txtPaddingChar.setDisabled(true);
		}
	}
	
}
