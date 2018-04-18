package co.id.fifgroup.core.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.service.PersonServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class EmployeeNumberListOfValueComposer extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4721484686849739069L;
	
	private Logger logger = LoggerFactory.getLogger(EmployeeNumberListOfValueComposer.class);
	
	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Searchtextbox txtEmployeeNumber;
	@Wire
	private Searchtextbox txtEmployeeName;
	@Wire
	private Listbox listbox;
	private Bandbox source;
	@Wire
	private Paging pgListOfValue;
	
	@WireVariable(rewireOnActivate=true)
	private transient PersonServiceAdapter personService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private List<KeyValue> persons;
	private ListModelList<KeyValue> listModelPerson;
	
	private String employeeNumber;
	private String employeeName;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
	}
	
	private void init() {
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		initListModelPerson();
	}
	
	private void initListModelPerson() {
		listModelPerson = new ListModelList<KeyValue>();
		listbox.setModel(listModelPerson);
		listbox.renderAll();
	}
	
	@Listen("onClick = #btnFind")
	public void onFind() {
		employeeNumber = txtEmployeeNumber.getValue();
		employeeName = txtEmployeeName.getValue();
		int totalSize = personService.countPersonByCompany(employeeNumber, employeeName, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		pgListOfValue.setTotalSize(totalSize);
		pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
		pgListOfValue.setActivePage(0);
		generateDataAndPaging();
	}
	
	@Listen("onSelect=#listbox")
	public void select() {
		try {
			KeyValue selectedData = (KeyValue) listbox.getSelectedItem().getValue();
			if(source != null) {
				source.setRawValue(selectedData);
				Events.postEvent(Events.ON_CLOSE, source, selectedData);
			}
			getSelf().detach();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Listen("onClick=#btnDeselect")
	public void deselect() {
		if(source != null) {
			source.setRawValue(null);
			Events.postEvent(Events.ON_CLOSE, source, null);
		}
		getSelf().detach();
	}
	
	private void generateDataAndPaging() {
		persons = personService.getPersonByCompany(employeeNumber, employeeName, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		listModelPerson.clear();
		listModelPerson.addAll(persons);
	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
}
